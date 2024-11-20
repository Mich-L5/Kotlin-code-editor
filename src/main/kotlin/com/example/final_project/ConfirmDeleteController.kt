package com.example.final_project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.Stage

class ConfirmDeleteController {

    @FXML
    private lateinit var deleteFileBtn: Button

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

    private lateinit var fileToDelete: File

    /**
     * Sets the file to delete (this is executed in the IDEController class)
     *
     * @param file The file to be deleted.
     */
    fun setFileToDelete(file: File) {
        this.fileToDelete = file
    }

    /**
     * Executes when the Confirm Delete button is clicked.
     *
     * Deletes the current file from the database.
     */
    @FXML
    fun deleteFile(event: ActionEvent?) {

        dbHelper.deleteFile(fileToDelete.getFileName())
        editorController.updateFileList()

        editorController.textContent.isEditable = false
        editorController.currentFile = null

        // Close window
        val stage = deleteFileBtn?.scene?.window as Stage
        stage.close()
    }
}