module edu.wsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens edu.wsu to javafx.fxml;
    exports edu.wsu;
  exports edu.wsu.controller;
  opens edu.wsu.controller to javafx.fxml;
    exports edu.wsu.model;
    opens edu.wsu.model to javafx.fxml;
}
