package com.my.voenmeh.ui.news;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.my.voenmeh.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NewsRepository {
    List<PostRepository> listOfPosts = null;
    NewsRepository() {
        fetchLatestNews();
    }
    private void fetchLatestNews() {
        //TODO Обновление/заполнение списка: Логика для запросов из ВК должна быть здесь
        listOfPosts = new ArrayList<PostRepository>();

        int number_of_posts = 5;

        String text = "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
        String imageUrl = "https://sun9-33.userapi.com/impg/-Ye-AMZ__Epgw8gzYlP97a6ccOXzBmknxtCnvw/WuEBawwHTBk.jpg?size=1080x1036&quality=96&sign=6b9e5b453df618fbc8625494fa236160&type=album";
        for (int i = 0; i < number_of_posts; i++) {
            listOfPosts.add(new PostRepository(text, imageUrl));
        }
    }
}
