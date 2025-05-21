package com.troutarea.notes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import com.troutarea.notes.models.Note;
import com.troutarea.notes.models.Tournament;

import com.troutarea.notes.dao.NoteDAO;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NoteController {

    // Поля интерфейса
    @FXML
    private TextField txtTour;
    @FXML
    private TextField txtLure;
    @FXML
    private TextField txtLureDesc;
    @FXML
    private TextArea txtDescriptionWiring;
    @FXML
    private TextField txtFishActivity;
    @FXML
    private TextField txtHorizon;
    @FXML
    private TextField txtWaterClarity;
    @FXML
    private TextField txtWeather;
    @FXML
    private TextField txtCastDistance;
    @FXML
    private TextField txtFishingGear;

    @FXML
    private ListView<String> notesListView;

    @FXML
    private TableView<Note> notesTableView;


    private int sector;
    private Tournament tournament;
    private List<Note> noteList;
    private Mode currentMode;



    // Установка сектора
    public void setSector(int sector) {
        this.sector = sector;
    }

    // Установка турнира
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    // Метод для сохранения заметки
    @FXML
    public void handleSaveNote() {
        Note note = new Note();

        note.setSector(sector);
        note.setTour(txtTour.getText());
        note.setLure(txtLure.getText());
        note.setLureDesc(txtLureDesc.getText());
        note.setDescriptionWiring(txtDescriptionWiring.getText());
        note.setFishActivity(txtFishActivity.getText());
        note.setHorizon(txtHorizon.getText());
        note.setWaterClarity(txtWaterClarity.getText());
        note.setWeather(txtWeather.getText());
        note.setCastDistance(txtCastDistance.getText());
        note.setFishingGear(txtFishingGear.getText());
        note.setDate(LocalDate.now().toString());

        // Проверка и сохранение
        boolean isUpdated = NoteDAO.updateNoteIfExists(note);
        if (isUpdated) {
            showInformationAlert("Success", "Заметка успешно обновлена.");
        } else {
            NoteDAO.saveNote(note);
            showInformationAlert("Success", "Заметка сохранена!");
        }
    }

    // Метод для отображения всех заметок
//    @FXML
//    public void handleViewNotes() {
//        List<Note> notes = NoteDAO.getAllNotes();
//        List<String> noteDescriptions = notes.stream()
//                .map(note -> "Sector: " + note.getSector() + ", Tour: " + note.getTour())
//                .collect(Collectors.toList());
//
//        notesListView.getItems().setAll(noteDescriptions);
//
//        if (notes.isEmpty()) {
//            showInformationAlert("Нет заметок", "Нет доступных заметок для просмотра.");
//        }
//    }

    // Вспомогательный метод для отображения алертов
    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
     * Обработчик нажатия кнопки "Сохранить"
     */
    @FXML
    public void handleSaveButton() {
        try {
            // Если мы в режиме просмотра, то сохранение не требуется
            if (mode == Mode.VIEW) {
                return;
            }

            // Вызываем существующий метод сохранения заметки
            handleSaveNote();

            // Закрываем окно после сохранения
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка при сохранении", "Не удалось сохранить заметку: " + e.getMessage());
        }
    }

    /*
     * Обработчик нажатия кнопки "Отмена"
     */
    @FXML
    public void handleCancelButton() {
        // Закрываем окно без сохранения
        closeWindow();
    }

    /*
     * Обработчик нажатия кнопки "Назад"
     */
    @FXML
    public void handleBackButton() {
        // Закрываем текущее окно и возвращаемся к предыдущему экрану
        closeWindow();
    }

    /*
     * Обработчик нажатия кнопки "Просмотреть детали"
     */
    @FXML
    public void handleViewDetailsButton() {
        try {
            // Получаем выбранную заметку из таблицы или списка
            Note selectedNote = getSelectedNote();

            if (selectedNote == null) {
                showInformationAlert("Выбор заметки", "Пожалуйста, выберите заметку для просмотра.");
                return;
            }

            // Открываем окно с деталями заметки
            openNoteDetailsWindow(selectedNote);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка", "Не удалось открыть детали заметки: " + e.getMessage());
        }
    }

    /*
     * Получение выбранной заметки из таблицы или списка
     */
    private Note getSelectedNote() {
        // Если у вас используется TableView
        if (notesTableView != null && notesTableView.getSelectionModel().getSelectedItem() != null) {
            return notesTableView.getSelectionModel().getSelectedItem();
        }

        // Если у вас используется ListView
        if (notesListView != null && notesListView.getSelectionModel().getSelectedIndex() >= 0) {
            int selectedIndex = notesListView.getSelectionModel().getSelectedIndex();
            if (noteList != null && selectedIndex < noteList.size()) {
                return noteList.get(selectedIndex);
            }
        }

        return null;
    }

    /*
     * Открытие окна с деталями заметки
     */
    private void openNoteDetailsWindow(Note note) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NoteDetails.fxml"));
        Parent root = loader.load();

        // Получаем контроллер и передаем ему данные
        NoteDetailsController controller = loader.getController();
        controller.setNote(note);
        controller.setReadOnly(true); // Режим только для чтения

        // Создаем новое окно
        Stage stage = new Stage();
        stage.setTitle("Детали заметки");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /*
     * Закрытие текущего окна
     */
    private void closeWindow() {
        // Получаем текущее окно и закрываем его
        Stage stage = (Stage) notesTableView.getScene().getWindow();
        stage.close();
    }

    /*
     * Показ сообщения об ошибке
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
     * Перечисление для режимов работы контроллера
     */
    public enum Mode {
        VIEW, // Режим просмотра
        EDIT,
        ADD// Режим редактирования
    }

    /*
     * Поле для хранения текущего режима
     */
    private Mode mode = Mode.VIEW;

    /*
     * Метод для установки режима
     */
    public void setMode(Mode mode) {
        this.mode = mode;

        // Настраиваем интерфейс в зависимости от режима
        if (mode == Mode.VIEW) {
            // В режиме просмотра скрываем кнопки сохранения и отмены
            // Это нужно реализовать, если у вас есть соответствующие элементы интерфейса
        }
    }


    /* Метод для установки списка заметок
 */
    public void setNoteList(List<Note> notes) {
        this.noteList = notes;

        // Обновляем отображение в зависимости от используемого компонента
        if (notesTableView != null) {
            notesTableView.getItems().setAll(notes);
        } else if (notesListView != null) {
            List<String> noteDescriptions = notes.stream()
                    .map(note -> "Сектор: " + note.getSector() + ", Тур: " + note.getTour() + ", Дата: " + note.getDate())
                    .collect(Collectors.toList());
            notesListView.getItems().setAll(noteDescriptions);
        }
    }


//    private ObservableList<Note> noteList = FXCollections.observableArrayList();
//
//    public void initialize() {
//        loadNotes();
//        displayNotes();
//    }
//
//    private void loadNotes() {
//        // Здесь загружаем данные из вашей БД или другого источника и добавляем их в noteList
//        // Например:
//        noteList.addAll(fetchNotesFromDatabase());
//    }
//
//    public void setNoteList(ObservableList<Note> notes) {
//        this.noteList = notes;
//    }
//
//    public void displayNotes() {
//        if (noteList != null && !noteList.isEmpty()) {
//            for (Note note : noteList) {
//                System.out.println(note);
//            }
//        } else {
//            System.out.println("No notes available.");
//        }
//    }
//
//    private List<Note> fetchNotesFromDatabase() {
//        // Здесь должна находиться логика для получения данных из вашей базы данных
//        return new ArrayList<>(); // возвращает список заметок
//    }

}