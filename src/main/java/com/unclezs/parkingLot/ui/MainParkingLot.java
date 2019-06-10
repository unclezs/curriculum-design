package com.unclezs.parkingLot.ui;

import com.unclezs.dataStruct.MyQueue;
import com.unclezs.dataStruct.MyStack;
import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Date;

/*
 *@author unclezs.com
 *@date 2019.06.06 09:58
 */
public class MainParkingLot extends Application {
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("parkingLot/parking.fxml"));
        Pane load = loader.load();
        Scene scene=new Scene(load);
//        primaryStage.setResizable(false);
        primaryStage.setTitle("停车场模拟管理程序");
        primaryStage.getIcons().add(new Image("/parkingLot/parking.jpg"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
