package main.java.edu.johnlab.radforduniversity.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.edu.johnlab.radforduniversity.model.Book;
import main.java.edu.johnlab.radforduniversity.model.Category;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import main.java.edu.johnlab.radforduniversity.repository.BookRepository;
import main.java.edu.johnlab.radforduniversity.repository.CategoryRepository;
import main.java.edu.johnlab.radforduniversity.utils.sceneManager.SceneManager;

public class MainMenuController implements Initializable {

    private UserAuth userModel;
    private SceneManager sceneManager;
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private Category category;
    private Book libroSeleccionado;

    public MainMenuController(UserAuth userModel, SceneManager sceneManager, BookRepository bookRepository, CategoryRepository categoryRepository, Category category, Book libroSeleccionado) {
        this.userModel = userModel;
        this.sceneManager = sceneManager;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.category = category;
        this.libroSeleccionado = libroSeleccionado;
    }

    @FXML
    private Label lblWelcomeUser;
    @FXML
    private ComboBox<String> cbCategorias;
    @FXML
    private Label lblCategorias;
    @FXML
    private ScrollPane scrollPaneLibros;
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
    @FXML
    private VBox vBoxButtons;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblWelcomeUser.setText("Hola");

        tvColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tvColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tvColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvColumnPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        tvColumnPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tvColumnAvailableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        tvColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tvColumnIdUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        tvBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldBook, newBook) -> {
            if (newBook != null) {
                showButtons();
            } else {
                vBoxButtons.setVisible(false);
            }
        });

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

    public void mostrarCategorias(ActionEvent event) {
        lblCategorias.setVisible(true);
        cbCategorias.setVisible(true);
        showButtons();
    }

    public void handleLoadTableBooks() {
        scrollPaneLibros.setVisible(true);

        try {
            String categoriaSeleccionada = cbCategorias.getValue();
            if (categoriaSeleccionada == null) {
                return;
            }

            category = categoryRepository.findByCategoryName(categoriaSeleccionada);

            if (category != null) {
                String idCategoria = this.category.getIdCategory();
                tvBooks.setItems(bookRepository.findAll(idCategoria));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla: " + e.getMessage());
        }
    }

    public void showButtons() {
        vBoxButtons.setVisible(true);
    }

    public void handleCreateBook() throws Exception {
        sceneManager.showCreateBookView(userModel);
    }

    public void handleUpdateBook() throws Exception {
        libroSeleccionado = tvBooks.getSelectionModel().getSelectedItem();
        sceneManager.showUpdateBookView(userModel, libroSeleccionado);
    }

    public void handleDeleteBook() {
        libroSeleccionado = tvBooks.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            return;
        }

        try {
            boolean eliminado = bookRepository.deleteById(libroSeleccionado.getIsbn());

            if (eliminado) {
                tvBooks.getItems().remove(libroSeleccionado);
                tvBooks.getSelectionModel().clearSelection();
                sceneManager.showInfoAlert("Estado de eliminación", "Libro eliminado", "El libro se ha eliminado correctamente", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        }
    }
}
