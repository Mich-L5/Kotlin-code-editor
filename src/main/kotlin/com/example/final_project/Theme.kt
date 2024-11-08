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
    var anchorBg: String = "bg"
    var editorBg: String = "editor-bg"
    var sidebarBg: String = "sidebar-bg"
    var btn: String = "btn-style"
    var choiceBox: String = "choice-box-style"

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
     * @param anchorBgColor The background color of the anchor pane.
     * @param editorBgColor The background color of the text editor.
     * @param sidebarBgColor The background color of the sidebar.
     * @param btn The color of the buttons.
     * @param choiceBox The color of the choice box.
     *
     */
    fun setNewTheme(curlyBracesColor: String, funKeywordColor: String, numbersColor: String, conditionalsColor: String, returnKeywordColor: String, stringsColor: String, dataTypesColor: String, angledBracesColor: String, commentsColor: String, anchorBgColor: String, editorBgColor: String, sidebarBgColor: String, btnColor: String, choiceBoxColor: String)
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
        this.anchorBg = anchorBgColor
        this.editorBg = editorBgColor
        this.sidebarBg = sidebarBgColor
        this.btn = btnColor
        this.choiceBox = choiceBoxColor
    }

}