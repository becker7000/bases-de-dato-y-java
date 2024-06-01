package mx.ipn.app.repository;

import mx.ipn.app.model.Administrador;
import mx.ipn.app.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorRepositorioImp implements Repositorio<Administrador> {

    // Crear un método para la conexión a la base de datos.
    private Connection getConnection() throws SQLException {
        return ConexionBD.getInstance();
    }

    @Override
    public List<Administrador> listar() {
        List<Administrador> administradores = new ArrayList<>();
        try(Statement statement = getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administradores");
            while(resultSet.next()){
                Administrador administrador = crearAdministrador(resultSet);
                administradores.add(administrador);
            }
        } catch (SQLException e) {
            System.out.println("\n\t (listar) Error: "+e.getMessage());
        }
        return administradores;
    }

    @Override
    public Administrador porId(Integer id) {
        Administrador administrador = null;
        String query = "SELECT * FROM administradores WHERE idadministradores=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){ // Consultas simples.
                if (resultSet.next()) {
                    administrador = crearAdministrador(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.print("\n\t (porId) Error: "+e.getMessage());
        } // El preparedStatement y el resultSet se auto cierran con el bloque try.
        return administrador;
    }

    @Override
    public void guardar(Administrador administrador) { // Modifica o inserta registros.
        String query;
        if(administrador.getId()>0){ // Si el procuto ya existe
            query="UPDATE administradores SET nombre=?, contrasena=? WHERE idadministradores=?";
        }else{
            query="INSERT INTO administradores(nombre,contrasena) VALUES(?,?)";
        }

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setString(1,administrador.getNombre());
            preparedStatement.setString(2,administrador.getContrasena());
            if(administrador.getId()>0){
                preparedStatement.setInt(3, administrador.getId());
            }
            preparedStatement.executeUpdate(); // Modificaciones a la base de datos.
        } catch (SQLException e) {
            System.out.println("\n\t (guardar) Error: "+e.getMessage());
        }

    }

    @Override
    public void eliminar(Integer id) {
        String query="DELETE FROM administradores WHERE idadministradores=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate(); // Actualización a la base de datos.
        } catch (SQLException e) {
            System.out.println("\n\t (eliminar) Error: "+e.getMessage());
        }
    }

    // Método auxiliar:
    private Administrador crearAdministrador(ResultSet resultSet) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setId(resultSet.getInt("idadministradores"));
        administrador.setNombre(resultSet.getString("nombre"));
        administrador.setContrasena(resultSet.getString("contrasena"));
        return administrador;
    }

}
