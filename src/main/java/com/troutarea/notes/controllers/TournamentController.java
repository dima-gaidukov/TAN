package com.troutarea.notes.controllers;
import com.troutarea.notes.models.Venue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.troutarea.notes.models.Tournament;

import java.util.HashMap;
import java.util.Map;


public class TournamentController {




    @FXML
    private Label reservoirLabel;
    @FXML
    private ListView<Tournament> tournamentList;

    private String reservoirKey;

    @FXML
    private VBox mainContainer;


    // Карта соответствия водоемов и их фоновых изображений
    private Map<String, String> reservoirBackgrounds = new HashMap<>();

    // Текущий выбранный водоем
    private String currentReservoir;

    @FXML
    public void initialize() {
        // Инициализация карты водоемов и их фоновых изображений
        reservoirBackgrounds.put("Парус-Север", "/images/ParusSever.jpg");
        reservoirBackgrounds.put("ФишПаркАрена", "/images/Arena.jpg");
        reservoirBackgrounds.put("Парус-Юг", "/images/venues/Arena/SectorArena.jpg");
        reservoirBackgrounds.put("Озеро Селигер", "/images/Seliger.jpg");

    }

    // Метод, вызываемый из предыдущего экрана для передачи выбранного водоёма
    public void setReservoir(String reservoirKey) {
        this.reservoirKey = reservoirKey;
        reservoirLabel.setText("Водоём: " + reservoirKey);
        // Инициализируем список турниров для выбранного водоёма
        loadTournaments();
        // Загрузка изображения в каждому водоему(разная)
        String backgroundImage = reservoirBackgrounds.getOrDefault(reservoirKey, "/images/ParusSever.jpg");
        mainContainer.setStyle("-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;");

    }



    private void loadTournaments() {

        // Пример данных – в реальности можно загрузить с сервера или БД
        ObservableList<Tournament> list = FXCollections.observableArrayList();
        if ("Парус-Север".equals(reservoirKey)) {
            list.add(new Tournament(1,"6 этап ITC Open (1 день) личка", "2025-04-05", "/images/tournamentA.jpg", new Venue("Парус-Север")));

            list.add(new Tournament(2,"6 этап ITC Open (2 день) личка", "2025-04-06", "/images/tournamentA.jpg", new Venue("Парус-Север")));

            list.add(new Tournament(3,"6 этап ITC Team Open (1 день) ", "2025-04-12", "/images/tournamentA.jpg", new Venue("Парус-Север")));

            list.add(new Tournament(4,"6 этап ITC Team Open (2 день)", "2025-04-13", "/images/tournamentA.jpg", new Venue("Парус-Север")));

            list.add(new Tournament(5,"7 этап ITC Open _Forest Trout_ (1 день) личка", "2025-04-19", "/images/tournamentA.jpg", new Venue("Парус-Север")));

            list.add(new Tournament(6,"7 этап ITC Open (2 день) личка", "2025-04-20", "/images/tournamentA.jpg", new Venue("Парус-Север")));


            // Добавьте другие турниры для водоёма "north"
        } else if ("Парус-Юг".equals(reservoirKey)) {
            list.add(new Tournament(1,"Международный турнир ITC Парус-Арена (1 день)", "2025-04-26", "/images/tournamentB.jpg", new Venue("Парус-Юг")));
            list.add(new Tournament(2,"Международный турнир ITC Парус-Арена (2 день)", "2025-04-27", "/images/tournamentB.jpg", new Venue("Парус-Юг")));

        } else if ("ФишПаркАрена".equals(reservoirKey)) {
            list.add(new Tournament(1,"Trout Fighters spring", "2025-04-05", "/images/Arena.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(2,"ЖЕЛЕЗНЫЙ АРГУМЕНТ", "2025-04-12", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(3,"MUKAI CUP", "2025-04-19", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(4,"VALKEIN STAGE MOSCOW", "2025-04-26", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(5,"VALKEIN CUP RUSSIA", "2025-05-17", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(6,"KING OF WOBBLERS", "2025-05-24", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(7,"YARIE CUP", "2025-05-31", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));

            list.add(new Tournament(8,"BELIEVE IN THE BAIT", "2025-06-07", "/images/tournamentD.jpg", new Venue("ФишПаркАрена")));


        }

        tournamentList.setItems(list);

        // Обработка двойного клика по турниру для перехода дальше (например, на карту водоёма)
        tournamentList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2 && tournamentList.getSelectionModel().getSelectedItem() != null) {
                loadSectorScreen(tournamentList.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void loadSectorScreen(Tournament tournament) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Sector.fxml"));
            Parent root = loader.load();

            // Передача данных выбранного турнира (реализуйте метод setTournament в SectorController)
            SectorController controller = loader.getController();
            controller.setTournament(tournament);

            Stage stage = (Stage) tournamentList.getScene().getWindow();
            stage.setTitle("Выбор сектора");
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

