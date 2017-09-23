package com.android.bigserj.mainActivity;


import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static com.android.bigserj.Constants.URL_GIF_EARTH;

public final class ImageBinder {

    @BindingAdapter({"bind:earth_url"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}

