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
            "\\{|\\}" to "red-text", // Curly braces
            "\\bfun\\b" to "blue-text", // 'fun' keyword
            ";" to "green-text", // Semicolons
            "\\d+" to "pink-text" // Numeric values
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
        for ((matchResult, styleClass) in matches)
        {
            // Add default (no) style to text between matches
            styledText.add(emptyList(), matchResult.range.first - lastEnd)

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