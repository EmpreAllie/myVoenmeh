package com.my.voenmeh.ui.news;

import android.app.ActionBar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsPost {
    TextView postText = null;
    ImageView postImage = null;
    public NewsPost(int index) {
        NewsRepository nr = new NewsRepository();

        postText.setText(nr.listOfPosts.get(index).getText());
        //postText.setLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT);
        postImage.setImageBitmap(nr.listOfPosts.get(index).getBitmap());

        //TODO разместить эти Views на экране

    }
    public TextView getTextView() { return postText; }
    public ImageView getImageView() { return postImage; }
}
