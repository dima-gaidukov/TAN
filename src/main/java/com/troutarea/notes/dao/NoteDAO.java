package com.troutarea.notes.dao;
import com.troutarea.notes.models.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.troutarea.notes.dao.DatabaseManager.getConnection;

public class NoteDAO {



    public static List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        // Код для подключения к базе данных и извлечения заметок
        // Например:
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/dmitrijgajdukov")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Note");
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                // Установите остальные поля
                note.setTour(resultSet.getString("tour"));
                note.setLure(resultSet.getString("lure"));
                note.setLureDesc(resultSet.getString("lure_desc"));
                note.setDescriptionWiring(resultSet.getString("description_wiring"));
                note.setFishActivity(resultSet.getString("fish_activity"));
                note.setHorizon(resultSet.getString("horizon"));
                note.setWaterClarity(resultSet.getString("water_clarity"));
                note.setWeather(resultSet.getString("weather"));
                note.setCastDistance(resultSet.getString("cast_distance"));
                note.setFishingGear(resultSet.getString("fishing_gear"));
                note.setSector(resultSet.getInt("sector"));
                note.setDate(resultSet.getString("date"));
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }




    /*
     * Получает список заметок для указанного сектора и турнира
     * @param sectorNumber номер сектора
     * @param tournamentId идентификатор турнира
     * @return список заметок
     */
    public List<Note> getNotesBySector(int sector) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM Note WHERE sector = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sector);

            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    count++;
                    Note note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setTour(resultSet.getString("tour"));
                    note.setLure(resultSet.getString("lure"));
                    note.setLureDesc(resultSet.getString("luredesc"));
                    note.setDescriptionWiring(resultSet.getString("descriptionwiring"));
                    note.setFishActivity(resultSet.getString("fishactivity"));
                    note.setHorizon(resultSet.getString("horizon"));
                    note.setWaterClarity(resultSet.getString("waterclarity"));
                    note.setWeather(resultSet.getString("weather"));
                    note.setCastDistance(resultSet.getString("castdistance"));
                    note.setFishingGear(resultSet.getString("fishinggear"));


                    // Проверка с одной попыткой получения описания
                    String lureDesc = resultSet.getString("luredesc");
                    if (lureDesc == null) {
                        lureDesc = resultSet.getString("lure_desc");
                    }
                    if (lureDesc == null) {
                        lureDesc = resultSet.getString("lure_description");
                    }
                    note.setLureDesc(lureDesc != null ? lureDesc : "");

                    note.setSector(resultSet.getInt("sector"));
                    note.setDate(resultSet.getString("date"));

                    notes.add(note);
                }
                System.out.println("Found " + count + " notes for sector " + sector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при получении заметок: " + e.getMessage());
        }
        return notes;
    }



    /*
     * Проверяет наличие заметок для указанного сектора и турнира
     * @param sectorNumber номер сектора
     * @param tournamentId идентификатор турнира
     * @return true, если заметки существуют, иначе false
     */
    public boolean hasSectorNotes(int sector, int id) {
        String query = "SELECT COUNT(*) FROM Note WHERE sector = ? AND id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sector);
            statement.setInt(2, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    Сохраняет заметку в базу данных
    @param note заметка для сохранения
      @return true, если сохранение успешно, иначе false
     */

    public static void saveNote(Note note) {
        String sql = "INSERT INTO Note(tour, lure, lureDesc, descriptionWiring, fishActivity, horizon, waterClarity, weather, castDistance, fishingGear, sector, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, note.getTour());
            stmt.setString(2, note.getLure());
            stmt.setString(3, note.getLureDesc());
            stmt.setString(4, note.getDescriptionWiring());
            stmt.setString(5, note.getFishActivity());
            stmt.setString(6, note.getHorizon());
            stmt.setString(7, note.getWaterClarity());
            stmt.setString(8, note.getWeather());
            stmt.setString(9, note.getCastDistance());
            stmt.setString(10, note.getFishingGear());
            stmt.setInt(11, note.getSector());
            stmt.setString(12, note.getDate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    Обновляет существующую заметку
    @param note заметка для обновления
    @return true, если обновление успешно, иначе false
    */

    public static boolean updateNoteIfExists(Note note) {
        String checkSql = "SELECT COUNT(*) FROM Note WHERE id = ?";
        String updateSql = "UPDATE Note SET tour = ?, lure = ?, lureDesc = ?, descriptionWiring = ?, "
                + "fishActivity = ?, horizon = ?, waterClarity = ?, weather = ?, "
                + "castDistance = ?, fishingGear = ?,sector = ?, date = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            checkStmt.setLong(1, note.getId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                updateStmt.setString(1, note.getTour());
                updateStmt.setString(2, note.getLure());
                updateStmt.setString(3, note.getLureDesc());
                updateStmt.setString(4, note.getDescriptionWiring());
                updateStmt.setString(5, note.getFishActivity());
                updateStmt.setString(6, note.getHorizon());
                updateStmt.setString(7, note.getWaterClarity());
                updateStmt.setString(8, note.getWeather());
                updateStmt.setString(9, note.getCastDistance());
                updateStmt.setString(10, note.getFishingGear());
                updateStmt.setInt(11, note.getSector());
                updateStmt.setString(12, note.getDate());
                updateStmt.setLong(13, note.getId());

                int rowsAffected = updateStmt.executeUpdate();
                return rowsAffected > 0;
            } else {
                System.err.println("Запись с id = " + note.getId() + " не найдена");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Удаляет заметку по ID
     * @param noteId ID заметки для удаления
     * @return true, если удаление успешно, иначе false
     */

    public boolean deleteNote(long id) {
        String query = "DELETE FROM Note WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при удалении заметки: " + e.getMessage());
            return false;
        }
    }
}

