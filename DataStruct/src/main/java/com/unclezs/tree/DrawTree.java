package com.unclezs.tree;

import com.unclezs.dataStruct.MyList;
import com.unclezs.dataStruct.MyQueue;
import com.unclezs.dataStruct.SortTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

/*
 *@author unclezs.com
 *@date 2019.06.14 18:45
 */
public class DrawTree extends Application {
    public static GraphicsContext gc=null;//画笔
    private MyList<Integer> layers;//树总层数和每层多少个元素
    private double topLen=0;
    private final double lingH=160;//每层间距
    private final double radius=40;//节点圆直径
    private final double cw=1900;
    private final int MAX_LYAER=4;//最大层数
    public final static Canvas canvas=new Canvas(1900,1000);//画布
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        SortTree<Integer> st = new SortTree<>();
        st.add(88);
        stage.setResizable(false);
        TextField field=new TextField();
        Button flach=new Button("添加节点");
        field.setLayoutX(80);
        ScrollPane root = new ScrollPane();
        gc = canvas.getGraphicsContext2D();
        draw(st, cw);
        Pane pane=new Pane();
        pane.getChildren().addAll(canvas,flach,field);
        root.setContent(pane);
        Scene scene = new Scene(root,1900,1000);
        stage.setScene(scene);
        stage.widthProperty().addListener(e -> {
            st.add(Integer.parseInt(field.getText()));
            gc.clearRect(0, 0,cw,1000);
            draw(st, cw);
        });
        flach.setOnMouseClicked(e->{
            st.add(Integer.parseInt(field.getText()));
            field.setText("");
            field.requestFocus();
            gc.clearRect(0, 0,cw,1000);
            draw(st, cw);
        });
        field.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){
                st.add(Integer.parseInt(field.getText()));
                field.setText("");
                field.requestFocus();
                gc.clearRect(0, 0,cw,1000);
                draw(st, cw);
            }
        });
        stage.show();
    }
    //画树
    public void draw(SortTree<Integer> s, double width) {
        if(s==null||s.first()==null)
            return;
        levelTraversal(s);
        //初始化画笔
        gc.setStroke(Color.WHITE);
        gc.setFont(new Font(16));
        gc.setFill(Color.valueOf("#009688"));
        gc.setLineWidth(2);
        double x=width/2-radius/2;//居x中显示，根节点横坐标
        s.first().x=x;
        s.first().y=50;
        //二次层次遍历画树
        MyQueue<SortTree.Node> queue=new MyQueue<>();
        queue.add(s.first());
        int n=0;
        while (queue.size()!=0){
            int len=queue.size();
            for (int i = 0; i < len; i++) {
                SortTree.Node node = queue.poll();
                double[] rect={};
                if(node==s.first()){//画头节点
                    drawRoot(node.x,node.getElement().toString());
                }else {
                    if(node==node.getParent().getRight()){//画右节点
                        rect=drawRight(node.getParent().x,node.getParent().y,node.getElement().toString(),n);
                    }
                    if(node==node.getParent().getLeft()){//画左节点
                        rect = drawLeft(node.getParent().x, node.getParent().y, node.getElement().toString(), n);
                    }
                }
                //入队列
                if(node.getRight()!=null){
                    queue.add(node.getRight());

                }
                if(node.getLeft()!=null){
                    queue.add(node.getLeft());
                }
                //更新本节点坐标
                if(rect.length==2){
                    node.x=rect[0];
                    node.y=rect[1];
                }
            }
            n++;//层数加1,大于最大层数层停止
            if(n>MAX_LYAER)
                break;
        }
    }
    /**
     * 画根节点
     * @param x 根节点横坐标
     * @param content 节点数据
     */
    public void drawRoot(double x,String content){
        double len=Math.pow(2,layers.size()-1)*120;//更新顶端最大长度
        gc.fillOval(x, 50, radius, radius);
        gc.strokeText(content,x+10,75);//与节点的坐标，x大5，y大15
    }
    /**
     * 画左边节点
     * @param pw 父节点的横坐标
     * @param ph 父节点的纵坐标
     * @param content 节点数据
     * @param layer 所在层数
     * @return 节点的坐标
     */
    public double[] drawLeft(double pw,double ph,String content,int layer){
        double part=Math.pow(2,layer);//当前层多少节点
        double xoff = topLen / part;//横坐标偏移量
        if(layer==MAX_LYAER){
            xoff=20;
        }
        double p=(radius/2)/Math.sqrt(Math.pow(lingH+radius,2)+Math.pow(xoff,2));//斜边长度
        double lsy=p*lingH;//计算离圆心y偏移量
        double lsx=p*xoff;//计算离圆心x偏移量
        gc.setStroke(Color.valueOf("#009688"));
        gc.strokeLine(pw+radius/2-lsx,ph+radius/2+lsy, pw-xoff+lsx, ph+radius/2+lingH-lsy);//画线
        gc.setStroke(Color.WHITE);
        gc.fillOval(pw-xoff-radius/2, ph+lingH, radius, radius);//画空心圆
        gc.strokeText(content,pw-xoff-radius/2+10,ph+lingH+25);//与节点的坐标，x大5，y大15
        double[] rect={pw-xoff-radius/2,ph+lingH};//本节点坐标
        return rect;
    }
    //画右边节点
    public double[] drawRight(double pw,double ph,String content,int layer){
        double xoff = topLen / Math.pow(2,layer);//横坐标偏移量
        if(layer==MAX_LYAER){//最后一层偏移
            xoff=20;
        }
        double p=(radius/2)/Math.sqrt(Math.pow(lingH+radius,2)+xoff*xoff);// 半径/三角形的斜边
        double lsx=p*xoff;
        double lsy=p*lingH;
        gc.setStroke(Color.valueOf("#009688"));
        gc.strokeLine(pw+radius/2+lsx, ph+radius/2+lsy, pw+xoff+radius-lsx, ph+lingH+radius/2-lsy);
        gc.setStroke(Color.WHITE);
        gc.fillOval(pw+xoff+radius/2, ph+lingH, radius, radius);
        gc.strokeText(content,pw+xoff+radius/2+10,ph+lingH+25);//与节点的坐标，x大5，y大15
        double[] rect={pw+xoff+radius/2,ph+lingH};
        return rect;
    }
    //层次遍历,返回有多少层最大MAX_LAYER层
    public int levelTraversal(SortTree st){
        layers=new MyList<>();
        MyQueue<SortTree.Node> queue=new MyQueue<>();
        queue.add(st.first());
        int n=0;
        while (queue.size()!=0){
            int len=queue.size();
            for (int i = 0; i < len; i++) {
                SortTree.Node node = queue.poll();
                if(node.getLeft()!=null)
                    queue.add(node.getLeft());
                if(node.getRight()!=null){
                    queue.add(node.getRight());
                }
            }
            layers.add(len);//每层遍历完添加该层多少个节点
            n++;
            if(n>MAX_LYAER){
                break;
            }
        }
//        topLen=Math.pow(2,layers.size()-1)*5>gc.getCanvas().getWidth()?gc.getCanvas().getWidth():Math.pow(2,layers.size()-1)*5;
        topLen=Math.pow(2,layers.size()-1)*(40);
        return n;
    }
    //两点间距离
    private void printDistance(double x1,double y1,double x2,double y2){
        double x = Math.abs(x1 - x2);
        double y =Math.abs(y1-y2);
        System.out.println(Math.sqrt(x*x+y*y));
    }
    public SortTree<Integer> getST(boolean isFix){
        SortTree<Integer> st = new SortTree<>();
        st.isFix=isFix;
        Random r=new Random();
        for (int i = 0; i <20 ; i++) {
            st.add(r.nextInt(100));
        }
        return st;
    }
 }
