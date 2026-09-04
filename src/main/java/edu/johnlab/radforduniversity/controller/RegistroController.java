package main.java.edu.johnlab.radforduniversity.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;
import main.java.edu.johnlab.radforduniversity.utils.security.jbcrypt.BCrypt;

public class RegistroController implements Initializable {

    private UserAuth userModel;
    private SceneManager sceneManager;

    public RegistroController(UserAuth userModel, SceneManager sceneManager) {
        this.userModel = userModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private TextField txtFieldName;
    
    @FXML
    private TextField txtFieldLastName;

    @FXML
    private TextField txtFieldUser;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField passFieldPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void goToLogin() throws Exception {
        sceneManager.showLoginView();
    }

    public void saveUser() throws Exception {
        if (txtFieldName.getText().isEmpty() || txtFieldLastName.getText().isEmpty() || txtFieldUser.getText().isEmpty() || txtFieldEmail.getText().isEmpty() || passFieldPassword.getText().isEmpty()) {
            sceneManager.showInfoAlert("Campos faltantes", "Revisar información", "Uno o más campos están vacíos... ¯|_(ツ)_/¯", AlertType.WARNING);
        } else {
            try {
                String contrasenaHash = BCrypt.hashpw(passFieldPassword.getText(), BCrypt.gensalt(12));

                boolean registrado = userModel.saveUser(txtFieldName.getText(), txtFieldLastName.getText(), txtFieldUser.getText(), txtFieldEmail.getText(), contrasenaHash);

                if (registrado) {
                    sceneManager.showInfoAlert("Información de Registro", "Usuario guardado correctamente", "Operación exitosa", AlertType.INFORMATION);
                    goToLogin();
                } else {
                    sceneManager.showInfoAlert("Información de Reistro", "No se pudo guardar el usuario", "Intenta de nuevo.", AlertType.INFORMATION);
                }

            } catch (RuntimeException e) {
                sceneManager.showInfoAlert("Información de Reistro", "No se pudo guardar el usuario", "Intenta de nuevo", AlertType.INFORMATION);
            }

        }
    }

    public void cancelar() throws Exception {
        goToLogin();

    }

}
