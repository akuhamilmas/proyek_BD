package com.example.proyek2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class topbulanController {
    @FXML
    private Button btnback;

    @FXML
    private TextField txtbulan;

    @FXML
    private TextField txttotaljumlah;

    @FXML
    void initialize() {
        Connection conn = null;
        try {
            // Assumed database url, username, and password
            String dbUrl = "jdbc:mysql://localhost:3306/proyek2";
            String username = "root";
            String password = "";

            // Create a connection to your database
            conn = DriverManager.getConnection(dbUrl, username, password);

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery("SELECT DATE_FORMAT(tanggal, '%Y-%m') AS Bulan, SUM(jumlah) as TotalJumlah " +
                    "FROM pembelian " +
                    "WHERE tanggal >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
                    "GROUP BY DATE_FORMAT(tanggal, '%Y-%m') " +
                    "ORDER BY TotalJumlah DESC " +
                    "LIMIT 1");

            // Get the first result
            if (rs.next()) {
                txtbulan.setText(rs.getString("Bulan"));
                txttotaljumlah.setText(Integer.toString(rs.getInt("TotalJumlah")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnbackadmin(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display_admin.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btnback.getScene().getWindow();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}