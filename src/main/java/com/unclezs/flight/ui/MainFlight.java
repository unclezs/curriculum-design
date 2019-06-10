package com.unclezs.flight.ui;


import com.unclezs.utils.GlobalValue;
import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/*
 *@author unclezs.com
 *@date 2019.06.03 14:32
 */
public class MainFlight extends Application {
    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GlobalValue.flightStage=primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("flight/flight.fxml"));
        Pane pane=loader.load();
        Scene scene=new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("航班订票系统");
        primaryStage.getIcons().add(new Image("/flight/flight.jpg"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
