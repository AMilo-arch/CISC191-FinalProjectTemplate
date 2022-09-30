package edu.sdccd.cisc191.template;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.Console;

public class Sprite extends Rectangle {



    private ImageView _imageView;
    private Pane _pane;

    public Sprite(Image image, double width, double height, double x, double y, double tileSizeX, double tileSizeY){

        super(x, y, tileSizeX, tileSizeY);
        _imageView = new ImageView();
        _imageView.setX(x/2);
        _imageView.setY((y/2) - (height/2));


        if(image != null){
            _imageView.setImage(image);
            System.out.println(image.getUrl());
            System.out.println(x + " | " + y);

        }

        _imageView.setFitWidth(width);
        _imageView.setFitHeight(height);
        _pane = new Pane();
        _pane.getChildren().add(_imageView);
        _pane.getChildren().add(this);

        // _imageView.toFront();


    }

    public ImageView getimageView(){
        return _imageView;
    }
    public Pane getStackPane(){
        return _pane;
    }

    public void setImage(Image image){

        _imageView.setImage(image);

    }

}
