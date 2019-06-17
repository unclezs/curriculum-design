package com.unclezs.tree.ui;

import com.jfoenix.controls.JFXRadioButton;
import com.unclezs.dataStruct.MyQueue;
import com.unclezs.dataStruct.SortTree;
import com.unclezs.tree.DrawTree;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/*
 *@author unclezs.com
 *@date 2019.06.16 10:32
 */
public class TreeController implements Initializable {
    @FXML
    Pane root;//主容器
    @FXML
    Canvas canvas;//画布
    @FXML
    Button add;//添加节点
    @FXML
    Button remove;//删除节点
    @FXML
    Button query;//查找节点
    @FXML
    Button resetTree;//重置树
    @FXML
    Button randTree;//随机树
    @FXML
    Button inorder;//转平衡树
    @FXML
    TextField input;//输入框
    @FXML
    JFXRadioButton bs;//排序树
    @FXML
    JFXRadioButton rb;//红黑树
    @FXML
    HBox box;//遍历结果
    @FXML
    Label msg;//信息label
    @FXML
    Button asl;//计算asl
    //成员
    private final DrawTree dt = new DrawTree();//画树类
    SortTree<Integer> sortTree = new SortTree<>();//排序树
    final ImageView k = new ImageView("tree/k.jpg");//查找箭头
    final ImageView circle = new ImageView("tree/basketball.jpg");//遍历的球
    final PathTransition pt = new PathTransition();//动画

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化画笔
        DrawTree.gc = canvas.getGraphicsContext2D();
        //初始化事件
        add.setOnMouseClicked(e -> addNode());
        randTree.setOnMouseClicked(e -> randTree());
        resetTree.setOnMouseClicked(e -> resetTree());
        query.setOnMouseClicked(e -> queryNode());
        remove.setOnMouseClicked(e -> removeNode());
        asl.setOnMouseClicked(e->showASL());
        inorder.setOnMouseClicked(e -> {
            inorderTarverl();
        });
        input.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addNode();
            }
        });
        //初始化选择树
        ToggleGroup group = new ToggleGroup();
        rb.setToggleGroup(group);
        bs.setSelected(true);
        bs.setToggleGroup(group);
        group.selectedToggleProperty().addListener(e -> {
            resetTree();
        });

    }
    //画树
    void drawTree() {
        stopAnimation();
        //隐藏查找箭头
        k.setVisible(false);
        k.relocate(-111, -111);
        DrawTree.gc.clearRect(0, 0, root.getWidth(), root.getHeight());
        dt.draw(sortTree, canvas.getWidth());
    }
    //添加节点
    void addNode() {
        stopAnimation();
        if (!input.getText().equals("")) {
            int v = Integer.parseInt(input.getText());
            sortTree.add(v);
            sortTree.inorder(sortTree.first());
            drawTree();
            input.requestFocus();
            input.setText("");
        }
    }
    //随机树
    void randTree() {
        stopAnimation();
        sortTree = dt.getST(rb.isSelected());
        drawTree();
    }
    //重置树
    void resetTree() {
        stopAnimation();
        sortTree = new SortTree<>();
        //选择树类型
        if (bs.isSelected()) {
            sortTree.isFix = false;
        } else {
            sortTree.isFix = true;
        }
        drawTree();
    }
    //查找节点
    void queryNode() {
        String text=input.getText();
        try {
            stopAnimation();
            k.setVisible(true);
            int v = Integer.parseInt(text);
            SortTree<Integer>.Node<Integer> node = sortTree.getNode(v);
            k.relocate(node.x + canvas.getLayoutX() - 20, node.y - 20);
            root.getChildren().add(k);
        } catch (Exception e) {
            msg.setText("未找到节点："+text);
        }
    }
    //删除节点
    void removeNode() {
        stopAnimation();
        try {
            int v = Integer.parseInt(input.getText());
            sortTree.remove(v);
            drawTree();
        } catch (Exception e) {
        }
    }
    //中序遍历
    void inorderTarverl() {
        stopAnimation();
        //遍历后的队列
        MyQueue<SortTree<Integer>.Node<Integer>> queue = sortTree.inorder(sortTree.first(), null);
        int size = queue.size();
        if (size == 0) {
            return;
        }
        circle.setFitWidth(40);
        circle.setFitHeight(40);

        root.getChildren().add(circle);
        Path path = new Path();
        SortTree.Node node = queue.poll();
        double xpreOff = canvas.getLayoutX() + 20;//横坐标偏移
        double ypreOff = canvas.getLayoutY() + 20;//纵坐标偏移
        path.getElements().add(new MoveTo(node.x + xpreOff, node.y + ypreOff));
        while (queue.size() != 0) {

            box.getChildren().add(getLabel(node.getElement()));
            node = queue.poll();
            path.getElements().add(new LineTo(node.x + xpreOff, node.y + ypreOff));
        }
        pt.setDuration(Duration.millis(size * 1500));//设置持续时间
        pt.setPath(path);//设置路径
        pt.setNode(circle);//设置物体
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        //设置周期性，无线循环
        pt.setCycleCount(1);
        pt.setAutoReverse(false);//自动往复
        pt.setOnFinished(e -> root.getChildren().remove(circle));//结束后球消失
        pt.play();//启动动画

    }
    //停止动画
    void stopAnimation() {
        pt.stop();
        box.getChildren().clear();
        root.getChildren().remove(circle);
    }
    //计算ASL
    void showASL(){
        int len = sortTree.getAvgLen();
        msg.setText("平均查找长度： "+len);
    }
    //获取遍历结果球Label
    private Label getLabel(Object data) {
        Label label = new Label();
        label.setPrefWidth(40);
        label.setPrefHeight(40);
        label.setBorder(new Border(new BorderStroke(Color.RED, null, null, null)));
        label.setFont(Font.font("宋体", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(
                new BackgroundImage(new Image("tree/circle.jpg", 40, 40, true, true), null, null, null, new BackgroundSize(40, 40, false, false, false, false))));
        label.setText(data + "");
        return label;
    }
}
