package com.example.final_project

class File(private var fileName: String, private var fileContent: String = "This is a new file! Start writing code here!") {

    /**
     * Returns the file's content.
     *
     * @return File's content.
     */
    fun getFileContent(): String {
        return this.fileContent
    }

    /**
     * Returns the file's name.
     *
     * @return File's name.
     */
    fun getFileName(): String {
        return this.fileName
    }

    /**
     * Set the file's content.
     *
     * @param content The content to set as the file's content.
     */
    fun setFileContent(content: String) {
        this.fileContent = content
    }

    /**
     * Set the file's name.
     *
     * @param name The name to set as the file's name.
     */
    fun setFileName(name: String) {
        this.fileName = name
    }

}
