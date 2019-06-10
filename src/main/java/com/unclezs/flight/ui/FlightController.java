package com.unclezs.flight.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.unclezs.dataStruct.MyList;
import com.unclezs.flight.Customer;
import com.unclezs.flight.FlightInfo;
import com.unclezs.flight.Order;
import com.unclezs.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/*
 *@author unclezs.com
 *@date 2019.06.03 20:57
 */
public class FlightController implements Initializable {
    //录入航班信息
    @FXML
    TextField hbgs;//航班公司
    @FXML
    TextField hbbh;//航班编号
    @FXML
    TextField cfz;//出发站
    @FXML
    TextField zdz;//到达站
    @FXML
    TextField jg;//价格
    @FXML
    TextField zk;//折扣
    @FXML
    DatePicker cfsj;//出发时间
    @FXML
    DatePicker ddsj;//到达时间
    @FXML
    TextField dgch;//串号
    @FXML
    TextField sypl;//剩余票量
    @FXML
    JFXButton savelr;//保存录入

    //航班信息-表格数据
    @FXML
    JFXButton drsj;//导入数据
    @FXML
    JFXButton dcsj;//导入数据
    @FXML
    Button showall;//显示全部数据
    @FXML
    TableColumn hbxx_hbgs;//航班公司
    @FXML
    TableColumn hbxx_hbbh;//航班编号
    @FXML
    TableColumn hbxx_cfz;//出发站
    @FXML
    TableColumn hbxx_zdz;//到达站
    @FXML
    TableColumn hbxx_jg;//价格
    @FXML
    TableColumn hbxx_zk;//折扣
    @FXML
    TableColumn hbxx_cfsj;//出发时间
    @FXML
    TableColumn hbxx_ddsj;//到达时间
    @FXML
    TableColumn hbxx_dgch;//订购串号
    @FXML
    TableColumn hbxx_sypl;//剩余票量
    @FXML
    TableView tview;//表格

    //航班查询相关
    @FXML
    TextField hbbhss;//航班编号搜索输入框
    @FXML
    TextField hbcx_cfd;//航班搜索起始地
    @FXML
    TextField hbcx_ddd;//航班搜索到达地
    @FXML
    Label hbbhss_text;//编号搜索输入框标签
    @FXML
    Label hbcx_ddbq;//地点查询标签
    @FXML
    DatePicker hbsssj;//搜索条件
    @FXML
    JFXRadioButton ahbh;//按航班编号
    @FXML
    JFXRadioButton aqjd;//按起始地
    @FXML
    JFXButton cxbtn;//查询按钮

    //客户数据区
    @FXML TextField kh_ssq;//客户搜索框
    @FXML TextField kh_ixm;//客户添加——姓名
    @FXML TextField kh_izjhm;//客户添加——证件号码
    @FXML TextField kh_idhhm;//客户添加——电话号码
    @FXML Button kh_cxhk;//查询用户
    @FXML Button kh_tjkh;//添加客户
    @FXML Button kh_xssy;//显示所有客户
    @FXML Button kh_dckh;//导出客户
    @FXML Button kh_drkh;//导入客户
    @FXML TableView kh_tview;//客户表
    @FXML TableColumn kh_tbh;//表格中-编号
    @FXML TableColumn kh_tzjhm;//表格中-证件号码
    @FXML TableColumn kh_txm;//表格中-姓名
    @FXML TableColumn kh_tdhhm;//表格中-电话号码

    //订单区
    @FXML TextField dd_ssk;//搜索框
    @FXML Button dd_cxdd;//查询按钮
    @FXML Button dd_drdd;//导入按钮
    @FXML Button dd_dcdd;//导出按钮
    @FXML Button dd_xsqb;//显示全部按钮
    @FXML TableView dd_tview;//订单表格
    @FXML TableColumn dd_hbch;//航班串号
    @FXML TableColumn dd_ddh;//订单号
    @FXML TableColumn dd_khbh;//客户编号
    @FXML TableColumn dd_dgsl;//订购数量
    @FXML TableColumn dd_dgsj;//订购时间

    //购退票区
    @FXML TextField gp_hbch;//航班串号
    @FXML TextField gp_khzjhm;//购票客户证件号码
    @FXML TextField gp_sl;//购票数量
    @FXML Button gp_qrdg;//确认购票按钮
    @FXML Button tp_qrtp;//确认退票按钮
    @FXML TextField tp_ddbh;//订单号
    @FXML TextField tp_zjhm;//退票身份证号码
    @FXML Tab tpgp;//退票购票按钮tab
    @FXML TabPane tabPane;//主窗口
    //本类数据区
    MyList<FlightInfo> data = new MyList<>();//航班信息数据
    MyList<Customer> kh_data = new MyList<>();//客户数据
    MyList<Order> dd_data = new MyList<>();//订单数据

    //初始化函数
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化表
        tviewAddItem(MyList2OList(data));
        addKhByList(MyList2OList(kh_data));
        addOrderByList(null);

        //设置航班信息表格右键菜单
        tview.setRowFactory(tv->{
            TableRow<FlightInfo> tableRow=new TableRow<>();
            tableRow.setOnMouseClicked(e->{
                if(e.getClickCount()==1&&e.getButton()== MouseButton.SECONDARY){
                    ContextMenu contextMenu =GlobalMenu.getInstance();
                    //购买菜单被选中
                    contextMenu.getItems().get(0).setOnAction(ebuy->{
                        gp_hbch.setText(tableRow.getItem().getStatus());
                        tabPane.getSelectionModel().select(tpgp);
                    });
                    //删除菜单被选中
                    contextMenu.getItems().get(1).setOnAction(edelete->{
                        data.remove(tableRow.getIndex());
                        tview.setItems(MyList2OList(data));
                    });
                    contextMenu.show(tview,e.getScreenX(),e.getScreenY());
                }

            });
            return tableRow;
        });
        //导入数据按钮点击事件绑定
        drsj.setOnMouseClicked(e -> importData());
        //导出数据按钮点击事件绑定
        dcsj.setOnMouseClicked(e -> exportData());
        //查询航班信息按钮点击事件绑定
        cxbtn.setOnMouseClicked(e -> searchFlightInfo());
        //显示全部按钮事件绑定
        showall.setOnMouseClicked(e->tview.setItems(MyList2OList(data)));
        //设置航班信息查询选择radiobutton为单选
        ToggleGroup group = new ToggleGroup();
        ahbh.setToggleGroup(group);
        aqjd.setToggleGroup(group);
        //绑定选中事件
        ahbh.setOnAction(e->{
            if(ahbh.isSelected()){
                hbbhss.setVisible(true);
                hbbhss_text.setVisible(true);
                hbcx_cfd.setVisible(false);
                hbcx_ddd.setVisible(false);
                hbcx_ddbq.setVisible(false);
            }
        });
        aqjd.setOnAction(e->{
            if(aqjd.isSelected()){
                hbbhss.setVisible(false);
                hbbhss_text.setVisible(false);
                hbcx_cfd.setVisible(true);
                hbcx_ddd.setVisible(true);
                hbcx_ddbq.setVisible(true);
            }
        });
        //初始化录入数据
        cfsj.getEditor().setText("2019-6-15");
        ddsj.getEditor().setText("2019-6-16");

        //客户区
        //添加客户点击事件
        kh_tjkh.setOnMouseClicked(e->addCustomer());
        //绑定客户查询事件
        kh_cxhk.setOnMouseClicked(e->queryCustomer());
        //帮定显示所有客户事件
        kh_xssy.setOnMouseClicked(e->showAllCustomer());
        //客户导出
        kh_dckh.setOnMouseClicked(e->exportCustomer());
        //客户导入
        kh_drkh.setOnMouseClicked(e->importCustomer());

        //订单区
        //查询订单
        dd_cxdd.setOnMouseClicked(e->queryOrder());
        //导入导出订单
        dd_dcdd.setOnMouseClicked(e->exportOrder());
        dd_drdd.setOnMouseClicked(e->importOrder());
        //显示全部订单
        dd_xsqb.setOnMouseClicked(e->showAllOrder());
//        dd_ddh.setCellValueFactory(new PropertyValueFactory("Onumber"));
        dd_ddh.setCellFactory(TextFieldTableCell.<Order>forTableColumn());

        //购票区
        //购票绑定
        gp_qrdg.setOnMouseClicked(e->buyTicket());
        //退票绑定
        tp_qrtp.setOnMouseClicked(e->backTicket());
    }

    /******************************航班信息区*********************************************/
    //录入航班信息
    public void saveLr(ActionEvent event) {
        //录入
        FlightInfo info = new FlightInfo();
        info.setName(hbgs.getText().trim());
        info.setNumber(hbbh.getText().trim());
        info.setSetOutAddress(cfz.getText().trim());
        info.setEndAddress(zdz.getText().trim());
        info.setSetOutTime(cfsj.getEditor().getText().trim());
        info.setEndTime(ddsj.getEditor().getText().trim());
        info.setPrice(Float.parseFloat(jg.getText().trim()));
        info.setDiscount(Float.parseFloat(zk.getText().trim()));
        info.setStatus(dgch.getText().trim());
        info.setTiketsNum(Integer.parseInt(sypl.getText().trim()));
        //保存
        System.out.println(info);
        ObservableList<FlightInfo> list = FXCollections.observableArrayList();
        list.add(info);
        tviewAddItem(list);
    }

    /**
     * 根据顺序表导入表格
     *
     * @param list 数据顺序表
     */
    void tviewAddItem(ObservableList<FlightInfo> list) {
        hbxx_hbgs.setCellValueFactory(new PropertyValueFactory("name"));
        //设置编辑绑定事件
        hbxx_hbgs.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_hbgs.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setName(value.getNewValue());
                    }
                }
        );
        hbxx_hbbh.setCellValueFactory(new PropertyValueFactory("number"));
        hbxx_hbbh.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_hbbh.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setNumber(value.getNewValue());
                    }
                }
        );
        hbxx_cfz.setCellValueFactory(new PropertyValueFactory("SetOutAddress"));
        hbxx_cfz.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_cfz.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setSetOutAddress(value.getNewValue());
                    }
                }
        );
        hbxx_zdz.setCellValueFactory(new PropertyValueFactory("EndAddress"));
        hbxx_zdz.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_zdz.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setEndAddress(value.getNewValue());
                    }
                }
        );
        hbxx_ddsj.setCellValueFactory(new PropertyValueFactory("EndTime"));
        hbxx_ddsj.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_ddsj.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setEndTime(value.getNewValue());
                    }
                }
        );
        hbxx_cfsj.setCellValueFactory(new PropertyValueFactory("SetOutTime"));
        hbxx_cfsj.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_cfsj.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setSetOutTime(value.getNewValue());
                    }
                }
        );
        hbxx_jg.setCellValueFactory(new PropertyValueFactory("Price"));
        hbxx_jg.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        hbxx_jg.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, Float>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, Float> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setPrice(value.getNewValue());
                    }
                }
        );
        hbxx_zk.setCellValueFactory(new PropertyValueFactory("Discount"));
        hbxx_zk.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        hbxx_zk.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, Float>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, Float> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setDiscount(value.getNewValue());
                    }
                }
        );
        hbxx_dgch.setCellValueFactory(new PropertyValueFactory("Status"));
        hbxx_dgch.setCellFactory(TextFieldTableCell.<FlightInfo>forTableColumn());
        hbxx_dgch.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, String>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setStatus(value.getNewValue());
                    }
                }
        );
        hbxx_sypl.setCellValueFactory(new PropertyValueFactory("TiketsNum"));
        hbxx_sypl.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        hbxx_sypl.setOnEditCommit(
                new EventHandler<CellEditEvent<FlightInfo, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<FlightInfo, Integer> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setTiketsNum(value.getNewValue());
                    }
                }
        );
        //取出原来的添加后来新的
        ObservableList items = tview.getItems();
        items.addAll(list);
        tview.setItems(items);
        //保存表格数据到备份data
        data.clear();
        data.addAll(OList2MyList(items));
//        data=OList2MyList(items);
    }

    //导出数据到文件
    void exportData() {
        tview.refresh();
        //打开文件弹窗
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName("航班信息.dat");
        File file = chooser.showSaveDialog(GlobalValue.flightStage);
        if (file != null) {
            String path = file.getPath();
            //保存到文件
            ObjUtil.saveConfig(OList2MyList(tview.getItems()), path);
        }

    }

    //导入文件数据
    void importData() {
        //打开文件选择框
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(GlobalValue.flightStage);
        if (file == null)
            return;
        //加载保存得文件
        MyList<FlightInfo> infoMyList = (MyList) ObjUtil.loadConfig(file.getPath());
        //将自定义顺序表内容加载到表格
        tview.setItems(MyList2OList(infoMyList));
        data.clear();
        data.addAll(infoMyList);
    }

    //搜索按钮点击绑定函数
    void searchFlightInfo() {
        String time = hbsssj.getEditor().getText().trim();
        //根据条件查询
        MyList<FlightInfo> searchData = new MyList<>();//查询结果
        if(ahbh.isSelected()){
            String in = hbbhss.getText().trim();
            if (in == null)
                return;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getNumber().equals(in)&&data.get(i).getSetOutTime().equals(time))
                    searchData.add(data.get(i));
            }
        }
        if(aqjd.isSelected()){
            String stAddr = hbcx_cfd.getText();
            String edAddr = hbcx_ddd.getText();
            if(stAddr==null||edAddr==null)
                return;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSetOutAddress().equals(stAddr)&&data.get(i).getEndAddress().equals(edAddr)&&data.get(i).getSetOutTime().equals(time))
                    searchData.add(data.get(i));
            }
        }
        tview.setItems(MyList2OList(searchData));
    }



    /****************************客户资料区****************************************/

    //根据顺序表的数据添加到表
    public void addKhByList(ObservableList<Customer> list){
        kh_tbh.setCellValueFactory(new PropertyValueFactory("cid"));
        kh_tbh.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        kh_tbh.setOnEditCommit(
                new EventHandler<CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(CellEditEvent<Customer, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setCid(value.getNewValue());
                    }
                }
        );
        kh_txm.setCellValueFactory(new PropertyValueFactory("name"));
        kh_txm.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        kh_txm.setOnEditCommit(
                new EventHandler<CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(CellEditEvent<Customer, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setName(value.getNewValue());
                    }
                }
        );
        kh_tzjhm.setCellValueFactory(new PropertyValueFactory("idNumber"));
        kh_tzjhm.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        kh_tzjhm.setOnEditCommit(
                new EventHandler<CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(CellEditEvent<Customer, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setIdNumber(value.getNewValue());
                    }
                }
        );
        kh_tdhhm.setCellValueFactory(new PropertyValueFactory("tel"));
        kh_tdhhm.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        kh_tdhhm.setOnEditCommit(
                new EventHandler<CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(CellEditEvent<Customer, String> value) {
                        value.getTableView().getItems().get(value.getTablePosition().getRow()).setTel(value.getNewValue());
                    }
                }
        );
        //取出原来的添加后来新的
        ObservableList items = kh_tview.getItems();
        items.addAll(list);
        kh_tview.setItems(items);
        //保存表格数据到备份data
        kh_data = OList2MyList(items);
    }
    //添加客户
    void addCustomer(){
        if(kh_idhhm.getText().equals("")||kh_izjhm.getText().equals("")||kh_ixm.equals("")){
            return;
        }
        Customer customer=new Customer();
        customer.setTel(kh_idhhm.getText());
        customer.setIdNumber(kh_izjhm.getText());
        customer.setName(kh_ixm.getText());
        customer.setCid(RandomUtils.getRandom(5)+"");
        MyList<Customer> list=new MyList<>();
        list.add(customer);
        addKhByList(MyList2OList(list));
    }
    //查询客户
    void queryCustomer(){
        String condition = kh_ssq.getText();
        MyList<Customer> list=new MyList<>();
        for (int i=0;i<kh_data.size();i++){
            if(condition.equals(kh_data.get(i).getIdNumber())||condition.equals(kh_data.get(i).getTel())||condition.equals(kh_data.get(i).getName())){
                list.add(kh_data.get(i));
            }
        }
        kh_tview.setItems(MyList2OList(list));
    }
    //显示所有客户
    void showAllCustomer(){
        kh_tview.setItems(MyList2OList(kh_data));
    }
    //导出客户资料
    void exportCustomer(){
        FileChooser chooser=new FileChooser();
        chooser.setInitialFileName("客户资料.dat");
        File file = chooser.showSaveDialog(GlobalValue.flightStage);
        if(file!=null){
            ObjUtil.saveConfig(kh_data,file.getPath());
        }
    }
    //导出客户资料
    void importCustomer(){
        FileChooser chooser=new FileChooser();
        File file = chooser.showOpenDialog(GlobalValue.flightStage);
        if(file!=null){
            MyList<Customer> o = (MyList<Customer>)ObjUtil.loadConfig(file.getPath());
            kh_tview.setItems(MyList2OList(o));
            kh_data.clear();
            kh_data.addAll(o);
        }
    }

    /****************************订单区****************************************/
    void addOrderByList(Order order){
        dd_ddh.setCellValueFactory(new PropertyValueFactory("Onumber"));
        dd_hbch.setCellValueFactory(new PropertyValueFactory("Hnumber"));
        dd_khbh.setCellValueFactory(new PropertyValueFactory("CNumber"));
        dd_dgsl.setCellValueFactory(new PropertyValueFactory("tiketNum"));
        dd_dgsj.setCellValueFactory(new PropertyValueFactory("date"));
        if(order==null)
            return;
        //取出原来的添加后来新的
        ObservableList<Order> items = dd_tview.getItems();
        items.add(order);
        dd_tview.setItems(items);
        //保存表格数据到备份data
        dd_data.clear();
        dd_data.addAll(OList2MyList(items));
    }


    /****************************订票退票区****************************************/
    //买票
    public void buyTicket(){
        String flighid = gp_hbch.getText();//航班订购串号
        String idNumber = gp_khzjhm.getText();//购买身份证号码
        String num = gp_sl.getText();//购票数量
        String cid=null;//客户编号
        if(Integer.parseInt(num)<0){
            alertBox("错误参数","请输入正确票数");
            return;
        }
        //查询用户是否存在,存在则记录编号
        for (int i=0;i<kh_data.size();i++){
            if (kh_data.get(i).getIdNumber().equals(idNumber)){
                cid=kh_data.get(i).getCid();
                break;
            }
        }
        if(cid==null){
            alertBox("用户不存在","请确认客户表中存在："+idNumber);
            return;
        }
        //减掉购买的票
        for(int i=0;i<data.size();i++){
            if(data.get(i).getStatus().equals(flighid)){
                int dnum=data.get(i).getTiketsNum()-Integer.parseInt(num);
                if(dnum<0){
                    alertBox("票量不足","剩余可购票量为："+data.get(i).getTiketsNum()+"张");
                    return;
                }
                data.get(i).setTiketsNum(dnum);
                tview.refresh();
                break;
            }
            if(i==data.size()-1){
                alertBox("航班不存在","请确认航班信息中存在订购串号："+flighid);
                return;
            }
        }
        //添加订单信息
        Order order=new Order();
        order.setCNumber(cid);
        order.setOnumber(RandomUtils.getRandom(12));
        order.setDate(new Date(System.currentTimeMillis()).toString());
        order.setHnumber(flighid);
        order.setTiketNum(Integer.parseInt(num));
        //添加到订单数据表
        addOrderByList(order);
        alertBox("购票成功","购买"+order.getTiketNum()+"张票成功！");
    }
    //退票
    public void backTicket(){
        String orderid = tp_ddbh.getText();
        String idNumber = tp_zjhm.getText();
        if("".equals(orderid)||"".equals(idNumber)){
            alertBox("参数错误","请输入正确的参数");
            return;
        }
        int oid=-1;//订单下标
        Order order=null;
        for (int i=0;i<dd_data.size();i++){
           if(dd_data.get(i).getOnumber().equals(orderid)){
               order=dd_data.get(i);
               oid=i;
               break;
           }
        }
        if (order==null&&oid==-1){
            alertBox("订单号不存在","不存在的订单号："+orderid);
            return;
        }
        //查询用户是否存在,存在删除订单
        for (int i=0;i<kh_data.size();i++){
            if (order.getCNumber().equals(kh_data.get(i).getCid())&&kh_data.get(i).getIdNumber().equals(idNumber)){
                //删除订单
                dd_data.remove(oid);
                dd_tview.setItems(MyList2OList(dd_data));
                dd_tview.refresh();
                //票量返回
                for (int j=0;j<data.size();j++){
                    if(data.get(j).getStatus().equals(order.getHnumber())){
                         data.get(j).setTiketsNum(data.get(j).getTiketsNum()+order.getTiketNum());
                         tview.refresh();
                         break;
                    }
                }
                break;
            }
            if(i==kh_data.size()-1){
                alertBox("订单与身份证号码不匹配","不匹配的身份证号："+idNumber);
            }
        }
        alertBox("退票成功","订单号："+orderid);
    }
    //导入订单
    void importOrder(){
        FileChooser chooser=new FileChooser();
        File file = chooser.showOpenDialog(GlobalValue.flightStage);
        if (file!=null){
            MyList<Order> o = (MyList<Order>)ObjUtil.loadConfig(file.getPath());
            dd_data.clear();
            dd_data.addAll(o);
            dd_tview.setItems(MyList2OList(dd_data));
            dd_tview.refresh();
        }
    }
    //导出订单
    void exportOrder(){
        FileChooser chooser=new FileChooser();
        chooser.setInitialFileName("订单信息.dat");
        File file = chooser.showSaveDialog(GlobalValue.flightStage);
        if (file!=null){
            ObjUtil.saveConfig(OList2MyList(dd_tview.getItems()),file.getPath());
        }
    }
    //显示全部
    void showAllOrder(){
        dd_tview.setItems(MyList2OList(dd_data));
        dd_tview.refresh();
    }
    //查询订单
    void queryOrder(){
        String cd = dd_ssk.getText();
        if("".equals(cd))
            return;
        MyList<Order> list=new MyList<>();
        for(int i=0;i<dd_data.size();i++){
            if(dd_data.get(i).getCNumber().equals(cd)||dd_data.get(i).getOnumber().equals(cd)){
                list.add(dd_data.get(i));
            }
        }
        if(list.size()==0){
            alertBox("未找到订单","订单号/客户编号："+cd);
            return;
        }
        dd_tview.setItems(MyList2OList(list));
        dd_tview.refresh();
    }
    private <T> ObservableList<T> MyList2OList(MyList<T> infoMyList) {
        ObservableList<T> list = FXCollections.observableArrayList();
        if(infoMyList==null||infoMyList.size()==0)
            return list;
        for (int i = 0; i < infoMyList.size(); i++) {
            list.add(infoMyList.get(i));
        }
        return list;
    }

    private <T> MyList<T> OList2MyList(ObservableList<T> items) {
        MyList<T> infoMyList = new MyList<T>();
        for (T f : items) {
            infoMyList.add(f);
        }
        return infoMyList;
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
