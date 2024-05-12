package com.my.voenmeh.ui.news;

import static android.view.ViewGroup.LayoutParams;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.my.voenmeh.R;
import com.my.voenmeh.Utils.Constants;
import com.squareup.picasso.Picasso;

public class NewsPost {
    TextView postText = null;
    ImageView postImage = null;

    public NewsPost(Context mContext) {
        NewsRepository nr
                = new NewsRepository(); // здесь заполняется список из медиа для постов

        // фигня для доступа к вьюшкам в активити
        Activity a = (Activity) mContext;
        LinearLayout ll = (LinearLayout) a.findViewById(R.id.news_posts);

        // int count = a.getResources().getInteger(R.integer.NUMBER_OF_POSTS);

        for (int index = 0; index < Constants.NUMBER_OF_POSTS; index++) {

            postText = new TextView(mContext);
            postText.setText(nr.listOfPosts.get(index).getText());
            postText.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            Typeface typeface = ResourcesCompat.getFont(mContext, R.font.montserrat_m);
            postText.setTypeface(typeface);

            postText.setTextSize(15);
            postText.setTextColor(Color.BLACK);
            ll.addView(postText);

            // создание одной картинки
            postImage = new ImageView(mContext);
            String url = nr.listOfPosts.get(index).getImageUrl();
            System.out.println("url: " + url);
            if (url != "") {
                Picasso.get().load(url).into(postImage);
            } else {
                String unavailablePath
                        = "https://eagle-sensors.com/wp-content/uploads/unavailable-image.jpg";
                Picasso.get().load(unavailablePath).into(postImage);
            }
            ll.addView(postImage);
        }
    }
}
