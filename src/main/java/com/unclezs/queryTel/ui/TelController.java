package com.unclezs.queryTel.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.unclezs.dataStruct.MyHashTable;
import com.unclezs.queryTel.User;
import com.unclezs.utils.GlobalValue;
import com.unclezs.utils.RandomUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.print.attribute.standard.NumberUp;
import java.net.URL;
import java.util.ResourceBundle;

/*
 *电话号码查询fxml控制器
 *@author unclezs.com
 *@date 2019.06.10 09:56
 */
public class TelController implements Initializable {

    //录入系统
    @FXML TextField name;//姓名
    @FXML TextField tel;//电话
    @FXML TextField address;//电话
    @FXML TextField number;//随机生成数量
    @FXML JFXButton save;//确认录入
    @FXML JFXButton create;//随机生成

    //查询系统
    @FXML TableView table;//查询结果表格
    @FXML TableColumn tname;//姓名
    @FXML TableColumn ttel;//电话
    @FXML TableColumn taddress;//地址
    @FXML JFXButton query;//查询按钮
    @FXML TextField searchText;//搜索内容
    @FXML JFXComboBox searchType;//搜索类型

    //数据统计
    @FXML
    LineChart lineMap;//折线图
    @FXML Text cmode;//处理冲突方式
    @FXML Text chash;//散列函数
    @FXML Text cerate;//扩容比例
    @FXML Text ccapactiy;//容量

    //主页
    @FXML JFXComboBox fhash;//哈希函数
    @FXML JFXComboBox fmode;//处理冲突方式
    @FXML Button rebuild;//重建哈希表
    @FXML TextField erate;//装填因子
    @FXML TextField capacity;//容量
    //成员数据
    private MyHashTable<String,User> nameTable=new MyHashTable<>();//名字为关键字建立散列表
    private MyHashTable<String,User> telTable=new MyHashTable<>();//电话号码为关键字建立散列表

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQueryAndIndex();//初始化首页和查询
        initLineMap();//初始化折线图
        //事件绑定
        save.setOnMouseClicked(e->saveUser());
        create.setOnMouseClicked(e->createUser());
        query.setOnMouseClicked(e->queryUser());
        rebuild.setOnMouseClicked(e->rebuildHashTable());
    }
    //确认录入
    void saveUser(){
        User user=new User();
        user.setName(name.getText());
        user.setAddress(address.getText());
        user.setTel(tel.getText());
        nameTable.put(user.getName(),user);
        telTable.put(user.getTel(),user);
        alertBox(null,"录入信息成功");
    }
    //随机生成
    void createUser(){
        String text=number.getText();
        if (text.equals(""))
            return;
        int num=Integer.parseInt(text);
        for(int i=0;i<num;i++){
            User user=new User();
            user.setName(RandomUtils.getRandomString(5,""));
            user.setAddress(RandomUtils.getRandomString(20,""));
            user.setTel(RandomUtils.getRandom(11));
            nameTable.put(user.getName(),user);
            telTable.put(user.getTel(),user);
            //计算平均查找长度生成表格
            addDataToLineMap();
        }
        alertBox("随机批量录入信息成功","数量:"+num);
    }
    //初始化查询系统和主页
    void initQueryAndIndex(){
        //查询系统
        searchType.getItems().add("姓名");
        searchType.getItems().add("电话号码");
        searchType.setValue("姓名");
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        ttel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        taddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        table.setItems(FXCollections.observableArrayList());
        //主页
        fmode.getItems().addAll("开放地址法","链式法");
        fmode.setValue("开放地址法");
        fhash.getItems().addAll("1","2");
        fhash.setValue("1");
    }
    void queryUser(){
        table.getItems().clear();
        String text = searchText.getText();
        String condition=searchType.getValue().toString();
        if(condition.equals("姓名")){
            User user = nameTable.get(text);
            table.getItems().add(user);
        }else {
            User user = telTable.get(text);
            table.getItems().add(user);
        }
    }
    //计算平均查找长度生成表格
    void addDataToLineMap(){
        //获取解决冲突方式
        int mode=Integer.parseInt(fmode.getValue().equals("开放地址法")?"1":"2");
        //姓名曲线
        int conflictsNum = nameTable.getConflictsNum();
        int tableNum = nameTable.getNum(mode);
        float ASL=(conflictsNum+0.0f)/tableNum;
        ((XYChart.Series)(lineMap.getData().get(0))).getData().add(new XYChart.Data(tableNum,ASL));
        //电话曲线
        int conflictsNum_tel = telTable.getConflictsNum();
        int tableNum_tel = telTable.getNum(mode);
        float ASL_tel=(conflictsNum_tel+0.0f)/tableNum;
        ((XYChart.Series)(lineMap.getData().get(1))).getData().add(new XYChart.Data(tableNum_tel,ASL_tel));
    }
    void initLineMap(){
        lineMap.setData(null);
        ObservableList<XYChart.Series<Integer, Float>> series = FXCollections.observableArrayList();
        XYChart.Series<Integer,Float> name_series=new LineChart.Series<>();
        name_series.setName("姓名为关键词曲线图");
        XYChart.Series<Integer,Float> tel_series=new LineChart.Series<>();
        tel_series.setName("电话号码为关键词曲线图");
        series.addAll(name_series,tel_series);
        lineMap.setData(series);
        //更新散列表生成信息
        cmode.setText(fmode.getValue().toString());
        chash.setText("散列函数："+fhash.getValue().toString());
        ccapactiy.setText("容量："+(capacity.getText().equals("")? "16" : capacity.getText()));
        cerate.setText("扩容因子："+(erate.getText().equals("") ? "0.8" : erate.getText()));
    }
    void rebuildHashTable(){
        double eRate = Double.parseDouble(erate.getText().equals("") ? "0.8" : erate.getText());//扩容因子，默认0.8
        int num = Integer.parseInt(capacity.getText().equals("")? "16" : capacity.getText());//初始容量，默认16
        int hash = Integer.parseInt(fhash.getValue().toString());
        int mode=Integer.parseInt(fmode.getValue().equals("开放地址法")?"1":"2");
        nameTable=new MyHashTable(num,mode,eRate,hash);
        telTable=new MyHashTable(num,mode,eRate,hash);
        initLineMap();
        alertBox("重构散列表成功","处理冲突方式："+fmode.getValue().toString());
    }
    //弹出一个信息对话框
    private void alertBox(String p_header, String p_message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("提示信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.initOwner(GlobalValue.flightStage);
        _alert.show();
    }
}
