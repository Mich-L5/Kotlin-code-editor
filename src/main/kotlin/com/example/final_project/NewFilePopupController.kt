package com.example.final_project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

class NewFilePopupController : Initializable {

    @FXML
    private lateinit var createFileBtn: Button

    @FXML
    private lateinit var fileNameInput: TextField

    @FXML
    private lateinit var errorMsg: Label

    private lateinit var fileName:String

    private val dbHelper = DatabaseHelper()

    private lateinit var editorController: EditorController

    /**
     * Sets the IDEController (this is executed in the IDEController class)
     *
     * @param editorController IDE Controller to be set.
     */
    fun setIDEController(editorController: EditorController) {
        this.editorController = editorController
    }

    /**
     * Executes when the Create File button is clicked.
     *
     * Creates a new File object using the file name input and saves it to the database. Also displays the new file in the List View in the IDE.
     *
     */
    @FXML
    fun createFile(event: ActionEvent?) {

        // Get file name
        fileName = fileNameInput?.text.toString()

        val fileManager = FileManager(dbHelper)

        // Check if file name is valid
        if(fileManager.checkFileNameValid(fileName))
        {
            // Format file name
            fileName = fileManager.formatFileName(fileName)

            // Check if file name already exists
            if (!fileManager.checkFileDuplicate(fileName))
            {
                // Create new file
                val file = File(fileName)
                dbHelper.insertFile(file)
                editorController.addToFileList(file)

                // Close window
                val stage = createFileBtn?.scene?.window as Stage
                stage.close()
            }
            else
            {
                errorMsg.text = "File name already exists. Please try again."
            }
        }
        else
        {
            errorMsg.text = "File name must be between 1-30 characters. Please try again."
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        errorMsg.text = ""
    }
}