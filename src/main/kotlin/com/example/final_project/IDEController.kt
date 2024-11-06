package com.example.final_project

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.stage.Modality
import javafx.stage.Stage
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


//        // Pop up window to name the file
//        // Load the FXML for the popup window
//        val loader = FXMLLoader(javaClass.getResource("newFilePopup.fxml"))
//        val root = loader.load<Parent>()
//
//        // Create a new stage for the popup
//        val stage = Stage()
//        stage.title = "New File"
//        stage.initModality(Modality.APPLICATION_MODAL)  // This makes the popup modal
//        stage.scene = Scene(root)
//
//        // Show the popup window
//        stage.showAndWait()



        var fileName: String = "new file"

        // Check if file name already exists
        val fileManager = FileManager(dbHelper)

        if(!fileManager.checkFileDuplicate(fileName))
        {
            // Check if file name is valid
            if (fileManager.checkFileNameValid(fileName))
            {

                println("valid")


                // Format file name
                print(fileManager.formatFileName(fileName))

                // Create file
            }
            else
            {

            }


        }
        else
        {

        }




        // Create new file
        val file = File(fileName)


        textContent.text = file.getFileContent()

        dbHelper.insertFile(file)

        // Add new file to list view
        fileList.items?.add(file.getFileName());

    }
}