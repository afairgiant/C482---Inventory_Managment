module inventory_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens model to javafx.fxml;
    opens main to javafx.fxml;
    opens controller to javafx.fxml;

    exports model;
    exports main;
    exports controller;
}