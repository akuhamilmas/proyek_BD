package com.example.proyek2;

import com.example.proyek2.material.MaterialJumlah;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class topmaterialController {

    @FXML
    private Button btnback;

    @FXML
    private TableColumn<MaterialJumlah, String> tblnamamaterial;

    @FXML
    private TableColumn<MaterialJumlah, Integer> tbltotaljumlah;

    @FXML
    private TableView<MaterialJumlah> topmaterial;

    @FXML
    void initialize() {
        Connection conn = null;
        ObservableList<MaterialJumlah> data = FXCollections.observableArrayList();
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
            ResultSet rs = stmt.executeQuery("SELECT m.nama_material, SUM(p.jumlah) as TotalJumlah " +
                    "FROM pembelian p " +
                    "JOIN material m ON p.id_material = m.id_material " +
                    "GROUP BY m.id_material " +
                    "ORDER BY TotalJumlah DESC " +
                    "LIMIT 3");

            // Get the results
            while (rs.next()) {
                data.add(new MaterialJumlah(rs.getString("nama_material"), rs.getInt("TotalJumlah")));
            }
            // Set the cell value factories
            tblnamamaterial.setCellValueFactory(new PropertyValueFactory<MaterialJumlah, String>("nama_material"));
            tbltotaljumlah.setCellValueFactory(new PropertyValueFactory<MaterialJumlah, Integer>("totalJumlah"));

            // Add the data to the table
            topmaterial.setItems(data);
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