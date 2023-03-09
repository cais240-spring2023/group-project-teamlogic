package edu.wsu.controller;

import edu.wsu.OldApp;
import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        OldApp.setRoot("primary");
    }
}
