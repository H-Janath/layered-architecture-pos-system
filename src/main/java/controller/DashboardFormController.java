package controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane dashboardPane;
    public Label lblTime;


    public void initialize(){
        calculateTime();
    }

    private void calculateTime() {
        Timeline timeline=new Timeline(new KeyFrame(
                Duration.ZERO,
                ActionEvent->lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:MM:SS")))
        ),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void cutomerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"))));
        stage.setTitle("Customer Form");
        stage.show();
    }

    public void itemButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"))));
        stage.setTitle("Item Form");
        stage.show();
    }

    public void placeOrderButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)lblTime.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderForm1.fxml"))));
        stage.setTitle("Order Form");
        stage.show();
    }

    public void orderDetailsButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) lblTime.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderDetailsForm.fxml"))));
        stage.setTitle("Order details");
        stage.show();
    }
}
