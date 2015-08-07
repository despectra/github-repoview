package com.despectra.githubrepoview;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

/**
 * Utility class
 */
public class Utils {
    /**
     * Creates an instance of Gson parser protected from StackOverflowException when using it with RealmObject instances
     * @return Realm compatible Gson parser instance
     */
    public static Gson getDefaultGsonInstance() {
        return new GsonBuilder()
                .create();
    }

    @BindingAdapter("app:imageUrl")
    public static void loadImageAsync(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .resizeDimen(R.dimen.item_icon_width, R.dimen.item_icon_height)
                .centerCrop()
                .transform(new RoundedTransformation(40, 0))
                .placeholder(R.drawable.avatar_placeholder)
                .into(view);
    }

}
