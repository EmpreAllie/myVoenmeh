package com.my.voenmeh.ui.news;

import static android.view.ViewGroup.LayoutParams;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.voenmeh.R;
import com.squareup.picasso.Picasso;

public class NewsPost {
    TextView postText = null;
    ImageView postImage = null;
    public NewsPost(int index, Context mContext) {
        NewsRepository nr = new NewsRepository();

        // хуйня для доступа к вьюшкам в активити
        Activity a = (Activity) mContext;
        LinearLayout ll = (LinearLayout) a.findViewById(R.id.news_posts);

        // создание одного текствью
        postText = new TextView(mContext);
        postText.setText(nr.listOfPosts.get(index).getText());
        postText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.addView(postText);

        // создание одной картинки
        postImage = new ImageView(mContext);
        String url = nr.listOfPosts.get(index).getImageUrl();
        Picasso.get().load(url).into(postImage);
        ll.addView(postImage);

    }
    public TextView getTextView() { return postText; }
    public ImageView getImageView() { return postImage; }
}
