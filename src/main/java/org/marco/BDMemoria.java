package org.marco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDMemoria {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:test";
        String user = "sa";
        String passwd = "";

        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {

            crearTabla(conn);
            crearEstudiantes(conn);
            consultarEstudiantes(conn);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }   // main

    private static void consultarEstudiantes(Connection conn) {
        String query = "SELECT * FROM ESTUDIANTES e";

        try (Statement statement = conn.createStatement()) {
            ResultSet res = statement.executeQuery(query);

            while (res.next()) {
                System.out.print("ID: " + res.getInt("id"));
                System.out.print(" NOMBRE: " + res.getString("nombre"));
                System.out.println(" EDAD: " + res.getInt("edad"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void crearEstudiantes(Connection conn) {
        String query = "INSERT INTO ESTUDIANTES(id, nombre, edad) values (?,?,?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            for (int i = 0; i < 5; i++) {
                statement.setInt(1, i + 1);
                statement.setString(2, "Alumno " + i);
                statement.setInt(3, 15 + i);
                statement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void crearTabla(Connection conn) {
        String query = "CREATE TABLE ESTUDIANTES(id INT PRIMARY KEY, nombre VARCHAR(100), edad INT)";

        try (Statement statement = conn.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
