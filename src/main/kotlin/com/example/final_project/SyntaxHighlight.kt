package com.example.final_project

import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder


class SyntaxHighlight {

    /**
     * Applies syntax highlighting to the given text by matching specific patterns and assigning colors.
     *
     * @param text Syntax text content to highlight
     * @return Colored syntax styles for the CodeArea
     */
    fun applyHighlight(text: String): StyleSpans<Collection<String>> {

        // The RegEx patterns to be colored
        val patterns = mapOf(
            "\\{|\\}" to Theme.curlyBraces, // Curly braces
            "\\bfun\\b" to Theme.funKeyword, // fun keyword
            "(?<![A-Za-z0-9'\"\\-])\\b\\d+\\b(?![A-Za-z0-9'\"\\-])" to Theme.numbers, // Numeric values
            "\\b(if|else if|else)\\b" to Theme.conditionals, // if, else if, else
            "\\breturn\\b" to Theme.returnKeyword, // return
            "(['\"]).*?\\1" to Theme.strings, // Strings
            "\\b(Int|Long|Double|Float|Boolean|Char|String|Byte|Short|Array|List|Set|Map|Any|Unit|Nothing)\\b" to Theme.dataTypes, // Common data types
            "<([^>]+)>" to Theme.angledBraces, // Angled braces with content inside
            "//.*|/\\*[\\s\\S]*?\\*/" to Theme.comments // Comments
        )

        // Initialize the collection of styles
        val styledText = StyleSpansBuilder<Collection<String>>()
        var lastEnd = 0

        // Find all RegEx pattern matches in the text and create pairs of results found with style class to apply
        val matches = patterns.flatMap { (pattern, styleClass) ->
            pattern.toRegex().findAll(text).map { matchResult ->
                matchResult to styleClass
            }
        }.sortedBy { it.first.range.first } // Sort in order of occurrence

        // Apply styles to matches in order
        for ((matchResult, styleClass) in matches) {
            // Ensure lastEnd is up-to-date before processing each match
            if (matchResult.range.first < lastEnd) {
                // This can happen if matches are unordered or overlapping
                continue // Skip this match
            }

            // Add default (no) style to text between matches
            val length = matchResult.range.first - lastEnd
            styledText.add(emptyList(), length)

            // Apply specific style to matched pattern
            styledText.add(listOf(styleClass), matchResult.range.last - matchResult.range.first + 1)

            // Update last end position
            lastEnd = matchResult.range.last + 1
        }

        // Add remaining text as default style if any
        styledText.add(emptyList(), text.length - lastEnd)

        return styledText.create()
    }
}