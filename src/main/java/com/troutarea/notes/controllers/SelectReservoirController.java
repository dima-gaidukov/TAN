package com.troutarea.notes.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class SelectReservoirController {
    @FXML
    private ImageView reservoirBackgroundImage;


    @FXML
    private Button btnNorth;
    @FXML
    private Button btnSouth;
    @FXML
    private Button btnFishPark;

    @FXML
    public void initialize() {
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/Arena.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Ошибка при загрузке изображения: " + backgroundImage.getException().getMessage());
            } else {
                reservoirBackgroundImage.setImage(backgroundImage);
                System.out.println("Фоновое изображение загружено успешно");
            }
        } catch (Exception e) {
            System.err.println("Исключение при загрузке изображения: " + e.getMessage());
            e.printStackTrace();
        }

        // При клике на кнопку выбираем водоём и переходим к экрану выбора турнира
        btnNorth.setOnAction(e -> loadTournamentScreen("Парус-Север"));
        btnSouth.setOnAction(e -> loadTournamentScreen("Парус-Юг"));
        btnFishPark.setOnAction(e -> loadTournamentScreen("ФишПаркАрена"));
    }


    private void loadTournamentScreen(String reservoirKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Tournament.fxml"));
            Parent root = loader.load();

            // Передача выбранного водоёма контроллеру турниров (реализуйте метод setReservoir в TournamentController)
            TournamentController controller = loader.getController();
            if(controller != null) {
                controller.setReservoir(reservoirKey);
            }else {
                System.err.println("Ошибка: контроллер TournamentController не найден");

            }


            Stage stage = (Stage) btnNorth.getScene().getWindow();
            stage.setTitle("Выбор турнира");
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке экрана турнира: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

