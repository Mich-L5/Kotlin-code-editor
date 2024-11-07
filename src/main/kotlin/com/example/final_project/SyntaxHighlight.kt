package com.example.final_project

import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder


class SyntaxHighlight {

    /**
     *
     * Takes in syntax code as text and applies colors to the text based on each type of character.
     *
     * @param text Syntax text content to highlight
     * @return Colored syntax styles
     */
    fun applyHighlight(text: String): StyleSpans<Collection<String>> {

        // Initialize the collection of styles
        val styledText = StyleSpansBuilder<Collection<String>>()

        // Regex pattern to match curly braces
        val bracePattern = "\\{|\\}".toRegex()

        var lastEnd = 0

        // Find and style curly braces
        bracePattern.findAll(text).forEach { matchResult ->

            // Apply default (no) style to text between matches
            // Calculate the distance between the last match's end and the new match
            styledText.add(emptyList(), matchResult.range.first - lastEnd)

            // Apply style
            styledText.add(listOf("red-text"), matchResult.range.last - matchResult.range.first + 1)

            // Update last end position
            lastEnd = matchResult.range.last + 1
        }

        // Add remaining text as default style if any
        styledText.add(emptyList(), text.length - lastEnd)

        return styledText.create()
    }

}
