package com.example.proyek2;


import com.example.proyek2.helper.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class display_userController {

    @FXML
    private ComboBox<String> cmbnamabarang;

    @FXML
    private ComboBox<String> cmbnamasuplier;

    @FXML
    private Button btnlihatmaterial;
    @FXML
    private Button btnlihatsuplier;
    @FXML
    private Button btnorder;


    public void initialize() throws SQLException {
        loaddatabasenamasuplier();
        loaddatabasematerial();
    }

    @FXML // handle button lihat material
    void handleButtonAction(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listmaterial.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btnlihatmaterial.getScene().getWindow();
            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleButtonlihatsuplier(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listsuplier.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btnlihatsuplier.getScene().getWindow();
            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleButtonorder(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transaksi.fxml"));
            Parent root = loader.load();

            // Mendapatkan objek controller dari scene yang di-load
            pembelianController transaksiController = loader.getController();

            // Mengambil nilai yang dipilih dari ComboBox nama_barang dan nama_supplier
            String selectedBarang = cmbnamabarang.getValue();
            String selectedSuplier = cmbnamasuplier.getValue();

            // Mengirim nilai yang dipilih ke controller transaksiController
            transaksiController.setNamaBarang(selectedBarang);
            transaksiController.setNamaSupplier(selectedSuplier);

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Get reference to current window
            Stage currentStage = (Stage) btnorder.getScene().getWindow();
            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loaddatabasenamasuplier() throws SQLException {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;
        try {
            // Buat koneksi ke database
            con = DatabaseConnector.getConnection();
            // Buat pernyataan SQL
            statement = con.createStatement();
            String query1 = "SELECT nama_suplier FROM suplier";
            resultSet = statement.executeQuery(query1);

            // Bersihkan item di ComboBox
            cmbnamasuplier.getItems().clear();

            // Ambil data dari ResultSet dan tambahkan ke ComboBox
            while (resultSet.next()) {
                String namasuplier = resultSet.getString("nama_suplier");
                // Tambahkan data ke ComboBox
                cmbnamasuplier.getItems().add(namasuplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }



    private void loaddatabasematerial() throws SQLException {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;
        try {
            // Buat koneksi ke database
            con = DatabaseConnector.getConnection();
            // Buat pernyataan SQL
            statement = con.createStatement();
            String query2 = "SELECT nama_material FROM material";
            resultSet = statement.executeQuery(query2);

            // Bersihkan item di ComboBox
            cmbnamabarang.getItems().clear();

            // Ambil data dari ResultSet dan tambahkan ke ComboBox
            while (resultSet.next()) {
                String namamaterial = resultSet.getString("nama_material");
                // Tambahkan data ke ComboBox
                cmbnamabarang.getItems().add(namamaterial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}