package com.troutarea.notes;
import com.troutarea.notes.dao.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Главный класс, расширяющий Application. Отсюда начинается запуск JavaFX-приложения.
public class Main extends Application {



    // Метод start() является точкой входа в JavaFX-приложение.
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загрузка FXML-файла SplashScreen.fxml, который определяет интерфейс заставки
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SplashScreen.fxml"));
        Parent root = loader.load(); // загрузка разметки, возвращает объект Parent, который будет корневым узлом сцены

        // Создание новой сцены с заданными размерами
        Scene scene = new Scene(root, 800, 600);

        // Настройка заголовка окна
        primaryStage.setTitle("Заметки для TroutArea - Заставка");

        // Установка ранее созданной сцены в окно Stage
        primaryStage.setScene(scene);

        // Отображение окна
        primaryStage.show();
    }

    // Метод main, вызываемый при запуске приложения.
    public static void main(String[] args) {
        launch(args); // Запускает приложение, вызывает метод start()
    }
}
