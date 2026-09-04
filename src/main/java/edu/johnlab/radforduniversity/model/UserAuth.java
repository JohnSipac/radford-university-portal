package main.java.edu.johnlab.radforduniversity.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class UserAuth {

    //atributos para conexión a base de datos
    public static final String DATA_BASE = System.getenv("DATA_BASE");
    public static final String URL_DB = System.getenv("URL_MYSQL_DB") + DATA_BASE;
    public static final String USER_DB = System.getenv("USER_MYSQL_DB");
    public static final String PASS_DB = System.getenv("PASS_MYSQL_DB");

    private static Connection connection;

    //atributos del usuario
    private String idUser;
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String password;

    //constructor para un objeto usuario
    public UserAuth(String idUser, String name, String lastName, String userName, String email, String password) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public UserAuth() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    //obtener conexión a base de datos
    public static Connection getConnectionDataBase() throws Exception {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASS_DB);
        }
        return connection;
    }

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
