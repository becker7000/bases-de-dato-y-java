package mx.ipn.app;

import mx.ipn.app.model.Administrador;
import mx.ipn.app.repository.AdministradorRepositorioImp;
import mx.ipn.app.repository.Repositorio;
import mx.ipn.app.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionAdministradoresTest {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        int opcion = 0;

        try(Connection connection = ConexionBD.getInstance()) { // Controlamos la exception tipo SQLException:

            Repositorio<Administrador> administradorRepositorio = new AdministradorRepositorioImp();

            while(opcion!=6){

                do{ // Filtro para opciones del 1 al 6.
                    System.out.print("\n\t +------------------------------+");
                    System.out.print("\n\t | Menú:                        |");
                    System.out.print("\n\t | 1. Ver administradores       |");
                    System.out.print("\n\t | 2. Buscar por id             |");
                    System.out.print("\n\t | 3. Crear administrador       |");
                    System.out.print("\n\t | 4. Modificar administrador   |");
                    System.out.print("\n\t | 5. Eliminar administrador    |");
                    System.out.print("\n\t | 6. Salir                     |");
                    System.out.print("\n\t +------------------------------+");
                    System.out.print("\n\t | Opción: ");
                    opcion = Integer.parseInt(entrada.nextLine());
                }while(opcion<1 || opcion>6);

                switch (opcion){
                    case 1 -> {
                        // Probando el método listar()
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Listando administradores   |");
                        administradorRepositorio.listar().forEach(Administrador::mostrar); // :: Operador de resolución del alcance
                    }
                    case 2 -> {
                        // Probando el método porId(Integer)
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Buscando por id            |");
                        System.out.print("\n\t | id: ");
                        try{
                            administradorRepositorio.porId(Integer.parseInt(entrada.nextLine())).mostrar();
                            System.out.print("\n\t | Administrador encontrado   |");
                            System.out.print("\n\t +----------------------------+");
                        }catch (NullPointerException exception){
                            System.out.print("\n\t | (Caso 2) Error: "+exception.getMessage());
                            System.out.print("\n\t | Administrador no encontrado |");
                            System.out.print("\n\t +-----------------------------+");
                        }
                    }
                    case 3 -> {
                        // Probando el método guardar() con INSERT
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Creando administrador      |");
                        Administrador administrador = new Administrador();
                        System.out.print("\n\t | Nombre: ");
                        administrador.setNombre(entrada.nextLine());
                        System.out.print("\n\t | Contraseña: ");
                        administrador.setContrasena(entrada.nextLine());
                        administradorRepositorio.guardar(administrador);
                        System.out.print("\n\t | Administrador guardado     |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 4 -> {
                        // Probando el método guardar() con UPDATE
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Modificando administrador  |");
                        Administrador administrador = new Administrador();
                        System.out.print("\n\t | Id: ");
                        administrador.setId(Integer.parseInt(entrada.nextLine()));
                        System.out.print("\n\t | Nombre: ");
                        administrador.setNombre(entrada.nextLine());
                        System.out.print("\n\t | Contraseña: ");
                        administrador.setContrasena(entrada.nextLine());
                        administradorRepositorio.guardar(administrador);
                        System.out.print("\n\t | Administrador actualizado  |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 5 -> {
                        // Probando el método eliminar()
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Eliminando administrador   |");
                        System.out.print("\n\t | Id: ");
                        administradorRepositorio.eliminar(Integer.parseInt(entrada.nextLine()));
                        System.out.print("\n\t | Administrador eliminado    |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 6 -> {
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Saliendo, vuela pronto...  |");
                        System.out.print("\n\t +----------------------------+");
                    }
                }

                System.out.print("\n\t Da [ENTER] para continuar.");
                entrada.nextLine(); // Generar una pausa hasta dar enter

            }

        } catch (SQLException e) {
            System.out.print("\n\t (Connection Test) Error: "+e.getMessage());
        }

    }

}
