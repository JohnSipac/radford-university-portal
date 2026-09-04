package main.java.edu.johnlab.radforduniversity.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.utils.security.jbcrypt.BCrypt;

public class LoginController implements Initializable {

    private UserAuth userModel;
    private final SceneManager sceneManager;

    public LoginController(UserAuth userModel, SceneManager sceneManager) {
        this.userModel = userModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private TextField txtFieldUser;

    @FXML
    private PasswordField passFieldPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void handleTestDataBaseConnection() throws Exception {
        try {
            UserAuth.getConnectionDataBase();
            System.out.println("Conectado");

        } catch (Exception e) {
            System.out.println("error al conectar: " + e.getMessage());
        }
    }

    public void handleLogin() throws Exception {

        if (txtFieldUser.getText().isEmpty() || passFieldPassword.getText().isEmpty()) {
            sceneManager.showInfoAlert("Campos faltantes", "Falta llenar campos", "Uno o más campos están vacíos... ¯|_(ツ)_/¯", AlertType.WARNING);
        } else {
            try {
                UserAuth usuarioEncontrado = userModel.findUserByUserName(txtFieldUser.getText());

                if (usuarioEncontrado != null) {
                    String verificacion = usuarioEncontrado.getPassword();

                    boolean esValido = BCrypt.checkpw(passFieldPassword.getText(), verificacion);

                    if (esValido) {
                        sceneManager.showMainMenuView(usuarioEncontrado);
                    } else {
                        sceneManager.showInfoAlert("Datos incorrectos", "Revisa tu información", "Intenta de nuevo", Alert.AlertType.INFORMATION);
                    }
                } else {
                    sceneManager.showInfoAlert("Datos incorrectos", "Revisa tu información", "Intenta de nuevo", Alert.AlertType.INFORMATION);
                }

            } catch (RuntimeException e) {
                sceneManager.showInfoAlert("Error", "Ocurrió un error al iniciar sesión", "Intenta de nuevo", Alert.AlertType.INFORMATION);
            }

        }
    }

    public void handleRegister() throws Exception {
        sceneManager.showRegisterView();
    }

}
