package com.example.final_project

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseHelper {

    // Create connection string
    private val url = "jdbc:sqlite:files.db"

    // Create table upon object initialization
    init {
        createTable()
    }

    // Connect to the database - returns a Connection object
    private fun connect(): Connection? {
        return try {
            // Create a connection to the DB using JDBC DriverManager
            DriverManager.getConnection(url)
        } catch (e: SQLException) {
            println("Connection failed: ${e.message}")

            return null
        }
    }

    // Create a table for storing File objects
    private fun createTable() {

        // Connect to the DB
        val connection: Connection? = connect()

        // Create SQL table if it does not already exist
        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS files (
                id INTEGER PRIMARY KEY,
                file_name TEXT NOT NULL,
                file_content TEXT NOT NULL
            );
        """


        // Execute the table creation
        try {
            connection?.createStatement()?.execute(createTableSQL)
        } catch (e: SQLException) {
            println("Error creating table: ${e.message}")
        }
    }

    // Insert a File object into the database
    fun insertFile(file: File) {
        val connection = connect()

        // SQL to insert new file
        val insertSQL = "INSERT INTO files (file_name, file_content) VALUES (?, ?)"

        try {
            // Prepare SQL statement
            val preparedStatement = connection?.prepareStatement(insertSQL)
            preparedStatement?.setString(1, file.getFileName())
            preparedStatement?.setString(2, file.getFileContent())

            // Execute statement
            preparedStatement?.executeUpdate()

        } catch (e: SQLException) {
            println("Error inserting file: ${e.message}")
        }
    }

    // Query all files from the database and return as a list of File objects
    fun getAllFiles(): List<File> {
        val files = mutableListOf<File>()

        // Connect and create SQL to retrieve all files
        val connection = connect()
        val selectSQL = "SELECT id, file_name, file_content FROM files"

        try {

            // Get all files
            val resultSet = connection?.createStatement()?.executeQuery(selectSQL)

            // Loop through the set of files one by one, adding them to the list of files
            while (resultSet?.next() == true) {
                val fileName = resultSet.getString("file_name")
                val fileContent = resultSet.getString("file_content")
                files.add(File(fileName, fileContent))
            }
        } catch (e: SQLException) {
            println("Error fetching files: ${e.message}")
        }
        return files
    }

    // Get a specific file by its ID
//    fun getFileById(fileId: Int): File? {
//        val connection = connect()
//        val selectSQL = "SELECT file_name, file_content FROM files WHERE id = ?"
//
//        try {
//            val preparedStatement = connection?.prepareStatement(selectSQL)
//            preparedStatement?.setInt(1, fileId)
//            val resultSet = preparedStatement?.executeQuery()
//            if (resultSet?.next() == true) {
//                val fileName = resultSet.getString("file_name")
//                val fileContent = resultSet.getString("file_content")
//                return File(fileName, fileContent)
//            }
//        } catch (e: SQLException) {
//            println("Error fetching file: ${e.message}")
//        }
//        return null
//    }
}
