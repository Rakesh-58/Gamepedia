package com.example.gamepedia;

import android.graphics.Bitmap;

public class ListData {
    String name;
    String image,key;
    public ListData(String name,  String image,String key) {
        this.name = name;
        this.image = image;
        this.key=key;
    }

    public void setImage(String image)
    {
        this.image=image;
    }

    public String getImageURL() {
        return image;
    }

    public String getName() {
        return name;
    }
}