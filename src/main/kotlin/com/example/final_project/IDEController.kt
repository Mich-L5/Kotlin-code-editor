package com.example.final_project

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.stage.Modality
import javafx.stage.Stage
import java.net.URL
import java.util.*

class IDEController : Initializable {

    @FXML
    private lateinit var textContent: TextArea

    @FXML
    private lateinit var fileList: ListView<String>

    private var currentFile: File? = null

    private val dbHelper = DatabaseHelper()

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

        textContent.text = "No file selected. Select a file or create a new file to get started."

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

        // Load file on select click handler
        fileList.setOnMouseClicked { event ->
            val selectedItem = fileList.selectionModel.selectedItem

            if (selectedItem != null) {
                currentFile = dbHelper.getFileByName(selectedItem)!!
                setTextContent(currentFile!!.getFileContent())
            }
        }
    }

    /**
     * Executes when the New+ button is clicked.
     *
     * Opens the new file creation popup window, and passes the current IDEController instance.
     */
    @FXML
    fun createNewFile(event: ActionEvent?) {

        // Pop up window to name the file
        // Load the FXML for the popup window
        val loader = FXMLLoader(javaClass.getResource("newFilePopup.fxml"))
        val root = loader.load<Parent>()

        // Get the NewFilePopupController from the loader
        val newFilePopupController = loader.getController<NewFilePopupController>()

        // Pass the current IDEController instance to the NewFilePopupController
        newFilePopupController.setIDEController(this)

        // Create a new stage for the popup
        val stage = Stage()
        stage.title = "Create New File"
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.scene = Scene(root)

        // Show the popup window
        stage.showAndWait()
    }

    /**
     * Takes in a File and sets it as the current file.
     *
     * @param updatedCurrentFile File to set as current file.
     * @return The updated, current file.
     */
    fun setCurrentFile(updatedCurrentFile: File): File
    {
        return updatedCurrentFile
    }

    /**
     * Executes when the Save button is clicked.
     *
     * Saves (updates) the current open file's text content to the database.
     */
    @FXML
    fun saveFile(event: ActionEvent?) {

        val newTextContent = textContent.text
        currentFile?.setFileContent(newTextContent)
        currentFile?.let { dbHelper.updateFileContent(it.getFileName(), currentFile!!.getFileContent()) }
    }

    /**
     * Executes when the Delete File button is clicked.
     *
     * Triggers the confirm delete popup to launch, and passes the current IDEController instance and current file.
     */
    @FXML
    fun deleteFile(event: ActionEvent?) {

        if (currentFile != null)
        {
            // Pop up window to confirm file deletion
            // Load the FXML for the popup window
            val loader = FXMLLoader(javaClass.getResource("confirmDelete.fxml"))
            val root = loader.load<Parent>()

            // Get the ConfirmDeleteController from the loader
            val confirmDeleteController = loader.getController<ConfirmDeleteController>()

            // Pass the current IDEController instance and the current file to the ConfirmDeleteController
            confirmDeleteController.setIDEController(this)
            confirmDeleteController.setFileToDelete(currentFile!!)

            // Create a new stage for the popup
            val stage = Stage()
            stage.title = "Confirm Delete"
            stage.initModality(Modality.APPLICATION_MODAL)  // This makes the popup modal
            stage.scene = Scene(root)

            // Show the popup window
            stage.showAndWait()

            currentFile = null
        }
    }

    /**
     * Sets the IDE's text content.
     *
     * @param updatedTextContent Text to set as the IDE's text content.
     */
    fun setTextContent(updatedTextContent: String?) {
        textContent.text = updatedTextContent
    }

    /**
     * Adds a file name to the list of file names displayed in the List View.
     *
     * @param newFile File to add to the file list.
     */
    fun addToFileList(newFile: File) {
        fileList.items?.add(newFile.getFileName());
    }

    /**
     * Updates the file list displayed in the List View. Fetches all the files from the database and inserts the file names in a list in the List View.
     */
    fun updateFileList() {

        val files = dbHelper.getAllFiles()

        // Map the list of files to a list of file names (Strings)
        val fileNames = FXCollections.observableArrayList<String>()

        // Add file names to the ObservableList
        files.forEach { file ->
            fileNames.add(file.getFileName())  // Only get the file name
        }

        // Set the ListView's items to the list of file names
        fileList?.items = fileNames

        textContent.text = "No file selected. Select a file or create a new file to get started."
    }
}