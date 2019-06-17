package com.unclezs.tree.ui;

import com.unclezs.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 *@author unclezs.com
 *@date 2019.06.16 10:41
 */
public class MainSTree extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("tree/tree.fxml"));
        Pane load = loader.load();
        Scene scene=new Scene(load);
        stage.setTitle("二叉树");
        stage.getIcons().add(new Image("tree/tree.jpg"));
        stage.setScene(scene);
        stage.show();
    }
}
