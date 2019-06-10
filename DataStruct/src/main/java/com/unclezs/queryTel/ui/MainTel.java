package com.unclezs.queryTel.ui;

import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 *@author unclezs.com
 *@date 2019.06.10 10:50
 */
public class MainTel extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("queryTel/queryTel.fxml"));
        Pane load = loader.load();
        Scene scene=new Scene(load);
        stage.setTitle("电话号码查询系统");
        stage.getIcons().add(new Image("queryTel/tel.jpg"));
        stage.setScene(scene);
        stage.show();
    }
}
