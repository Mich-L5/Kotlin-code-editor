package com.example.final_project

class File(private var fileName: String, private var fileContent: String = "This is a new file! Start writing code here!") {


    fun getFileContent(): String {
        return this.fileContent
    }

    fun getFileName(): String {
        return this.fileName
    }

    fun setFileContent(content: String) {
        this.fileContent = content
    }

    fun setFileName(name: String) {
        this.fileName = name
    }


//    override fun toString(): String
//    {
//        return this.fileName + ", " + this.fileContent
//    }


}
