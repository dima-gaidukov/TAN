package com.troutarea.notes.controllers;

import com.troutarea.notes.models.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteDetailsController {

    private Note note;
    private boolean readOnly;

    // Пример FXML полей (будьте уверены, что они соответствуют вашим элементам в FXML)
    @FXML
    private TextField tourField;

    @FXML
    private TextField lureField;

    @FXML
    private TextField lureDescField;

    @FXML
    private TextArea descriptionWiringField;

    @FXML
    private TextField fishActivityField;

    @FXML
    private TextField horizonField;

    @FXML
    private TextField waterClarityField;

    @FXML
    private TextField weatherField;

    @FXML
    private TextField castDistanceField;

    @FXML
    private TextField fishingGearField;

    @FXML
    private TextField sectorField;

    @FXML
    private TextField dateField;

    // Метод для установки объекта Note
    public void setNote(Note note) {
        this.note = note;
        updateUI(); // Обновляем интерфейс
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        setFieldsEditable(!readOnly); // Устанавливаем элементы только для чтения
    }

    // Метод для обновления интерфейса
    private void updateUI() {
        if (note != null) {
            tourField.setText(note.getTour());
            lureField.setText(note.getLure());
            lureDescField.setText(note.getLureDesc());
            descriptionWiringField.setText(note.getDescriptionWiring());
            fishActivityField.setText(note.getFishActivity());
            horizonField.setText(note.getHorizon());
            waterClarityField.setText(note.getWaterClarity());
            weatherField.setText(note.getWeather());
            castDistanceField.setText(note.getCastDistance());
            fishingGearField.setText(note.getFishingGear());
            sectorField.setText(String.valueOf(note.getSector()));
            dateField.setText(note.getDate());
        }
    }

    // Метод для установки полей в режим только для чтения
    private void setFieldsEditable(boolean editable) {
        tourField.setEditable(editable);
        lureField.setEditable(editable);
        lureDescField.setEditable(editable);
        descriptionWiringField.setEditable(editable);
        fishActivityField.setEditable(editable);
        horizonField.setEditable(editable);
        waterClarityField.setEditable(editable);
        weatherField.setEditable(editable);
        castDistanceField.setEditable(editable);
        fishingGearField.setEditable(editable);
        sectorField.setEditable(editable);
        dateField.setEditable(editable);
    }
}
