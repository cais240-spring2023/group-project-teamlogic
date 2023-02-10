module edu.wsu {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.wsu to javafx.fxml;
    exports edu.wsu;
  exports edu.wsu.controller;
  opens edu.wsu.controller to javafx.fxml;
}
