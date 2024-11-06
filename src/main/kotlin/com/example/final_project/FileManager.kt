package com.example.final_project

class FileManager(private val dbHelper: DatabaseHelper) {


    // Check if file name is valid (cannot be over max. size or blank)
    fun checkFileNameValid(fileName: String): Boolean {

        return !(fileName.length > 30 || fileName.isBlank())
    }


    // Format file name (remove spaces and add .kt extension)
    fun formatFileName(fileName: String): String {

        // Remove white spaces on either end
        val trimmedName = fileName.trim()

        // Replace any series of whitespaces with a single hyphen
        val formattedName = trimmedName.replace("\\s+".toRegex(), "-")

        // Add ".kt" at the end and return the new formatted name
        return "$formattedName.kt"
    }

    // Check if a file name already exists in the database
    fun checkFileDuplicate(fileName: String): Boolean {
        val existingFileNames = dbHelper.getAllFiles()
        return existingFileNames.any { it.getFileName().equals(fileName, ignoreCase = true) }
    }

}
