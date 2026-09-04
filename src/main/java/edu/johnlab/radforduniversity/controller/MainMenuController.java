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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.java.edu.johnlab.radforduniversity.model.Book;
import main.java.edu.johnlab.radforduniversity.model.Category;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.repository.BookRepository;
import main.java.edu.johnlab.radforduniversity.repository.CategoryRepository;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;

public class MainMenuController implements Initializable {

    private UserAuth userModel;
    private SceneManager sceneManager;
    private BookRepository bookRepository = new BookRepository();
    private CategoryRepository categoryRepository = new CategoryRepository();
    private Category category = new Category();

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

        tvColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tvColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tvColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvColumnPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        tvColumnPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tvColumnAvailableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        tvColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tvColumnIdUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));

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
    
    @FXML
    private ScrollPane scrollPaneLibros;

    public void mostrarCategorias(ActionEvent event) {
        lblCategorias.setVisible(true);
        cbCategorias.setVisible(true);
    }

    @FXML
    private TableView<Book> tvBooks;
    @FXML
    private TableColumn<Book, String> tvColumnIsbn;
    @FXML
    private TableColumn<Book, String> tvColumnTitle;
    @FXML
    private TableColumn<Book, String> tvColumnAuthor;
    @FXML
    private TableColumn<Book, String> tvColumnPublisher;
    @FXML
    private TableColumn<Book, Integer> tvColumnPublicationYear;
    @FXML
    private TableColumn<Book, Integer> tvColumnAvailableCopies;
    @FXML
    private TableColumn<Book, String> tvColumnIdCategory;
    @FXML
    private TableColumn<Book, String> tvColumnIdUser;

    public void handleLoadTableBooks() {
        scrollPaneLibros.setVisible(true);
        try {
            String categoriaSeleccionada = cbCategorias.getValue();
            if (categoriaSeleccionada == null) {
                return;
            }

            // ASIGNACIÓN CORRECTA: Se guarda el objeto devuelto por el repositorio
            this.category = categoryRepository.findByCategoryName(categoriaSeleccionada);

            if (this.category != null) {
                String idCategoria = this.category.getIdCategory();
                tvBooks.setItems(bookRepository.findAll(idCategoria));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla: " + e.getMessage());
        }
    }
}
