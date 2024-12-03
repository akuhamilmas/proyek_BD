package com.example.proyek2;

import com.example.proyek2.material.MaterialJumlah;
import com.example.proyek2.pembelian.PembelianData;
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

public class topcustomerController {
    @FXML
    private Button btnback;

    @FXML
    private TableColumn<PembelianData, Integer> tblvjumlahpembelian;

    @FXML
    private TableColumn<PembelianData, String> tblvnamapembeli;

    @FXML
    private TableView<PembelianData> tblvtopcustomer;

    @FXML
    void initialize() {
        Connection conn = null;
        ObservableList<PembelianData> data = FXCollections.observableArrayList();
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
            ResultSet rs = stmt.executeQuery(
                    "SELECT nama_pembeli, COUNT(*) as TotalPembelian " +
                            "FROM pembelian " +
                            "GROUP BY nama_pembeli " +
                            "ORDER BY TotalPembelian DESC " +
                            "LIMIT 3");


            // Get the results
            while (rs.next()) {
                data.add(new PembelianData(rs.getString("nama_pembeli"), rs.getInt("TotalPembelian")));
            }
            // Set the cell value factories
            tblvnamapembeli.setCellValueFactory(new PropertyValueFactory<PembelianData, String>("nama_pembeli"));
            tblvjumlahpembelian.setCellValueFactory(new PropertyValueFactory<PembelianData, Integer>("TotalPembelian"));

            // Add the data to the table
            tblvtopcustomer.setItems(data);
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
