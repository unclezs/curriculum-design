package com.unclezs.test;

import com.unclezs.dataStruct.MyQueue;
import com.unclezs.dataStruct.SortTree;
import com.unclezs.tree.DrawTree;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 *@author unclezs.com
 *@date 2019.06.14 12:40
 */
public class AnimationDomo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DrawTree d = new DrawTree();
        SortTree<Integer> sortTree = d.getST(false);
        MyQueue<SortTree<Integer>.Node<Integer>> queue = sortTree.inorder(sortTree.first());
        Circle circle = new Circle(20);
        circle.setCenterX(55);
        circle.setCenterY(55);
        Pane root = new Pane();
        root.getChildren().add(circle);
        Path path = new Path();
        double x = queue.peek().x;
        double y = queue.poll().y;
        path.getElements().add(new MoveTo(100, 100));
        path.getElements().add(new LineTo(200, 100));
        path.getElements().add(new LineTo(300, 200));
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(2000));//设置持续时间4秒
        pt.setPath(path);//设置路径
        pt.setNode(circle);//设置物体
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        //设置周期性，无线循环
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(false);//自动往复
        pt.play();//启动动画
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void drawShapes(GraphicsContext gc, Canvas canvas) {
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeOval(width / 2, 60, 30, 30);//空心圆
        gc.fillText("2", width / 2 + 10, 80);
        gc.strokeLine(width / 2 + 15, 90, width / 2 - 40, 140);
        gc.strokeOval(width / 2 - 55, 140, 30, 30);//空心圆
        gc.strokeLine(width / 2 + 15, 90, width / 2 + 70, 140);
        gc.strokeOval(width / 2 + 55, 140, 30, 30);//空心圆
    }

}
