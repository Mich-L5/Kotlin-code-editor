package com.example.final_project

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseHelper {

    // Create connection string
    //private val url = "jdbc:sqlite:files.db"
    private val url = "jdbc:sqlite::resource:files.db"

    /**
     * Creates the table upon initialization.
     */
    init {
        createTable()
    }

    /**
     * Connects to the database.
     *
     * @return The database connection.
     */
    private fun connect(): Connection? {
        return try
        {
            // Create a connection to the DB using JDBC DriverManager
            DriverManager.getConnection(url)
        }
        catch (e: SQLException)
        {
            println("Connection failed: ${e.message}")
            return null
        }
    }

    /**
     * Creates a table for storing File objects.
     */
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
        try
        {
            connection?.createStatement()?.execute(createTableSQL)
        }
        catch (e: SQLException)
        {
            println("Error creating table: ${e.message}")
        }
        finally
        {
            connection?.close()
        }
    }

    /**
     * Inserts a File object in the database.
     *
     * @param file The file to add to the database.
     */
    fun insertFile(file: File) {

        val connection = connect()

        // SQL to insert new file
        val insertSQL = "INSERT INTO files (file_name, file_content) VALUES (?, ?)"

        try
        {
            // Prepare SQL statement
            val preparedStatement = connection?.prepareStatement(insertSQL)
            preparedStatement?.setString(1, file.getFileName())
            preparedStatement?.setString(2, file.getFileContent())

            // Execute statement
            preparedStatement?.executeUpdate()

        }
        catch (e: SQLException)
        {
            println("Error inserting file: ${e.message}")
        }
        finally
        {
            connection?.close()
        }
    }

    /**
     * Queries the database for the list of File objects.
     *
     * @return The list of File objects from the database.
     */
    fun getAllFiles(): List<File> {

        val files = mutableListOf<File>()

        // Connect and create SQL to retrieve all files
        val connection = connect()
        val selectSQL = "SELECT id, file_name, file_content FROM files"

        try
        {
            // Get all files
            val resultSet = connection?.createStatement()?.executeQuery(selectSQL)

            // Loop through the set of files one by one, adding them to the list of files
            while (resultSet?.next() == true) {
                val fileName = resultSet.getString("file_name")
                val fileContent = resultSet.getString("file_content")
                files.add(File(fileName, fileContent))
            }
        }
        catch (e: SQLException)
        {
            println("Error fetching files: ${e.message}")
        }
        finally
        {
            connection?.close()
        }
        return files
    }

    /**
     * Updates the content of a File object in the database based on a file name.
     *
     * @param fileName The name of the file to update its content.
     * @param newFileContent The updated file content to save.
     */
    fun updateFileContent(fileName: String, newFileContent: String) {

        // Connect and create SQL to update file content based on file name
        val connection = connect()
        val updateSQL = "UPDATE files SET file_content = ? WHERE file_name = ?"

        try
        {
            // Prepare SQL statement
            val preparedStatement = connection?.prepareStatement(updateSQL)
            preparedStatement?.setString(1, newFileContent)
            preparedStatement?.setString(2, fileName)

            // Execute update statement
            val rowsUpdated = preparedStatement?.executeUpdate()
        }
        catch (e: SQLException)
        {
            println("Error updating file: ${e.message}")
        }
        finally
        {
            connection?.close()
        }
    }

    /**
     * Queries the database for a File object based on a file name.
     *
     * @param fileName The name of the file to retrieve from the database.
     * @return The File object.
     */
    fun getFileByName(fileName: String): File? {

        // Connect and create SQL to select file based on file name
        val connection = connect()
        val selectSQL = "SELECT file_name, file_content FROM files WHERE file_name = ?"

        try
        {
            val preparedStatement = connection?.prepareStatement(selectSQL)
            preparedStatement?.setString(1, fileName)

            val resultSet = preparedStatement?.executeQuery()

            // If the file is found, return a new File object
            if (resultSet?.next() == true)
            {
                val fileContent = resultSet.getString("file_content")
                return File(fileName, fileContent)
            }
        }
        catch (e: SQLException)
        {
            println("Error fetching file by name: ${e.message}")
        }
        finally
        {
            connection?.close()
        }

        // Return null if no file is found
        return null
    }

    /**
     * Deletes a File object from the database based on a file name.
     *
     * @param fileName The name of the file to delete from the database.
     */
    fun deleteFile(fileName: String) {

        // Connect and create SQL to delete the file based on file name
        val connection = connect()
        val deleteSQL = "DELETE FROM files WHERE file_name = ?"

        try
        {
            // Prepare the SQL statement
            val preparedStatement = connection?.prepareStatement(deleteSQL)
            preparedStatement?.setString(1, fileName)

            // Execute the delete statement
            val rowsDeleted = preparedStatement?.executeUpdate()

        }
        catch (e: SQLException)
        {
            println("Error deleting file: ${e.message}")
        }
        finally
        {
            connection?.close()
        }
    }
}
