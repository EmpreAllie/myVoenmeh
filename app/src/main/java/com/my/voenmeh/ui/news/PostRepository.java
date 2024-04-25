package com.my.voenmeh.ui.news;

import android.graphics.Bitmap;

import com.my.voenmeh.Request.AsyncGettingBitmapFromUrl;

public class PostRepository {
    private String mtext = null;
    //private String mimageUrl = null;
    Bitmap mimageBitmap = null;
    PostRepository(String t, String u) {
        System.out.println("Вы добрались до конструктора PostRepository()");
        mtext = t;
        AsyncGettingBitmapFromUrl a = new AsyncGettingBitmapFromUrl();
        mimageBitmap = a.doInBackground(u);

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
