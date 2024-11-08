package com.example.final_project

object Theme
{
    var curlyBraces: String = "text-color-1"
    var funKeyword: String = "text-color-2"
    var numbers: String = "text-color-3"
    var conditionals: String = "text-color-4"
    var returnKeyword: String = "text-color-5"
    var strings: String = "text-color-6"
    var dataTypes: String = "text-color-7"
    var angledBraces: String = "text-color-8"
    var comments: String = "text-color-9"

    /**
     * Sets a new theme for the IDE.
     *
     * @param curlyBracesColor The font color for curly braces.
     * @param funKeywordColor The font color for the fun keyword.
     * @param numbersColor The font color for numbers.
     * @param conditionalsColor The font color for conditionals (if, if else, else).
     * @param returnKeywordColor The font color for the return keyword.
     * @param stringsColor The font color for Strings.
     * @param dataTypesColor The font color for data type keywords.
     * @param angledBracesColor The font color for angles braces and their content.
     * @param commentsColor The font color for comments.
     *
     */
    fun setNewTheme(curlyBracesColor: String, funKeywordColor: String, numbersColor: String, conditionalsColor: String, returnKeywordColor: String, stringsColor: String, dataTypesColor: String, angledBracesColor: String, commentsColor: String)
    {
        this.curlyBraces = curlyBracesColor
        this.funKeyword = funKeywordColor
        this.numbers = numbersColor
        this.conditionals = conditionalsColor
        this.returnKeyword = returnKeywordColor
        this.strings = stringsColor
        this.dataTypes = dataTypesColor
        this.angledBraces = angledBracesColor
        this.comments = commentsColor
    }

}