package com.my.voenmeh.ui.news;

import android.graphics.Bitmap;

public class PostRepository {
    private String mtext = null;
    //private String mimageUrl = null;
    Bitmap mimageBitmap = null;
    PostRepository(String t, String u) {
        System.out.println("Вы добрались до конструктора PostRepository()");
        mtext = t;
        //TODO get bitmap here

        if (mimageBitmap == null) {
            System.out.println("oh no cringe");
        }
        else {
            System.out.println("nice yo");
        }
        
    }
    String getText() { return mtext; }
    Bitmap getBitmap() { return mimageBitmap; }
}
