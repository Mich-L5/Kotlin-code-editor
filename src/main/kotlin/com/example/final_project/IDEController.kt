package com.example.final_project

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ListView
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.Stage
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.model.PlainTextChange
import java.net.URL
import java.util.*


class IDEController : Initializable {

    @FXML
    private lateinit var anchorPane: AnchorPane

    @FXML
    lateinit var textContent: CodeArea

    @FXML
    private lateinit var fileList: ListView<String>

    @FXML
    private lateinit var deleteFileBtn: Button

    @FXML
    private lateinit var newFileBtn: Button

    @FXML
    private lateinit var saveBtn: Button

    @FXML
    private lateinit var themePicker: ChoiceBox<String>

    var currentFile: File? = null

    private val dbHelper = DatabaseHelper()

    private val syntaxHighlight = SyntaxHighlight()

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

        // Add theme options & default value
        themePicker.items?.addAll("Light", "Dark")
        themePicker.value = "Light"

        // Apply initial (Light) theme to GUI elements
        addThemeGUI(Theme.anchorBg, Theme.editorBg, Theme.sidebarBg, Theme.btn, Theme.choiceBox)

        themePicker.valueProperty().addListener { _, oldValue, newValue ->

            // Remove previous GUI theme
            removeThemeGUI(Theme.anchorBg, Theme.editorBg, Theme.sidebarBg, Theme.btn, Theme.choiceBox)

            if(newValue == "Light")
            {
                // Set light theme
                Theme.setNewTheme("text-color-1", "text-color-2","text-color-3","text-color-4","text-color-5","text-color-6","text-color-7","text-color-8","text-color-9", "bg", "editor-bg", "sidebar-bg", "btn-style", "choice-box-style")
            }
            else if (newValue == "Dark")
            {
                // Set dark theme
                Theme.setNewTheme("text-color-1-dark", "text-color-2-dark","text-color-3-dark","text-color-4-dark","text-color-5-dark","text-color-6-dark","text-color-7-dark","text-color-8-dark","text-color-9-dark", "bg-dark", "editor-bg-dark", "sidebar-bg-dark", "btn-style-dark", "choice-box-style-dark")
            }
            // More themes can be added

            // Create highlight styles
            val styledSpans = syntaxHighlight.applyHighlight(textContent.text)

            // Apply highlight styles
            textContent.setStyleSpans(0, styledSpans)

            // Apply theme to GUI elements
            addThemeGUI(Theme.anchorBg, Theme.editorBg, Theme.sidebarBg, Theme.btn, Theme.choiceBox)
        }

        textContent.replaceText("No file selected. Select a file or create a new file to get started.")
        textContent.isEditable = false

        // Event listener for code area
        textContent.plainTextChanges().subscribe { change: PlainTextChange ->

            // Create highlight styles
            val styledSpans = syntaxHighlight.applyHighlight(textContent.text)

            // Apply highlight styles
            textContent.setStyleSpans(0, styledSpans)
        }

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

            // Save current file changes
            saveCurrentFile()

            val selectedItem = fileList.selectionModel.selectedItem

            if (selectedItem != null) {
                currentFile = dbHelper.getFileByName(selectedItem)!!
                setTextContent(currentFile!!.getFileContent())

                // Create highlight styles
                val styledSpans = syntaxHighlight.applyHighlight(textContent.text)

                // Apply highlight styles
                textContent.setStyleSpans(0, styledSpans)
            }

            textContent.isEditable = true
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

        saveCurrentFile()
    }

    /**
     * Saves (updates) the current open file's text content to the database.
     */
    private fun saveCurrentFile()
    {
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

        }
    }

    /**
     * Sets the IDE's text content.
     *
     * @param updatedTextContent Text to set as the IDE's text content.
     */
    fun setTextContent(updatedTextContent: String?) {
        textContent.replaceText(updatedTextContent)
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

        textContent.replaceText("No file selected. Select a file or create a new file to get started.")
    }

    /**
     * Applies theme settings to the GUI.
     *
     * @param anchorPaneBg The anchor pane background color to set.
     * @param editorBg The code editor background color to set.
     * @param FileListBg The file list background color to set.
     * @param btn The button color to set.
     * @param choiceBoxStyle The choice box styles to set.
     */
    fun addThemeGUI(anchorPaneBg: String, editorBg: String, fileListBg: String, btn: String, choiceBoxStyle: String)
    {
        anchorPane.styleClass.add(anchorPaneBg)
        textContent.styleClass.add(editorBg)
        fileList.styleClass.add(fileListBg)
        saveBtn.styleClass.add(btn)
        deleteFileBtn.styleClass.add(btn)
        newFileBtn.styleClass.add(btn)
        themePicker.styleClass.add(choiceBoxStyle)
    }


    /**
     * Removes theme settings from the GUI.
     *
     * @param anchorPaneBg The anchor pane background color to unset.
     * @param editorBg The code editor background color to unset.
     * @param FileListBg The file list background color to unset.
     * @param btn The button color to unset.
     * @param choiceBoxStyle The choice box styles to unset.
     */
    fun removeThemeGUI(anchorPaneBg: String, editorBg: String, fileListBg: String, btn: String, choiceBoxStyle: String)
    {
        anchorPane.styleClass.remove(anchorPaneBg)
        textContent.styleClass.remove(editorBg)
        fileList.styleClass.remove(fileListBg)
        saveBtn.styleClass.remove(btn)
        deleteFileBtn.styleClass.remove(btn)
        newFileBtn.styleClass.remove(btn)
        themePicker.styleClass.remove(choiceBoxStyle)
    }

}