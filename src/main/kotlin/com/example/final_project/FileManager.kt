package com.example.final_project

class FileManager(private val dbHelper: DatabaseHelper) {

    /**
     * Checks if a file name is valid (between 1-30 characters).
     *
     * @param fileName The file name to evaluate.
     * @return Boolean: True (file name is valid), False (file name is invalid)
     */
    fun checkFileNameValid(fileName: String): Boolean {
        return !(fileName.length > 30 || fileName.isBlank())
    }

    /**
     * Formats a file name (replaces series of blank spaces with hyphens and adds a .kt file extension)
     *
     * @param fileName The file name to format.
     * @return The formatted file name.
     */
    fun formatFileName(fileName: String): String {

        // Remove white spaces on either end
        val trimmedName = fileName.trim()

        // Replace any series of whitespaces with a single hyphen
        val formattedName = trimmedName.replace("\\s+".toRegex(), "-")

        // Add ".kt" at the end and return the new formatted name
        return "$formattedName.kt"
    }

    /**
     * Checks if a file name already exists in the database (case insensitive).
     *
     * @param fileName The file name to check for duplicates in the database.
     * @return Boolean: True (file name already exists), False (file name does not exist).
     */
    fun checkFileDuplicate(fileName: String): Boolean {
        val existingFileNames = dbHelper.getAllFiles()
        return existingFileNames.any { it.getFileName().equals(fileName, ignoreCase = true) }
    }

}
