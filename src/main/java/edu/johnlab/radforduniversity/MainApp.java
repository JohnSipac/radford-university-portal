package main.java.edu.johnlab.radforduniversity;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;
import java.sql.SQLException;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {

        try {
            UserAuth.getConnectionDataBase();
            System.out.println("Conectado!");

        } catch (SQLException e) {
            System.out.println("Error en la conexión"+ e.getMessage());
        }
        launch();

    }

}
