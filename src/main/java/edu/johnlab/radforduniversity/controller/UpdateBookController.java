package main.java.edu.johnlab.radforduniversity.controller;

import main.java.edu.johnlab.radforduniversity.model.BookRepository;
import main.java.edu.johnlab.radforduniversity.model.CategoryRepository;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.edu.johnlab.radforduniversity.model.Book;
import main.java.edu.johnlab.radforduniversity.model.Category;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;

public class UpdateBookController implements Initializable {

    SceneManager sceneManager;
    UserAuth userModel;
    CategoryRepository categoryRepository;
    Category category;
    BookRepository bookRepository;
    Book libroSeleccionado;

    public UpdateBookController(SceneManager sceneManager, UserAuth userModel, CategoryRepository categoryRepository, Category category, BookRepository bookRepository, Book libroSeleccionado) {
        this.sceneManager = sceneManager;
        this.userModel = userModel;
        this.categoryRepository = categoryRepository;
        this.category = category;
        this.bookRepository = bookRepository;
        this.libroSeleccionado = libroSeleccionado;
    }

    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextField txtFieldTitle;
    @FXML
    private TextField txtFieldAuthor;
    @FXML
    private TextField txtFieldPublisher;
    @FXML
    private TextField txtFieldPublicationYear;
    @FXML
    private TextField txtFieldAvailableCopies;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategoria.getItems().addAll(
                "Ciencia Ficción",
                "Misterio y Suspenso",
                "Novela Histórica",
                "Filosofía",
                "Ciencia y Naturaleza",
                "Literatura Clásica"
        );
        
        Category nombreCategoria = categoryRepository.findByCategoryId(libroSeleccionado.getIdCategory());   
        cmbCategoria.setValue(nombreCategoria.getCategoryName());
        txtFieldTitle.setText(libroSeleccionado.getTitle());
        txtFieldAuthor.setText(libroSeleccionado.getAuthor());
        txtFieldPublisher.setText(libroSeleccionado.getPublisher());
        String anioPub = Integer.toString(libroSeleccionado.getPublicationYear());
        String disponibles = Integer.toString(libroSeleccionado.getAvailableCopies());
        txtFieldPublicationYear.setText(anioPub);
        txtFieldAvailableCopies.setText(disponibles);
        
    }

    public void handleSaveBook() {
        if (cmbCategoria.getValue() == null || txtFieldTitle.getText().isBlank() || txtFieldAuthor.getText().isBlank() || txtFieldPublisher.getText().isBlank()
                || txtFieldPublicationYear.getText().isBlank() || txtFieldAvailableCopies.getText().isBlank()) {
            sceneManager.showInfoAlert("Campos vacíos", "Falta llenar Campos", "Ingrese todos los campos correspondientes", Alert.AlertType.WARNING);
        } else {
            try {
                String idUnico = libroSeleccionado.getIsbn();
                String categoryName = cmbCategoria.getValue();
                String idUser = userModel.getIdUser();
                String title = txtFieldTitle.getText().trim();
                String author = txtFieldAuthor.getText().trim();
                String publisher = txtFieldPublisher.getText().trim();

                int publicationYear = Integer.parseInt(txtFieldPublicationYear.getText().trim());
                int availableCopies = Integer.parseInt(txtFieldAvailableCopies.getText().trim());

                Category catObj = categoryRepository.findByCategoryName(categoryName);
                if (catObj == null) {
                    sceneManager.showInfoAlert("Error de Categoría", "Categoría no encontrada", "La categoría seleccionada no existe en la base de datos.", Alert.AlertType.ERROR);
                    return;
                }

                String idCategory = catObj.getIdCategory();
                
                Book libroModificado = new Book(idUnico, idCategory, idUser, title, author, publisher, publicationYear, availableCopies);

                boolean guardado = bookRepository.update(libroModificado);

                if (guardado) {
                    sceneManager.showInfoAlert("Guardado", "Libro modificado", "El libro se guardó correctamente", Alert.AlertType.INFORMATION);
                    sceneManager.showMainMenuView(userModel);
                } else {
                    sceneManager.showInfoAlert("Error", "El libro no pudo ser guardado", "Intente de nuevo", Alert.AlertType.ERROR);
                }

            } catch (NumberFormatException e) {
                sceneManager.showInfoAlert("Error de tipo", "Verifique los campos de año de publicación y copias disponibles", "Ingrese solamente números", Alert.AlertType.ERROR);
            } catch (Exception e) {
                sceneManager.showInfoAlert("Error", "Ocurrió un fallo al guardar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public void handleCancelSave() throws Exception {
        sceneManager.showMainMenuView(userModel);
    }    
    
}
