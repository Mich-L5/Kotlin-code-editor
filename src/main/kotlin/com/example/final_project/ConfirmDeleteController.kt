package com.example.final_project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.Stage


class ConfirmDeleteController {

    @FXML
    private lateinit var deleteFileBtn: Button

    private val dbHelper = DatabaseHelper()

    private lateinit var ideController: IDEController

    // Method to set the IDEController (this is executed in the IDEController class)
    fun setIDEController(ideController: IDEController) {
        this.ideController = ideController
    }

    private lateinit var fileToDelete: File

    fun setFileToDelete(file: File) {
        this.fileToDelete = file
    }

    @FXML
    fun deleteFile(event: ActionEvent?) {

        dbHelper.deleteFile(fileToDelete.getFileName())
        ideController.updateFileList()

        // Close window
        val stage = deleteFileBtn?.scene?.window as Stage
        stage.close()
    }
}