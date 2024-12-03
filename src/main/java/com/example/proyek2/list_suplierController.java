package com.example.proyek2;

import com.example.proyek2.helper.DatabaseConnector;
import com.example.proyek2.suplier.Suplier;
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
public class list_suplierController {



    @FXML
    private Button btnbacklistsuplier;

    @FXML
    private TableView<Suplier> tabelviewsuplier;
    @FXML
    private TableColumn<Suplier, Integer> tblvidsuplier;


    @FXML
    private TableColumn<Suplier, String> tblvalamatsuplier;

    @FXML
    private TableColumn<Suplier, String> tblvkotasuplier;

    @FXML
    private TableColumn<Suplier, String> tblvnamasuplier;

    @FXML
    private TableColumn<Suplier, String> tblvteleponsuplier;

    public void initialize() throws SQLException {
        loaddatabasenamasuplier();
    }


    private void loaddatabasenamasuplier() throws SQLException {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;

        ObservableList<Suplier> data = FXCollections.observableArrayList();

        try {
            con = DatabaseConnector.getConnection();
            System.out.println(con);
            statement = con.createStatement();
            String query = "SELECT id_suplier, nama_suplier, alamat, kota, telepon FROM suplier";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id_suplier = resultSet.getInt("id_suplier");
                String nama_suplier = resultSet.getString("nama_suplier");
                String alamat = resultSet.getString("alamat");
                String kota = resultSet.getString("kota");
                String telepon = resultSet.getString("telepon");

                data.add(new Suplier(id_suplier, nama_suplier, alamat, kota, telepon));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        tblvidsuplier.setCellValueFactory(new PropertyValueFactory<>("id_suplier"));
        tblvnamasuplier.setCellValueFactory(new PropertyValueFactory<>("nama_suplier"));
        tblvalamatsuplier.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        tblvkotasuplier.setCellValueFactory(new PropertyValueFactory<>("kota"));
        tblvteleponsuplier.setCellValueFactory(new PropertyValueFactory<>("telepon"));

        tabelviewsuplier.setItems(data);
    }
    @FXML
    void btnbacksuplier(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display_user.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btnbacklistsuplier.getScene().getWindow();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
