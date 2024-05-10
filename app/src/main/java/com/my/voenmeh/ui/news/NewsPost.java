package com.my.voenmeh.ui.news;

import static android.view.ViewGroup.LayoutParams;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.voenmeh.Utils.Constants;
import com.my.voenmeh.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.ls.LSOutput;

public class NewsPost {
    TextView postText = null;
    ImageView postImage = null;
    public NewsPost(Context mContext) {
        NewsRepository nr = new NewsRepository(); // здесь заполняется список из медиа для постов

        // фигня для доступа к вьюшкам в активити
        Activity a = (Activity) mContext;
        LinearLayout ll = (LinearLayout) a.findViewById(R.id.news_posts);

        //int count = a.getResources().getInteger(R.integer.NUMBER_OF_POSTS);

        for (int index = 0; index < Constants.NUMBER_OF_POSTS; index++) {

            // создание одного текствью
            postText = new TextView(mContext);
            postText.setText(nr.listOfPosts.get(index).getText());
            postText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ll.addView(postText);

            // создание одной картинки
            postImage = new ImageView(mContext);
            String url = nr.listOfPosts.get(index).getImageUrl();
            System.out.println("url: " + url);
            if (url != "") {
                Picasso.get().load(url).into(postImage);
            }
            else {
                String unavailablePath = "https://eagle-sensors.com/wp-content/uploads/unavailable-image.jpg";
                Picasso.get().load(unavailablePath).into(postImage);
            }
            ll.addView(postImage);
        }
    }
}
