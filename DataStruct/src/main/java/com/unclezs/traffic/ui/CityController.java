package com.unclezs.traffic.ui;

import com.jfoenix.controls.JFXListView;
import com.unclezs.dataStruct.MyList;
import com.unclezs.traffic.Traffic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

/*
 *@author unclezs.com
 *@date 2019.06.18 11:25
 */
public class CityController implements Initializable {
    @FXML
    Button add;//添加
    @FXML
    TextField input;//输入框
    @FXML
    JFXListView cityList;//城市信息
    ObservableList<String> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        cityList.setItems(data);
        cityList.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.SECONDARY){
                ContextMenu contextMenu=new ContextMenu();
                MenuItem item=new MenuItem("删除");
                item.setOnAction(e->{
                    int index = cityList.getSelectionModel().getSelectedIndex();
                    fixDelete(index);//删除
                });
                contextMenu.getItems().add(item);
                contextMenu.show(TrafficController.stage);
            }
        });
        add.setOnMouseClicked(e->add());
        input.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER){
                add();
            }
        });
    }
    void add(){
        if(!"".equals(input.getText())&&!data.contains(input.getText())){
            data.add(input.getText());
            TrafficController.graph.insertVertex(input.getText());
        }
        input.clear();
        input.requestFocus();
    }
    void fixDelete(int index){
        data.remove(index);
        TrafficController.graph.removeVertex(index);//在图中删除城市
        TrafficController.graph.removeAllEdges(index);//删除响应线路
    }
    void initData(){
        for (int i = 0; i < TrafficController.graph.getVertexs().size(); i++) {
            data.add(TrafficController.graph.getVertex(i));
        }
        String[] addr={"重庆","贵阳","兰州","西宁","北京","郑州","株洲","南昌","广州","天津","上海"};
        for (int i = 0; i <addr.length ; i++) {
            data.add(addr[i]);
            TrafficController.graph.insertVertex(addr[i]);
        }
    }
}
