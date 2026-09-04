package main.java.edu.johnlab.radforduniversity.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;

public class MainMenuController implements Initializable {

    private UserAuth userModel;
    private SceneManager sceneManager;

    public MainMenuController(UserAuth userModel, SceneManager sceneManager) {
        this.userModel = userModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private Label lblWelcomeUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblWelcomeUser.setText("Hola");
        
        cbCategorias.getItems().addAll(
        "Ciencia Ficción", 
        "Misterio y Suspenso", 
        "Novela Histórica", 
        "Filosofía", 
        "Ciencia y Naturaleza",
        "Literatura Clásica"
    );
    }

    public void initData() {
        if (userModel != null && userModel.getName() != null) {
            lblWelcomeUser.setText("Bienvenido " + userModel.getName());
        } else {
            lblWelcomeUser.setText("Bienvenido Usuario");
        }

    }

    public void handleLogout() throws Exception {
        sceneManager.showLoginView();
    }

    @FXML
    private ComboBox<String> cbCategorias;

    @FXML
    private Label lblCategorias;

    public void mostrarCategorias(ActionEvent event) {
        lblCategorias.setVisible(true);
        cbCategorias.setVisible(true);
    }

    /* @FXML
    private AnchorPane mainContainer;
    @FXML
    private BorderPane centerContainer; // El contenedor central

    @FXML
    private void handleGestionLibros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/registro-view.fxml"));

            // Le enseñamos a JavaFX cómo crear el RegistroController pasándole sus dependencias
            loader.setControllerFactory(clazz -> {
                if (clazz == RegistroController.class) {
                    return new RegistroController(userModel, sceneManager);
                }
                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Error al instanciar el controlador: " + e.getMessage());
                }
            });

            Node registerNode = loader.load();
            centerContainer.setCenter(registerNode);

        } catch (Exception e) {
            System.out.println("Error al cargar la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }
     */
}
