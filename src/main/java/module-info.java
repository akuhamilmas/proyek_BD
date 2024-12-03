module com.example.proyek2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens com.example.proyek2.suplier to javafx.base;
    opens com.example.proyek2.stock to javafx.base;
    opens com.example.proyek2.material to javafx.base;
    opens com.example.proyek2.pembelian to javafx.base;
    opens com.example.proyek2 to javafx.fxml;
    exports com.example.proyek2;
}