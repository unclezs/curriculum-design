package com.unclezs.parkingLot.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.unclezs.dataStruct.MyList;
import com.unclezs.dataStruct.MyQueue;
import com.unclezs.dataStruct.MyStack;
import com.unclezs.flight.ui.CarPane;
import com.unclezs.parkingLot.Car;
import com.unclezs.parkingLot.Record;
import com.unclezs.utils.DateUtil;
import com.unclezs.utils.RandomUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

/*
 *@author unclezs.com
 *@date 2019.06.06 16:20
 */
public class ParkingController implements Initializable {
    @FXML
    Pane mg;
    @FXML
    VBox vbox;//停车场
    @FXML
    HBox hbox;//便利道
    @FXML
    JFXButton ck;//出库
    @FXML
    JFXButton lc;//来车
    @FXML
    TextField cph;//车牌号
    @FXML
    JFXToggleButton showLog;//显示栈信息
    @FXML
    TextArea log;//显示框

    //计费系统
    @FXML TableView table;//表格
    @FXML TableColumn t_cphm;//车牌号码
    @FXML TableColumn t_kssj;//开始时间
    @FXML TableColumn t_jssj;//结束时间
    @FXML TableColumn t_dj;//单价
    @FXML TableColumn t_zjsc;//总计时长
    @FXML TableColumn t_zjsf;//总计收费
    //自用数据
    MyStack<CarPane> stopStack=new MyStack<>();//停车场栈
    MyStack<CarPane> changeStack=new MyStack<>();//交换栈
    MyQueue<CarPane> waitQueue=new MyQueue<>();//便利道队列
    MyList<Record> list = new MyList<>();//计费信息
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //页面渲染、事件绑定
        lc.setOnMouseClicked(e->comingCar());
        ck.setOnMouseClicked(e->leaveCar());
        hbox.setSpacing(23);
        //显示日志
        showLog.setOnAction(e->{
            boolean s=showLog.isSelected();
            boolean t=log.isVisible();
            showLog.setSelected(showLog.isSelected());
            log.setVisible(!t);
        });
        //初始化表格
        initTable();
    }
    //来车
    void comingCar(){
        //如果没有停车场满了，便道没有满
        if(hbox.getChildren().size()<6&&vbox.getChildren().size()==7){
            CarPane car =new CarPane(new Car(RandomUtils.getRandom(5), 0, new Date())) ;
            waitQueue.add(car);
            car.setOnMouseClicked(e->{cph.setText(car.getCar().getNumber());});
            hbox.getChildren().add(car);
            log.appendText(car.getCar().getNumber()+"进入了便利栈等待\r\n");
        }
        //停车场没有满，直接进停车场
        if(vbox.getChildren().size()<7){
            CarPane car=new CarPane(new Car(RandomUtils.getRandom(5),1,new Date()));
            car.setOnMouseClicked(e->{cph.setText(car.getCar().getNumber());});
            stopStack.push(car);
            vbox.getChildren().add(stopStack.peek());
            log.appendText(stopStack.peek().getCar().getNumber()+"进入了停车场栈\r\n");
        }
    }
    //出库
    void leaveCar(){
        //获取车牌号
        String number = cph.getText();
        if(number==""){
            return;
        }
        //停车场查找车牌号
        CarPane car=null;
        while (!stopStack.empty()){
            //从栈顶开始查找
            car = stopStack.pop();
            vbox.getChildren().remove(car);
            //如果本次没匹配
            if(!car.getCar().getNumber().equals(number))
            {
                //从停车场出来，让道进入交换栈
                changeStack.push(car);
                log.appendText(car.getCar().getNumber()+"从停车场进入交换栈\r\n");
            }
            else {
                break;
            }
        }
        //没有车直接不处理出库请求
        if(car==null)
            return;
        //如果找到匹配车牌号的则把交换栈里的车全部移动回停车场栈
        if(car.getCar().getNumber().equals(number)){
            log.appendText(car.getCar().getNumber()+"出停车场了\r\n");
            log.appendText(car.getCar().getNumber()+"停车时间:"+ DateUtil.getDatePoor(new Date(),car.getCar().getSdate())+"\r\n");
            //提交计费系统
            try {
                Record record=new Record();
                record.setStime(DateUtil.d2s(car.getCar().getSdate(),"yyyy-MM-dd HH:mm:ss"));
                record.setNumber(car.getCar().getNumber());
                addChargingInfo(record);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            log.appendText(car.getCar().getNumber()+"没有这辆车\r\n");
        }
        //把便道上的车重新进入停车场
        while (!changeStack.empty()){
            //从交换栈出来，重新进重新入停车场栈
            log.appendText(changeStack.peek().getCar().getNumber()+"从交换栈进入停车场栈\r\n");
            stopStack.push(changeStack.pop());
            vbox.getChildren().add(stopStack.peek());
        }
        //如果找到匹配车牌的车并移除了，而且便道上有车
        if(waitQueue.peek()!=null&&car.getCar().getNumber().equals(number)){
            waitQueue.peek().getCar().setState(1);
            log.appendText(waitQueue.peek().getCar().getNumber()+"--从便道进入停车场\r\n");
            //便道上等待的入库
            stopStack.push(waitQueue.poll());
            vbox.getChildren().add(stopStack.peek());
        }
    }
    //计时计费值绑定
    void  initTable(){
        t_cphm.setCellValueFactory(new PropertyValueFactory<>("number"));
        t_kssj.setCellValueFactory(new PropertyValueFactory<>("stime"));
        t_jssj.setCellValueFactory(new PropertyValueFactory<>("etime"));
        t_zjsc.setCellValueFactory(new PropertyValueFactory<>("paseTime"));
        t_zjsf.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        t_dj.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }
    //出库时候添加停车信息道计费系统
    void  addChargingInfo(Record record) throws ParseException {
        //更新时间
        record.setEtime(DateUtil.d2s(new Date(),"yyyy-MM-dd HH:mm:ss"));
        record.setPaseTime(DateUtil.getDatePoor(DateUtil.s2d(record.getEtime(),
                "yyyy-MM-dd HH:mm:ss"),DateUtil.s2d(record.getStime(),"yyyy-MM-dd HH:mm:ss")));
        //计算停车时间
        int hour=Integer.parseInt(DateUtil.getDatePoorToHour(DateUtil.s2d(record.getEtime(),
                "yyyy-MM-dd HH:mm:ss"),DateUtil.s2d(record.getStime(),"yyyy-MM-dd HH:mm:ss")));
        hour=hour==0?1:hour;
        //单价
        record.setUnitPrice(5);
        //总计收费
        record.setTotalPrice(record.getUnitPrice()*hour+"元");
        list.add(record);
        ObservableList<Record> lists = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            lists.add(list.get(i));
        }
        table.setItems(lists);
    }
}
