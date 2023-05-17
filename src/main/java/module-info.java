module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.school_management to javafx.fxml;
    exports com.example.school_management;
    exports com.example.mainclass;
    opens com.example.mainclass to javafx.fxml;
    exports com.example.Controller;
    opens com.example.Controller to javafx.fxml;
    exports com.example.Subject;
    opens com.example.Subject to javafx.fxml;

    requires java.sql;
    requires jasperreports;

    requires poi.ooxml.plus;
    requires sstool.poi.expand;
    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires xmlbeans;
    requires poi;
    requires java.desktop;

}