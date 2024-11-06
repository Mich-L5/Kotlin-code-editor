package com.example.final_project

class FileManager(private val dbHelper: DatabaseHelper) {

    // Check if a file name already exists in the database
    fun checkFileDuplicate(fileName: String): Boolean {
        val existingFileNames = dbHelper.getAllFiles()
        return existingFileNames.any { it.getFileName() == fileName }
    }

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




    // Create a new file if it doesn't already exist
//    fun createFile(fileName: String, fileContent: String): Boolean {
//        return if (checkFileDuplicate(fileName)) {
//            false  // Return false if file already exists
//        } else {
//            val newFile = File(fileName, fileContent)
//            dbHelper.insertFile(newFile)
//            true  // Return true to indicate success
//        }
//    }

    // Retrieve all file names
//    fun getAllFileNames(): List<String> {
//        return dbHelper.getAllFiles().map { it.getFileName() }
//    }
}
