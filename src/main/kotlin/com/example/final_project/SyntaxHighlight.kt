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
            "\\{|\\}" to "text-color-1", // Curly braces
            "\\bfun\\b" to "text-color-2", // fun keyword
            "(?<![A-Za-z0-9'\"\\-])\\b\\d+\\b(?![A-Za-z0-9'\"\\-])" to "text-color-3", // Numeric values
            "\\b(if|elseif|else)\\b" to "text-color-4", // if, elseif, else
            "\\breturn\\b" to "text-color-5", // return
            "(['\"]).*?\\1" to "text-color-6", // Strings
            "\\b(Int|Long|Double|Float|Boolean|Char|String|Byte|Short|Array|List|Set|Map|Any|Unit|Nothing)\\b" to "text-color-7", // Common data types
            "<([^>]+)>" to "text-color-8", // Angled braces
            "//.*|/\\*[\\s\\S]*?\\*/" to "text-color-9" // Comments
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
//            if (length < 0) {
//                println("Warning: Negative length detected, skipping this match")
//                continue
//            }
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