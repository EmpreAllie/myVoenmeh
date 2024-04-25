package com.my.voenmeh.Request;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
    @Override
    public Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        bitmap = RequestMethods.downloadImage(params[0]);
        return bitmap;
    }
}
