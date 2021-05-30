package ejercicioemanuelbodo;

import java.sql.*;
import java.util.Scanner;

public class EjercicioEmanuelBodo {

    Connection con;

    private EjercicioEmanuelBodo() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tareasdb?serverTimezone=UTC", "root", "ejfb");

        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }

    public static int menu() {

        int seleccion;
        Scanner input = new Scanner(System.in);

        System.out.println(".::APP TASK::.");
        System.out.println(".::MENU::.");
        System.out.println("1 - Mostrar tareas");
        System.out.println("2 - Ingresar tarea");
        System.out.println("3 - Eliminar tarea");
        System.out.println("4 - Actualizar estado tarea");
        System.out.println("5 - Salir");

        System.out.println("Ingrese el numero : ");
        seleccion = input.nextInt();
        return seleccion;
    }

    public static void main(String[] args) {
        int seleccionUsuario = 0;

        do {
            seleccionUsuario = menu();

            switch (seleccionUsuario) {
                case 1:
                    mostrar();
                    break;
                case 2:
                    ingresar();
                    break;
                case 3:
                    eliminar();
                    break;
                case 4:
                    actualizar();
                    break;

            }
        } while (seleccionUsuario != 5);

    }

    private static void mostrar() {
        EjercicioEmanuelBodo mt = new EjercicioEmanuelBodo();
        Statement st;
        ResultSet rs;

        try {

            st = mt.con.createStatement();
            rs = st.executeQuery("SELECT * FROM tarea");

            while (rs.next()) {
                System.out.println(rs.getInt("idTarea") + " " + rs.getString("nombre"));
            }

            mt.con.close();

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER LOS DATOS");
        }

    }

    private static void ingresar() {
        EjercicioEmanuelBodo mt = new EjercicioEmanuelBodo();

        try {
            System.out.println("Ingresar separadamente nombre,descripción y estado de tarea:");

            Scanner input = new Scanner(System.in);

            String query = "insert into tarea(nombre,descripcion,estado)" + "values (?,?,?)";
            PreparedStatement ps = mt.con.prepareStatement(query);
            String nombre = input.next();
            ps.setString(1, nombre);
            String descripcion = input.next();
            ps.setString(2, descripcion);
            String estado = input.next();
            ps.setString(3, estado);

            ps.execute();
            mt.con.close();

        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }

    }

    private static void eliminar() {
        EjercicioEmanuelBodo mt = new EjercicioEmanuelBodo();
        Statement st;

        try {
            System.out.println("Ingresar número ID de la tarea a eliminar:");

            Scanner input = new Scanner(System.in);

            st = mt.con.createStatement();

            String query = "DELETE FROM tarea WHERE idTarea = ?";
            PreparedStatement ps = mt.con.prepareStatement(query);
            int numeroTarea = input.nextInt();
            ps.setInt(1, numeroTarea);

            int resultCount = ps.executeUpdate();
            System.out.println("OK " + resultCount);

            mt.con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private static void actualizar() {
        EjercicioEmanuelBodo mt = new EjercicioEmanuelBodo();
        Statement st;

        try {
            System.out.println("Ingresar separadamente estado nuevo y número ID de la tarea que va a ser actualizada:");

            Scanner input = new Scanner(System.in);
            st = mt.con.createStatement();

            String query = "UPDATE tarea SET estado = ? WHERE idTarea = ?";
            PreparedStatement ps = mt.con.prepareStatement(query);
            String nombre = input.next();
            ps.setString(1, nombre);
            int numeroTarea = input.nextInt();
            ps.setInt(2, numeroTarea);

            int resultRowCount = ps.executeUpdate();

            if (resultRowCount > 0) {
                System.out.println("OK -> " + resultRowCount);
            } else {
                System.out.println("No se modifico ningun registro");
            }

            mt.con.close();

        } catch (SQLException e) {
            System.out.println("ERROR " + e);
        }

    }
}
