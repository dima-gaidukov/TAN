package com.troutarea.notes.controllers;
import java.util.List;

import com.troutarea.notes.dao.NoteDAO;
import com.troutarea.notes.models.Note;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.troutarea.notes.models.Tournament;

import java.lang.reflect.Field;
import java.util.Optional;


public class SectorController {

    // Метод для показа ошибки в виде диалогового окна
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Метод для показа информационного сообщения
    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private ImageView backgroundImage;

    private Tournament tournament; // Сохранение выбранного турнира

    @FXML
    private Button btnSector1, btnSector2, btnSector3, btnSector4, btnSector5, btnSector6, btnSector7, btnSector8,
            btnSector9, btnSector10 ,btnSector11 ,btnSector12 ,btnSector13, btnSector14, btnSector15, btnSector16,
            btnSector17, btnSector18, btnSector19, btnSector20, btnSector21, btnSector22, btnSector23, btnSector24,
    btnSector25, btnSector26, btnSector27, btnSector28, btnSector29, btnSector30, btnSector31, btnSector32,
    btnSector33, btnSector34, btnSector35, btnSector36, btnSector37, btnSector38, btnSector39, btnSector40;
    // … дополнительные кнопки для секторов

    private NoteDAO noteDAO;



    @FXML
    public void initialize() {


        // Инициализация сервиса для работы с заметками
        noteDAO = new NoteDAO();

        try {
            // Проходим по всем кнопкам от 1 до 40
            for (int i = 1; i <= 40; i++) {
                // Получаем поле по имени
                Field field = getClass().getDeclaredField("btnSector" + i);
                field.setAccessible(true);

                // Получаем кнопку из поля
                Button button = (Button) field.get(this);

                // Устанавливаем обработчик
                final int sector = i;
                button.setOnAction(event -> showSectorOptions(sector));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        System.out.println("Турнир установлен: " + tournament.getName() +
                " на водоеме: '" + tournament.getVenue().getName() + "'");
        loadBackgroundImage();
        updateAllSectorButtons();
    }

    /*
     * Обновляет внешний вид всех кнопок секторов
     */
    private void updateAllSectorButtons() {
        if (tournament == null) return;
        for (int i = 1; i <= 40; i++) {
            updateSectorButtonAppearance(i);
        }
    }

    private void updateSectorButtonAppearance(int sector) {
        try {
            boolean hasNotes = noteDAO.hasSectorNotes(sector, tournament.getId());
            Field field = getClass().getDeclaredField("btnSector" + sector);
            field.setAccessible(true);
            Button button = (Button) field.get(this);

            if (hasNotes) {
                button.getStyleClass().add("sector-with-notes");
                button.setStyle("-fx-background-color: #a6f1e6;");
            } else {
                button.getStyleClass().remove("sector-with-notes");
                button.setStyle("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * Показывает диалог с выбором действия для сектора
     */

    private void showSectorOptions(int sector) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Сектор " + sector);
        alert.setHeaderText("Выберите действие для сектора " + sector);

        ButtonType addNoteButton = new ButtonType("Добавить заметку");
        ButtonType viewNotesButton = new ButtonType("Просмотреть заметки");
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(addNoteButton, viewNotesButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == addNoteButton) {
                loadAddNoteScreen(sector);
            } else if (result.get() == viewNotesButton) {
                loadViewNotesScreen(sector);
            }
        }
    }


    /*
     * Загружает экран для добавления новой заметки
     */
    private void loadAddNoteScreen(int sector) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Note.fxml"));
            Parent root = loader.load();

            // Передача параметров в NoteController
            NoteController controller = loader.getController();
            controller.setSector(sector);
            controller.setTournament(tournament);
            controller.setMode(NoteController.Mode.ADD); // Устанавливаем режим добавления

            Stage stage = (Stage) btnSector1.getScene().getWindow();
            stage.setTitle("Добавление заметки для сектора " + sector);
            stage.setScene(new Scene(root, 800, 600));
        } catch(Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка при открытии формы добавления заметки: " + e.getMessage());
        }
    }


    /*
     * Загружает экран для просмотра существующих заметок
     */


    private void loadViewNotesScreen(int sector) {
        try {
            // Загружаем заметки из базы данных
            List<Note> notes = noteDAO.getNotesBySector(sector);

            // Проверяем, есть ли заметки для данного сектора
            if (notes.isEmpty()) {
                showInfoAlert("Для сектора " + sector + " нет заметок");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListView.fxml"));
            Parent root = loader.load();

            // Передача параметров в NoteController
            NoteController controller = loader.getController();
            controller.setSector(sector);
            controller.setTournament(tournament);
            controller.setMode(NoteController.Mode.VIEW); // Устанавливаем режим просмотра

            // Передаем уже загруженные заметки
            controller.setNoteList(notes);

            Stage stage = (Stage) btnSector1.getScene().getWindow();
            stage.setTitle("Просмотр заметок сектора " + sector);
            stage.setScene(new Scene(root, 800, 600));
        } catch(Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка при открытии формы просмотра заметок: " + e.getMessage());
        }
    }

    //     * Загружает фоновое изображение в зависимости от водоема и турнира
//     */
    private void loadBackgroundImage() {
        if (tournament == null) {
            System.err.println("Турнир не установлен");
            return;
        }

        String venue = tournament.getVenue().getName(); // Получаем название водоема
        String tournamentName = tournament.getName();   // Получаем название турнира

        // Формируем путь к изображению на основе водоема и турнира
        // Например: /images/venues/arena/tournament_a.jpg
        String imagePath = String.format("/images/venues/%s/%s.jpg",
                venue.toLowerCase().replace(" ", "_"),
                tournamentName.toLowerCase().replace(" ", "_"));

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));

            if (image.isError()) {
                System.err.println("Ошибка при загрузке изображения: " + image.getException().getMessage());
                loadDefaultImage(venue); // Загружаем изображение по умолчанию для водоема
            } else {
                backgroundImage.setImage(image);
                System.out.println("Фоновое изображение для турнира загружено успешно: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Исключение при загрузке изображения турнира: " + e.getMessage());
            loadDefaultImage(venue); // Загружаем изображение по умолчанию для водоема
        }
    }


    /*
     * Загружает изображение по умолчанию для конкретного водоема
     */
    private void loadDefaultImage(String venue) {
        String defaultImagePath;

        // Выбираем путь к изображению в зависимости от водоема
        if (venue.equalsIgnoreCase("Парус-Север") || venue.equalsIgnoreCase("Parus_Sever")) {
            defaultImagePath = "/images/venues/Parus_sever/PS.jpg";
        }
        else if (venue.equalsIgnoreCase("ФишПаркАрена") || venue.equalsIgnoreCase("Arena")) {
            defaultImagePath = "/images/venues/Arena/SectorArena.jpg";
        }
        else {
            // Для других водоемов используем общее изображение по умолчанию
            defaultImagePath = "/images/Arena.jpg";
        }

        try {
            Image defaultImage = new Image(getClass().getResourceAsStream(defaultImagePath));

            if (defaultImage.isError()) {
                System.err.println("Ошибка при загрузке изображения водоема: " + defaultImage.getException().getMessage());
                loadFallbackImage(); // Загружаем общее изображение по умолчанию
            } else {
                backgroundImage.setImage(defaultImage);
                System.out.println("Загружено изображение для водоема: " + venue + " - " + defaultImagePath);
            }
        } catch (Exception e) {
            System.err.println("Не удалось загрузить изображение для водоема: " + e.getMessage());
            loadFallbackImage(); // Загружаем общее изображение по умолчанию
        }
    }

    /*
     * Загружает общее изображение по умолчанию, если все остальные попытки не удались
     */
    private void loadFallbackImage() {
        try {
            Image fallbackImage = new Image(getClass().getResourceAsStream("/images/Arena.jpg"));
            backgroundImage.setImage(fallbackImage);
            System.out.println("Загружено общее изображение по умолчанию");
        } catch (Exception ex) {
            System.err.println("Не удалось загрузить общее изображение по умолчанию: " + ex.getMessage());
        }
    }


    private void loadNoteScreen(int sectorNumber) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListView.fxml"));
            Parent root = loader.load();

            // Передача параметров в NoteController
            NoteController controller = loader.getController();
            controller.setSector(sectorNumber);
            controller.setTournament(tournament);

            Stage stage = (Stage) btnSector1.getScene().getWindow();
            stage.setTitle("Заметки сектора " + sectorNumber);
            stage.setScene(new Scene(root, 800, 600));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
