module se.yrgo {
    requires javafx.controls;
    requires javafx.fxml;
    exports se.yrgo.tictactoe;
    exports se.yrgo.tictactoe.controllers;
    opens se.yrgo.tictactoe.controllers to javafx.fxml;
}
