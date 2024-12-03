package com.example.proyek2;
import com.example.proyek2.helper.DatabaseConnector;
import com.example.proyek2.material.Material;
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

public class list_materialController {
    @FXML
    private Button btnbacklistmaterial;
    @FXML
    private TableColumn<Material, Integer> tblvlistidmaterial;
    @FXML
    private TableColumn<Material, Integer> tblvlisthargamaterial;


    @FXML
    private TableView<Material> tableviewlistmaterial;

    @FXML
    private TableColumn<Material, String> tblvlistdeskripsi;

    @FXML
    private TableColumn<Material, String> tblvlistmaterial;


    public void initialize() throws SQLException {
        loaddatabasenamamaterial();

    }

    private void loaddatabasenamamaterial() throws SQLException {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;

        ObservableList<Material> data = FXCollections.observableArrayList();

        try {
            con = DatabaseConnector.getConnection();
            System.out.println(con);
            statement = con.createStatement();
            String query = "SELECT id_material, nama_material, deskripsi, harga FROM material";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id_material = resultSet.getInt("id_material");
                String nama_material = resultSet.getString("nama_material");
                String deskripsi = resultSet.getString("deskripsi");
                Integer harga = resultSet.getInt("harga");

                data.add(new Material(id_material, nama_material, deskripsi, harga));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        tblvlistidmaterial.setCellValueFactory(new PropertyValueFactory<>("id_material"));
        tblvlistmaterial.setCellValueFactory(new PropertyValueFactory<>("nama_material"));
        tblvlistdeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        tblvlisthargamaterial.setCellValueFactory(new PropertyValueFactory<>("harga"));

        tableviewlistmaterial.setItems(data);

    }
    @FXML
    void btnbacklistmaterial(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display_user.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btnbacklistmaterial.getScene().getWindow();
            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}