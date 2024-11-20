package com.example.final_project

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class App : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("ide.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "KOTLIN IDE"
        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(App::class.java)
        }
    }
}

//fun main() {
//    Application.launch(App::class.java)
//}