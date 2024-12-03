package com.example.proyek2;


import com.example.proyek2.helper.DatabaseConnector;
import com.example.proyek2.pembelian.Pembelian;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Statement;
import java.io.IOException;
import java.sql.*;

public class pembelianController {
    @FXML
    private Button btncheckouttransaksi;

    @FXML
    private TextField txtbiayatransaksi;

    @FXML
    private TextField txtjumlahbarangtransaksi;

    @FXML
    private TextField txtnamabarangtransaksi;

    @FXML
    private TextField txtnamapembelitransaksi;

    @FXML
    private TextField txtnamasupliertransaksi;

    @FXML
    private TextField txtperpricetransaksi;

    public void initialize() {
        txtnamabarangtransaksi.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int hargaMaterial = getHargaMaterial(newValue);
                txtperpricetransaksi.setText(String.valueOf(hargaMaterial));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        txtjumlahbarangtransaksi.textProperty().addListener((observable, oldValue, newValue) -> {
            calculateBiaya();
        });
    }

    public void setNamaBarang(String namaBarang) {
        txtnamabarangtransaksi.setText(namaBarang);
    }

    public void setNamaSupplier(String namaSupplier) {
        txtnamasupliertransaksi.setText(namaSupplier);
    }

    private int getHargaMaterial(String namaMaterial) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int harga = 0;

        try {
            con = DatabaseConnector.getConnection();
            String query = "SELECT harga FROM material WHERE nama_material = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, namaMaterial);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                harga = resultSet.getInt("harga");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return harga;
    }

    private void calculateBiaya() {
        String jumlahBarangStr = txtjumlahbarangtransaksi.getText();
        String perPriceStr = txtperpricetransaksi.getText();

        System.out.println("jumlah"+jumlahBarangStr);
        try {
            int jumlahBarang = Integer.parseInt(jumlahBarangStr);
            int perPrice = Integer.parseInt(perPriceStr);
            int biaya = jumlahBarang * perPrice;
            txtbiayatransaksi.setText(String.valueOf(biaya));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCheckout(ActionEvent event) {
        String namaBarang = txtnamabarangtransaksi.getText();
        String namaPembeli = txtnamapembelitransaksi.getText();
        String namaSupplier = txtnamasupliertransaksi.getText();
        String jumlahBarangStr = txtjumlahbarangtransaksi.getText();
        String perPriceStr = txtperpricetransaksi.getText();
        String biayaStr = txtbiayatransaksi.getText();
        try {
            int idMaterial = getIdMaterial(namaBarang);
            int idSupplier = getIdSupplier(namaSupplier);
            int jumlahBarang = Integer.parseInt(jumlahBarangStr);
            int harga = Integer.parseInt(perPriceStr);
            int biaya = Integer.parseInt(biayaStr);

            Date tanggal = new Date(System.currentTimeMillis()); // Tambahkan ini

            Pembelian pembelian = new Pembelian(idMaterial, idSupplier, namaPembeli, jumlahBarang, harga, tanggal); // Ubah ini

            savePembelian(pembelian, biaya);

            clearInputFields();


            try {
                // Load the main menu scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("status.fxml"));
                Parent root = loader.load();
                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                // Get reference to current window
                Stage currentStage = (Stage) btncheckouttransaksi.getScene().getWindow();

                // Close the current window
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdMaterial(String namaMaterial) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int idMaterial = 0;

        try {
            con = DatabaseConnector.getConnection();
            String query = "SELECT id_material FROM material WHERE nama_material = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, namaMaterial);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                idMaterial = resultSet.getInt("id_material");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return idMaterial;
    }

    private int getIdSupplier(String namaSupplier) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int idSupplier = 0;

        try {
            con = DatabaseConnector.getConnection();
            String query = "SELECT id_suplier FROM suplier WHERE nama_suplier = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, namaSupplier);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                idSupplier = resultSet.getInt("id_suplier");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return idSupplier;
    }


    private void savePembelian(Pembelian pembelian, int biaya) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        try {
            con = DatabaseConnector.getConnection();
            String query = "INSERT INTO pembelian (id_material, id_suplier, nama_pembeli, jumlah, harga, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, pembelian.getId_material());
            pstmt.setInt(2, pembelian.getId_suplier());
            pstmt.setString(3, pembelian.getNama_pembeli());
            pstmt.setInt(4, pembelian.getJumlah());
            pstmt.setInt(5, pembelian.getHarga());
            pstmt.setDate(6, new java.sql.Date(pembelian.getTanggal().getTime()));
            pstmt.executeUpdate();

            generatedKeys = pstmt.getGeneratedKeys();
            int id_pembelian = 0;
            if (generatedKeys.next()) {
                id_pembelian = generatedKeys.getInt(1);
            }

            query = "INSERT INTO pengiriman (id_pembelian,nama_pembeli, tanggal_pengiriman, jumlah_pengiriman) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, id_pembelian);
            pstmt.setString(2, pembelian.getNama_pembeli());
            pstmt.setDate(3, new java.sql.Date(pembelian.getTanggal().getTime()));
            pstmt.setInt(4, pembelian.getJumlah());
            pstmt.executeUpdate();

            System.out.println("Pembelian saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    private void clearInputFields() {
        txtnamabarangtransaksi.clear();
        txtnamapembelitransaksi.clear();
        txtnamasupliertransaksi.clear();
        txtjumlahbarangtransaksi.setText("0");
        txtperpricetransaksi.clear();
        txtbiayatransaksi.clear();
    }
}