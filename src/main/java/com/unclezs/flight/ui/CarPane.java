package com.unclezs.flight.ui;

import com.unclezs.parkingLot.Car;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;



/*
 *@author unclezs.com
 *@date 2019.06.06 17:04
 */
public class CarPane extends AnchorPane {
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Car car;

    public CarPane(Car car) {
        this.car = car;
        setImage("parkingLot/car.jpg");
        setLabel();
    }
    public void setImage(String url){
        ImageView imageView=new ImageView(url);
        this.getChildren().add(imageView);
    }
    public void setLabel(){
        Label label=new Label(car.getNumber());
        this.getChildren().add(label);
    }
}
