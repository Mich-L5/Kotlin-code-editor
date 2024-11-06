package com.example.final_project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
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





    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

        textContent.text = "No file selected. Create a new file to get started."
    }

    // On New+ button click event
    @FXML
    fun createNewFile(event: ActionEvent?) {

        val file = File()
        textContent.text = file.getFileContent()


        dbHelper.insertFile(file)


    }
}