package com.troutarea.notes.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class SplashController {

    @FXML
    private ImageView splashImage;

    @FXML
    public void initialize() {
        // Загружаем изображение для заставки (файл должен быть в resources/images)
        Image image = new Image(getClass().getResourceAsStream("/images/zastavka.jpg"));
        splashImage.setImage(image);

        // Задержка 3 секунды для показа заставки
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> loadNextScreen());
        delay.play();
    }

    private void loadNextScreen() {



        try {
            // Загружаем экран выбора водоёма – FXML файл должен называться SelectReservoir.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SelectReservoir.fxml"));
            Parent root = loader.load();


            // Получаем текущее окно из ImageView
            Stage stage = (Stage) splashImage.getScene().getWindow();
            stage.setTitle("Выбор водоёма для турнира");
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при загрузке экрана выбора водоёма: " + e.getMessage());

        }
    }
}

