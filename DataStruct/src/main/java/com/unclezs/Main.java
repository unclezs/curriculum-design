package com.unclezs;

import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 *@author unclezs.com
 *@date 2019.06.10 21:25
 */
public class Main extends Application {
    public static Stage mainStage=null;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("main.fxml"));
        Pane load = loader.load();
        Scene scene=new Scene(load);
        stage.setTitle("课程设计主页");
        stage.getIcons().add(new Image("main.png"));
        stage.setScene(scene);
        mainStage=stage;
        stage.show();
    }

}
