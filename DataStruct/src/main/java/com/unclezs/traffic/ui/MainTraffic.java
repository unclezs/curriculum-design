package com.unclezs.traffic.ui;

import com.jfoenix.controls.JFXTabPane;
import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 *@author unclezs.com
 *@date 2019.06.16 10:41
 */
public class MainTraffic extends Application {
    public static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("traffic/traffic.fxml"));
        JFXTabPane load = loader.load();
        Scene scene=new Scene(load);
        stage.setTitle("全国交通咨询模拟");
        stage.getIcons().add(new Image("traffic/train.jpg"));
        stage.setScene(scene);
        stage.show();
    }
}
