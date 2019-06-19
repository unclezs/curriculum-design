package com.unclezs.traffic.ui;

import com.browniebytes.javafx.control.DateTimePicker;
import com.jfoenix.controls.JFXComboBox;
import com.unclezs.dataStruct.Graph;
import com.unclezs.dataStruct.MyList;
import com.unclezs.traffic.Traffic;
import com.unclezs.utils.DateUtil;
import com.unclezs.utils.ResourceLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

/*
 *@author unclezs.com
 *@date 2019.06.18 10:45
 */
public class TrafficController implements Initializable {
    //城市管理
    @FXML
    Button city;
    //添加车次
    @FXML
    TextField icfd, iddd, ipj;
    @FXML
    DateTimePicker icfsj, iddsj;
    @FXML
    Button iadd;
    @FXML
    TableView<Traffic> infoTable;//班次表
    //车次表格
    @FXML
    TableColumn tcfd, tddd, tcfsj, tddsj, tpj;


    //查询服务
    @FXML
    JFXComboBox<String> cfaddr, ctaddr, condition;//出发到达城市
    @FXML
    TableColumn tlxbh, tcfcs, tddcs, tqcfsj, tqddsj, tqpj, ths;//表格
    @FXML
    Label cost, useTime, pastCity;//统计
    @FXML
    Button query;
    @FXML
    TableView queryTable;
    //成员
    public final static Stage stage = new Stage();//城市管理
    public static Graph<Traffic> graph = new Graph<>(100); //图

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //城市管理
            cityManager();
            city.setOnMouseClicked(e -> {
                stage.show();
            });
            //初始化车从信息表格
            initInfoTable();
            //初始化查询表
            initQueryTable();
            //初始化查询
            fixQueryConditon();
            //查询
            query.setOnMouseClicked(e -> queryBestWay());
            //添加班次
            iadd.setOnMouseClicked(e -> addTrafficInfo());
            //初始数据
            readData();
        } catch (IOException e) {
        }

    }

    //初始化城市
    void cityManager() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceLoader.getFXMLResource("traffic/city.fxml"));
        Pane load = loader.load();
        stage.getIcons().add(new Image("traffic/train.jpg"));
        stage.setResizable(false);
        stage.setTitle("城市管理");
        Scene scene = new Scene(load);
        stage.setScene(scene);
    }

    //初始化班次表格数据
    void initInfoTable() {
        tcfd.setCellValueFactory(new PropertyValueFactory<>("fromAddr"));
        tddd.setCellValueFactory(new PropertyValueFactory<>("toAddr"));
        tcfsj.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        tcfsj.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        tcfsj.setOnEditCommit(e -> {
            TableColumn.CellEditEvent<Traffic, String> s = (TableColumn.CellEditEvent) e;
            s.getTableView().getItems().get(s.getTablePosition().getRow())
                    .setDepartureTime(s.getNewValue());
            Traffic traffic = s.getTableView().getItems().get(s.getTablePosition().getRow());
            MyList<String> v = graph.getVertexs();
            int f = v.indexOf(traffic.getFromAddr());
            int t = v.indexOf(traffic.getToAddr());
            graph.getEdges().get(f).get(t).setDepartureTime(s.getNewValue());
            graph.getEdges().get(f).get(t).setTimePoor(getTimePoor(traffic.getArrivalTime(), s.getNewValue()));
        });
        tddsj.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        tddsj.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        tddsj.setOnEditCommit(e -> {
            TableColumn.CellEditEvent<Traffic, String> s = (TableColumn.CellEditEvent) e;
            s.getTableView().getItems().get(s.getTablePosition().getRow())
                    .setArrivalTime(s.getNewValue());
            Traffic traffic = s.getTableView().getItems().get(s.getTablePosition().getRow());
            MyList<String> v = graph.getVertexs();
            int f = v.indexOf(traffic.getFromAddr());
            int t = v.indexOf(traffic.getToAddr());
            graph.getEdges().get(f).get(t).setArrivalTime(s.getNewValue());
            graph.getEdges().get(f).get(t).setTimePoor(getTimePoor(s.getNewValue(), traffic.getDepartureTime()));
        });
        tpj.setCellValueFactory(new PropertyValueFactory<>("price"));
        tpj.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tpj.setOnEditCommit(e -> {
            TableColumn.CellEditEvent<Traffic, Integer> s = (TableColumn.CellEditEvent) e;
            s.getTableView().getItems().get(s.getTablePosition().getRow())
                    .setPrice(s.getNewValue());
            Traffic traffic = s.getTableView().getItems().get(s.getTablePosition().getRow());
            MyList<String> v = graph.getVertexs();
            int f = v.indexOf(traffic.getFromAddr());
            int t = v.indexOf(traffic.getToAddr());
            graph.getEdges().get(f).get(t).setPrice(s.getNewValue());
        });
        infoTable.setItems(FXCollections.observableArrayList());
        infoTable.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem delete = new MenuItem("删除");
                contextMenu.getItems().add(delete);
                contextMenu.show(MainTraffic.stage);
                delete.setOnAction(event -> {
                    int index = infoTable.getSelectionModel().getSelectedIndex();
                    Traffic traffic = infoTable.getItems().remove(index);
                    //图中移除边
                    graph.removeAEdege(graph.getVertexs().indexOf(traffic.getFromAddr()), graph.getVertexs().indexOf(traffic.getToAddr()));
                });
            }
        });
    }

    //初始化查询表格
    void initQueryTable() {
        tcfcs.setCellValueFactory(new PropertyValueFactory<>("fromAddr"));
        tddcs.setCellValueFactory(new PropertyValueFactory<>("toAddr"));
        tqcfsj.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        tqddsj.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        tqpj.setCellValueFactory(new PropertyValueFactory<>("price"));
        ths.setCellValueFactory(new PropertyValueFactory<>("timePoor"));
        tlxbh.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    //添加车次信息
    void addTrafficInfo() {
        try {
            String fromAddr = icfd.getText();
            String toAddr = iddd.getText();
            int i1 = graph.getVertexs().indexOf(fromAddr);
            int i2 = graph.getVertexs().indexOf(toAddr);
            if (i2 == -1 || i1 == -1) {
                alert("出发地或者到达地不存在，请确认添加了该城市");
                return;
            } else if (traffics(i1, i2) != null) {
                alert("路线信息已经存在");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy年MM月dd日 HH:mm");
            String ftime = icfsj.dateTimeProperty().getValue().format(formatter);
            String etime = iddsj.dateTimeProperty().getValue().format(formatter);
            //时间差
            long poorTime = getTimePoor(etime, ftime);
            int price = Integer.parseInt(ipj.getText());
            Traffic traffic = new Traffic(fromAddr, toAddr, ftime, etime, price);
            traffic.setTimePoor(poorTime);
            graph.insertEdge(i1, i2, traffic);
            infoTable.getItems().add(traffic);
            fixQueryConditon();//修复查询条件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修复查询信息
    public void fixQueryConditon() {
        cfaddr.getItems().clear();
        ctaddr.getItems().clear();
        MyList<String> v = graph.getVertexs();
        for (int i = 0; i < v.size(); i++) {
            cfaddr.getItems().add(v.get(i));
            ctaddr.getItems().add(v.get(i));
        }
        cfaddr.setValue(v.get(0));
        ctaddr.setValue(v.get(1));
        condition.getItems().clear();
        condition.getItems().addAll("最快到达", "最低价格", "最少中转");
        condition.setValue("最快到达");
    }

    //查询最优
    void queryBestWay() {
        String fromAddr = cfaddr.getValue();
        String toAddr = ctaddr.getValue();
        int con = condition.getSelectionModel().getSelectedIndex() + 1;
        findOptimal(fromAddr, toAddr, con);//按条件查询最优
    }

    //弗洛伊德找到最短路径
    int[][] pre;
    long[][] dis;

    private void floydRouteByTime(int condition) {// condition 1：最短时间，2低价格，3：最少中转站
        int size = graph.getVertexs().size();
        pre = new int[size][size];//存放前驱节点
        dis = new long[size][size];
        //用前驱节点下标填满前驱数组
        for (int i = 0; i < size; i++) {
            Arrays.fill(pre[i], i);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    dis[i][j] = 0L;
                } else {
                    switch (condition) {
                        case 1:
                            dis[i][j] = traffics(i, j) == null ? Integer.MAX_VALUE : traffics(i, j).getTimePoor();
                            break;
                        case 2:
                            dis[i][j] = traffics(i, j) == null ? Integer.MAX_VALUE : traffics(i, j).getPrice();
                            break;
                        case 3:
                            dis[i][j] = traffics(i, j) == null ? Integer.MAX_VALUE : traffics(i, j).getTimePoor();
                            break;
                    }
                }
            }
        }
        //开始floyd算法
        long poor = 0;
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    poor = dis[i][k] + dis[k][j];
                    if (poor < dis[i][j]) {
                        dis[i][j] = poor;//更新时间差
                        pre[i][j] = pre[k][j];//更新前驱节点
                    }
                }
            }
        }
    }

    //打印在最短路径
    private void showMinWay() {
        floydRouteByTime(3);
        MyList<String> v = graph.getVertexs();
        for (int i = 0; i < v.size(); i++) {
            for (int j = 0; j < v.size(); j++) {
                System.out.print(v.get(pre[i][j]) + "             ");
            }
            System.out.println();
            for (int j = 0; j < v.size(); j++) {
                System.out.print(v.get(i) + "到" + v.get(j) + "最短距离为" + dis[i][j] + "          ");
            }
            System.out.println();
            System.out.println();
        }
    }

    //查找最优路径
    public void findOptimal(String fromAddr, String toAddr, int condition) {
        ObservableList<Traffic> list = FXCollections.observableArrayList();//路线对象
        MyList<Integer> route = new MyList<>();//路线
        MyList<Integer> tmpRoute = new MyList<>();//路线
        //弗洛伊德查询
        floydRouteByTime(condition);
        MyList<String> vertexs = graph.getVertexs();
        //获取顶点下标
        int i1 = vertexs.indexOf(fromAddr);
        int i2 = vertexs.indexOf(toAddr);
        //统计
        long totalTime = 0;//总花费时间
        int totalSpend = 0;//总花费金钱
        System.out.println(condition + "最少节点：" + dis[i1][i2]);
        System.out.print(vertexs.get(i2) + "<-");
        tmpRoute.add(i2);
        int k = pre[i1][i2];
        while (i1 != k) {
            System.out.print(vertexs.get(k) + "-<");
            tmpRoute.add(k);
            k = pre[i1][k];
        }
        System.out.println(vertexs.get(i1));
        tmpRoute.add(i1);
        for (int i = tmpRoute.size() - 1; i >= 0; i--) {
            route.add(tmpRoute.get(i));
        }
        for (int i = 1; i < route.size(); i++) {
            Traffic traffic = traffics(route.get(i - 1), route.get(i));
            totalSpend += traffic.getPrice();
            totalTime += traffic.getTimePoor();
            traffic.setId(i);
            list.add(traffic);
        }
        System.out.println(list);
        queryTable.getItems().clear();
        queryTable.getItems().addAll(list);
        cost.setText(totalSpend + "元");
        useTime.setText(totalTime + "分钟");
        pastCity.setText(route.size() - 2 + "个");
    }

    //广度遍历遍历找最少中转
    void bfsFindMinAddr() {
        MyList<String> vertexs = graph.getVertexs();
        MyList<MyList<Traffic>> edges = graph.getEdges();
        int[][] node = new int[vertexs.size()][vertexs.size()];
        for (int i = 0; i < vertexs.size(); i++) {
            for (int j = 0; j < vertexs.size(); j++) {
                if (i == j) {
                    node[i][j] = 0;
                }
                node[i][j] = traffics(i, j) == null ? -1 : 1;
            }
        }
    }

    //获取指定下标的边
    private Traffic traffics(int i, int j) {
        return graph.getEdges().get(i).get(j);
    }

    //获取两个时间字符串的时间差（分钟）
    private long getTimePoor(String etime, String ftime) {
        try {
            return (DateUtil.s2d(etime, "yy年MM月dd日 HH:mm").getTime() - DateUtil.s2d(ftime, "yy年MM月dd日 HH:mm").getTime()) / (1000L * 60L);
        } catch (ParseException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    //弹窗
    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示信息");
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.initOwner(MainTraffic.stage);
        alert.show();
    }

    private void readData() {
        MyList<String> list = new MyList<>();
        list.add("重庆--贵阳--19年06月19日 12:09--19年06月19日 12:11--998");
        list.add("重庆--广州--19年06月19日 12:11--19年06月19日 12:19--548");
        list.add("重庆--北京--19年06月19日 12:09--19年06月19日 12:19--1200");
        list.add("贵阳--兰州--19年06月19日 15:29--19年06月19日 15:32--888");
        list.add("贵阳--北京--19年06月19日 12:16--19年06月19日 12:23--789");
        list.add("贵阳--株洲--19年06月19日 12:41--19年06月19日 12:45--345");
        list.add("贵阳--广州--19年06月19日 12:09--19年06月19日 12:17--678");
        list.add("兰州--西宁--19年06月19日 12:39--19年06月19日 12:40--425");
        list.add("兰州--北京--19年06月19日 12:17--19年06月19日 12:25--128");
        list.add("北京--天津--19年06月19日 12:09--19年06月19日 12:13--784");
        list.add("北京--郑州--19年06月19日 12:09--19年06月19日 12:12--458");
        list.add("郑州--株洲--19年06月19日 12:09--19年06月19日 12:12--975");
        list.add("株洲--南昌--19年06月19日 12:09--19年06月19日 12:13--714");
        list.add("南昌--郑州--19年06月19日 12:09--19年06月19日 12:14--254");
        list.add("南昌--上海--19年06月19日 12:09--19年06月19日 12:13--314");
        list.add("上海--天津--19年06月19日 12:09--19年06月19日 12:13--457");
        MyList<String> v = graph.getVertexs();
        String s;
        for (int i = 0; i < list.size(); i++) {
            String[] t = list.get(i).split("--");
            Traffic traffic = new Traffic(t[0], t[1], t[2], t[3], Integer.parseInt(t[4]));
            traffic.setTimePoor(getTimePoor(t[3], t[2]));
            graph.insertEdge(v.indexOf(t[0]), v.indexOf(t[1]), traffic);
            infoTable.getItems().add(traffic);
        }
    }
}
