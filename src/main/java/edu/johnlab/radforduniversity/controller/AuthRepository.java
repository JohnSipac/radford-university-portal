package main.java.edu.johnlab.radforduniversity.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import main.java.edu.johnlab.radforduniversity.model.UserAuth;
import static main.java.edu.johnlab.radforduniversity.model.UserAuth.getConnectionDataBase;

public class AuthRepository {
    
    //método para encontrar usuario en la base de datos
    public UserAuth findUserByUserName(String userName) throws Exception {
        UserAuth usuario = null;
        String sql = "Select * from users where username = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, userName);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {

                usuario = new UserAuth(
                        rs.getString("id_user"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("hashed_password")
                );
            }
        } catch (Exception e) {
            System.out.println("No se encontró al usuario" + e.getMessage());
        }
        return usuario;
    }

    //método para guardar un usuario
    public boolean saveUser(String name, String lastName, String userName, String email, String password) throws Exception {
        boolean estado = false;
        UUID idUnico = UUID.randomUUID();

        String sql = "insert into users (id_user, name, last_name, username, email, hashed_password) values (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idUnico.toString());
            pstm.setString(2, name);
            pstm.setString(3, lastName);
            pstm.setString(4, userName);
            pstm.setString(5, email);
            pstm.setString(6, password);

            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                estado = true;
            }

        } catch (Exception e) {
            estado = false;
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
    
}
