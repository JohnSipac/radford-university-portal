package main.java.edu.johnlab.radforduniversity.controller;

import java.util.UUID;
import main.java.edu.johnlab.radforduniversity.model.Book;
import java.sql.PreparedStatement;
import static main.java.edu.johnlab.radforduniversity.model.UserAuth.getConnectionDataBase;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookRepository {

    private ObservableList<Book> listaLibros = FXCollections.observableArrayList();

    public boolean create(Book book) throws SQLException {
        UUID idUnico = UUID.randomUUID();
        boolean estado = false;

        String sql = "insert into books values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, book.getIsbn());
            pstm.setString(2, book.getIdCategory());
            pstm.setString(3, book.getIdUser());
            pstm.setString(4, book.getTitle());
            pstm.setString(5, book.getAuthor());
            pstm.setString(6, book.getPublisher());
            pstm.setInt(7, book.getPublicationYear());
            pstm.setInt(8, book.getAvailableCopies());

            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                estado = true;
            }

        } catch (Exception e) {
            System.out.println("Error al añadir libro: " + e.getMessage());
        }
        return estado;
    }

    public ObservableList<Book> findAll(String idCategory) {
        listaLibros.clear();
        String sql = "Select * from books where id_category = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idCategory);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                listaLibros.add(
                        new Book(
                                rs.getString("isbn"),
                                rs.getString("id_category"),
                                rs.getString("id_user"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("publisher"),
                                rs.getInt("publication_year"),
                                rs.getInt("available_copies")
                        ));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener libros: " + e.getMessage());

        }
        return listaLibros;
    }

    public boolean update(Book book) throws SQLException {
        boolean estado = false;

        String sql = "update books set id_category = ?, id_user = ?, title = ?, author = ?, publisher = ?, publication_year = ?, available_copies = ? where isbn = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, book.getIdCategory());
            pstm.setString(2, book.getIdUser());
            pstm.setString(3, book.getTitle());
            pstm.setString(4, book.getAuthor());
            pstm.setString(5, book.getPublisher());
            pstm.setInt(6, book.getPublicationYear());
            pstm.setInt(7, book.getAvailableCopies());
            pstm.setString(8, book.getIsbn());

            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                estado = true;
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar libro: " + e.getMessage());
        }
        return estado;
    }

    public boolean deleteById(String idLibro) throws SQLException {
        boolean estado = false;
        String sql = "delete from books where isbn = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idLibro);

            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                estado = true;
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
        }
        return estado;
    }

}
