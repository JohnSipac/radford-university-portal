package main.java.edu.johnlab.radforduniversity.utils.sceneManager;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.edu.johnlab.radforduniversity.controller.LoginController;
import main.java.edu.johnlab.radforduniversity.controller.MainMenuController;
import main.java.edu.johnlab.radforduniversity.controller.RegistroController;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;

public class SceneManager {

    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));

        loader.setControllerFactory(
                clazz -> {
                    if (clazz == LoginController.class) {
                        UserAuth userAuth = new UserAuth();
                        return new LoginController(userAuth, this);
                    }
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor " + e.getMessage());
                    }
                }
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "registro-view.fxml"));

        loader.setControllerFactory(
                clazz -> {
                    if (clazz == RegistroController.class) {
                        UserAuth userAuth = new UserAuth();
                        return new RegistroController(userAuth, this);
                    }
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor " + e.getMessage());
                    }
                }
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public void showMainMenuView(UserAuth loggedUser) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "main-menu-view.fxml"));

        loader.setControllerFactory(clazz -> {
            if (clazz == MainMenuController.class) {
                MainMenuController mainMenuController = new MainMenuController(loggedUser, this);

                javafx.application.Platform.runLater(() -> mainMenuController.initData());

                return mainMenuController;
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    //ventana para mostrar alertas
    public void showInfoAlert(String title, String head, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(this.primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        alert.showAndWait();
        
    }

}
