package com.example.proyek2;

import com.example.proyek2.cabang.Cabang;
import com.example.proyek2.helper.DatabaseConnector;
import com.example.proyek2.material.Material;
import com.example.proyek2.stock.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class display_adminController {
    @FXML
    private Button btntopbulan;

    @FXML
    private Button btntopcustomer;

    @FXML
    private Button btntopmaterial;

    @FXML
    private Button btncreate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableView<Stock> tblvadmin;

    @FXML
    private TableColumn<Stock, Integer> tblvharga;

    @FXML
    private TableColumn<Stock, String> tblvindustri;

    @FXML
    private TableColumn<Stock, String> tblvnamamaterial;

    @FXML
    private TableColumn<Stock, Integer> tblvno;

    @FXML
    private TableColumn<Stock, Integer> tblvstock;

    @FXML
    private TableColumn<Stock, String> tblvcabang;


    @FXML
    private ComboBox<Cabang> cmbcabang;

    @FXML
    private TextField txtharga;

    @FXML
    private TextField txtindustri;

    @FXML
    private TextField txtmaterial;

    @FXML
    private TextField txtstock;

    public void initialize() throws SQLException {
        loaddatabaseadmin();

        tblvadmin.setRowFactory(tv -> {
            TableRow<Stock> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Stock rowData = row.getItem();
                    txtharga.setText(String.valueOf(rowData.getHarga()));
                    txtindustri.setText(rowData.getNamaIndustri());
                    txtmaterial.setText(rowData.getNamaMaterial());
                    txtstock.setText(String.valueOf(rowData.getStok()));
                }
            });
            return row;
        });
    }

    private void loaddatabaseadmin() throws SQLException {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;

        ObservableList<Stock> data = FXCollections.observableArrayList();

        try {
            con = DatabaseConnector.getConnection();
            System.out.println(con);
            statement = con.createStatement();
            String query = "SELECT material.id_material, material.nama_material, industri.nama_industri, material.harga, stok FROM stock JOIN material ON material.id_material = stock.id_material JOIN industri ON material.id_industri = industri.id_industri";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id_material = resultSet.getInt("id_material");
                String nama_material = resultSet.getString("nama_material");
                String nama_industri = resultSet.getString("nama_industri");
                Integer harga = resultSet.getInt("harga");
                Integer stok = resultSet.getInt("stok");

                data.add(new Stock(id_material, nama_material, nama_industri, harga, stok));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        tblvno.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));
        tblvnamamaterial.setCellValueFactory(new PropertyValueFactory<>("namaMaterial"));
        tblvindustri.setCellValueFactory(new PropertyValueFactory<>("namaIndustri"));
        tblvharga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        tblvstock.setCellValueFactory(new PropertyValueFactory<>("stok"));

        tblvadmin.setItems(data);
    }

    @FXML
    private void handleCreateButtonClicked(ActionEvent event) throws SQLException {
        String namaMaterial = txtmaterial.getText();
        String namaIndustri = txtindustri.getText();
        int harga = Integer.parseInt(txtharga.getText());
        int stok = Integer.parseInt(txtstock.getText());

        Connection con = null;
        Statement statement = null;

        try {
            con = DatabaseConnector.getConnection();
            statement = con.createStatement();

            // Check if the industry exists, if not create a new one
            String checkIndustriQuery = "SELECT id_industri FROM industri WHERE nama_industri = '" + namaIndustri + "'";
            ResultSet industriResultSet = statement.executeQuery(checkIndustriQuery);
            int idIndustri;
            if (industriResultSet.next()) {
                idIndustri = industriResultSet.getInt("id_industri");
            } else {
                // Insert new industry into the industry table
                String insertIndustriQuery = "INSERT INTO industri (nama_industri) VALUES ('" + namaIndustri + "')";
                statement.executeUpdate(insertIndustriQuery, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idIndustri = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating industry failed, no ID obtained.");
                }
            }

            // Check if the material exists, if not create a new one
            String checkMaterialQuery = "SELECT id_material FROM material WHERE nama_material = '" + namaMaterial + "'";
            ResultSet materialResultSet = statement.executeQuery(checkMaterialQuery);
            int idMaterial;
            if (materialResultSet.next()) {
                idMaterial = materialResultSet.getInt("id_material");
            } else {
                // Insert new material into the material table
                String insertMaterialQuery = "INSERT INTO material (nama_material, id_industri, harga) VALUES ('" + namaMaterial + "', " + idIndustri + ", " + harga + ")";
                statement.executeUpdate(insertMaterialQuery, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idMaterial = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating material failed, no ID obtained.");
                }
            }

            // Insert new stock record into the database
            String insertQuery = "INSERT INTO stock (id_material, stok) VALUES (" + idMaterial + ", " + stok + ")";
            statement.executeUpdate(insertQuery);



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Refresh table view
        loaddatabaseadmin();
    }

    @FXML
    private void handleUpdateButtonClicked(ActionEvent event) throws SQLException {
        Stock selectedStock = tblvadmin.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            int idMaterial = selectedStock.getIdMaterial();
            String namaMaterial = txtmaterial.getText();
            String namaIndustri = txtindustri.getText();
            int harga = Integer.parseInt(txtharga.getText());
            int stok = Integer.parseInt(txtstock.getText());

            Connection con = null;
            Statement statement = null;

            try {
                con = DatabaseConnector.getConnection();
                con.setAutoCommit(false);  // Start transaction

                statement = con.createStatement();

                // Check if the industry exists, if not create a new one
                String checkIndustriQuery = "SELECT id_industri FROM industri WHERE nama_industri = '" + namaIndustri + "'";
                ResultSet industriResultSet = statement.executeQuery(checkIndustriQuery);
                int idIndustri;
                if (industriResultSet.next()) {
                    idIndustri = industriResultSet.getInt("id_industri");
                } else {
                    // Insert new industry into the industry table
                    String insertIndustriQuery = "INSERT INTO industri (nama_industri) VALUES ('" + namaIndustri + "')";
                    statement.executeUpdate(insertIndustriQuery, Statement.RETURN_GENERATED_KEYS);
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idIndustri = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating industry failed, no ID obtained.");
                    }
                }

                // Update the material and stock
                String updateMaterialQuery = "UPDATE material SET nama_material = '" + namaMaterial + "', id_industri = " + idIndustri + ", harga = " + harga + " WHERE id_material = " + idMaterial;
                statement.executeUpdate(updateMaterialQuery);

                String updateStockQuery = "UPDATE stock SET stok = " + stok + " WHERE id_material = " + idMaterial;
                statement.executeUpdate(updateStockQuery);

                con.commit();  // If everything is successful, commit the transaction
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        con.rollback();  // If there is an error, rollback the transaction
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            }

            // Refresh table view
            loaddatabaseadmin();
        }
    }

    @FXML
    private void handleDeleteButtonClicked(ActionEvent event) throws SQLException {
        Stock selectedStock = tblvadmin.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            int idMaterial = selectedStock.getIdMaterial();

            Connection con = null;
            Statement statement = null;

            try {
                con = DatabaseConnector.getConnection();
                con.setAutoCommit(false);  // Start transaction

                statement = con.createStatement();

                // Delete selected stock record from the database
                String deleteStockQuery = "DELETE FROM stock WHERE id_material = " + idMaterial;
                statement.executeUpdate(deleteStockQuery);

                // Check if the material is used in any stock, if not delete it
                String checkStockQuery = "SELECT * FROM stock WHERE id_material = " + idMaterial;
                ResultSet stockResultSet = statement.executeQuery(checkStockQuery);
                if (!stockResultSet.next()) {
                    String deleteMaterialQuery = "DELETE FROM material WHERE id_material = " + idMaterial;
                    statement.executeUpdate(deleteMaterialQuery);
                }

                con.commit();  // If everything is successful, commit the transaction
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        con.rollback();  // If there is an error, rollback the transaction
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            }

            // Refresh table view
            loaddatabaseadmin();
        }
    }

    @FXML
    void btnmovetopbulan(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topbulan.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btntopbulan.getScene().getWindow();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnmovetotopmaterial(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topmaterial.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btntopmaterial.getScene().getWindow();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btntotopcustomer(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topcustomer.fxml"));
            Parent root = loader.load();
            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Get reference to current window
            Stage currentStage = (Stage) btntopcustomer.getScene().getWindow();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
