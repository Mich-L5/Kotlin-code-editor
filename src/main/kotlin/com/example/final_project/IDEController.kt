package com.example.final_project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import java.net.URL
import java.util.*

class IDEController : Initializable {
    @FXML
    private lateinit var textContent: TextArea

    @FXML
    private val newFile: Button? = null

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

        textContent.text = "Hello World"
    }

    @FXML
    fun createNewFile(event: ActionEvent?) {
        println("button clicked!")
    }
}