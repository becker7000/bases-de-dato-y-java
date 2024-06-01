package mx.ipn.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Modificar username a tu username
    // Modificar el password a tu password
    private static String url="jdbc:mysql://localhost:3306/e_commerce?serverTimezone=UTC";
    private static String username="root";
    private static String password="toor";
    private static Connection connection;

    // Este método es para que la conexión en el sistema sea una sola
    public static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }}
