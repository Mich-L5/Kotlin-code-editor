package com.example.final_project

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import java.net.URL
import java.util.*

class IDEController : Initializable {

    // DB Helper to save objects to the DB
    private val dbHelper = DatabaseHelper()


    @FXML
    private lateinit var textContent: TextArea

    @FXML
    private val newFile: Button? = null

    @FXML
    private lateinit var fileList: ListView<String>



    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

        textContent.text = "No file selected. Create a new file to get started."

        // Get all existing files and load them into the file list view
        // Get all files from the database
        val files = dbHelper.getAllFiles()
        // Map the list of files to a list of file names (Strings)
        val fileNames = FXCollections.observableArrayList<String>()

        // Add file names to the ObservableList
        files.forEach { file ->
            fileNames.add(file.getFileName())  // Only get the file name
        }

        // Set the ListView's items to the list of file names
        fileList?.items = fileNames



        print(fileNames)
    }

    // On New+ button click event
    @FXML
    fun createNewFile(event: ActionEvent?) {

        val file = File()
        textContent.text = file.getFileContent()

        dbHelper.insertFile(file)

        // Add new file to list view
        fileList?.items?.add(file.getFileName());

    }
}