package com.my.voenmeh.ui.news;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

public class PostRepository {
    private String mtext = null;
    private String mimageUrl = null;
    Bitmap mimageBitmap = null;
    PostRepository(String t, String url) {
        System.out.println("Вы добрались до конструктора PostRepository()");
        mtext = t;
        mimageUrl = url;
        //TODO get bitmap here

        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(mimageBitmap);

        if (mimageBitmap == null) {
            System.out.println("bitmap is null");
        }
        else {
            System.out.println("nice yo");
        }
        
    }
    String getText() { return mtext; }
    String getImageUrl() { return mimageUrl; }
    Bitmap getBitmap() { return mimageBitmap; }
}
