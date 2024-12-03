package com.example.proyek2;

import com.example.proyek2.helper.DatabaseConnector;
import com.example.proyek2.material.Material;
import com.example.proyek2.pembelian.Pembelian;
import com.example.proyek2.pengiriman.Pengiriman;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class statusController {
    @FXML
    private TableView<Material> statustable;

    @FXML
    private Button btnbackstatus;

    @FXML
    private TableColumn<Pembelian, String> tblvlistbeli;

    @FXML
    private TableColumn<Pembelian, String> colNamaPembeli;

    @FXML
    private TableColumn<Pengiriman, Integer> tblvlistjumlah;

    @FXML
    private TableColumn<Pengiriman, Date> colTanggalPengiriman;

    @FXML
    private TableColumn<Pengiriman, String> tblvlisttglmaterial;
    @FXML
    private TextField txtjumlahmaterial;

    @FXML
    private TextField txtnamamaterial;

    @FXML
    private TextField txtnamapembeli;

    @FXML
    private TextField txttglpengiriman;

    private ObservableList<Pembelian> listPembelian = FXCollections.observableArrayList();
    private ObservableList<Pengiriman> listPengiriman = FXCollections.observableArrayList();
    private ObservableList<Object[]> data= FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        setDataPembelian();
        setDataPengiriman();
        loaddatabasestatus();
    }

    private void setDataPembelian() {
        try {
            Connection con = DatabaseConnector.getConnection();
            String query = "SELECT * FROM pembelian";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int idMaterial = rs.getInt("id_material");
                int idSupplier = rs.getInt("id_suplier");
                String namaPembeli = rs.getString("nama_pembeli");
                int jumlah = rs.getInt("jumlah");
                int harga = rs.getInt("harga");
                Date tanggal = rs.getDate("tanggal");

                Pembelian pembelian = new Pembelian(idMaterial, idSupplier, namaPembeli, jumlah, harga, tanggal);
                listPembelian.add(pembelian);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDataPengiriman() {
        try {
            Connection con = DatabaseConnector.getConnection();
            String query = "SELECT * FROM pengiriman";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int idPembelian = rs.getInt("id_pembelian");
                Date tanggalPengiriman = rs.getDate("tanggal_pengiriman");
                int jumlahPengiriman = rs.getInt("jumlah_pengiriman");

                Pengiriman pengiriman = new Pengiriman(idPembelian, null, tanggalPengiriman, jumlahPengiriman);
                listPengiriman.add(pengiriman);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnbackstatus(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display_user.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) btnbackstatus.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loaddatabasestatus() {
        try {
            Connection con = DatabaseConnector.getConnection();
            String query = "SELECT  p.tanggal , m.nama_material, p.jumlah, p.nama_pembeli FROM pembelian p, material m WHERE m.id_material=p.id_material ORDER BY p.id_pembelian DESC";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

//
            rs.next();
            System.out.println(rs.getString("tanggal"));
            txttglpengiriman.setText(rs.getString("tanggal"));
            txtnamamaterial.setText(rs.getString("nama_material"));
            txtjumlahmaterial.setText(rs.getString("jumlah"));
            txtnamapembeli.setText(rs.getString("nama_pembeli"));
//
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
