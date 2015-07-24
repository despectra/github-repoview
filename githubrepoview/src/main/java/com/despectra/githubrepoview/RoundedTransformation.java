package com.despectra.githubrepoview;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

// enables hardware accelerated rounded corners for Picasso
// original idea here : http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
public class RoundedTransformation implements Transformation {
    private final int mRadius;
    private final int mMargin;  // dp

    private final String mKey;

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(final int radius, final int margin) {
        mRadius = radius;
        mMargin = margin;
        mKey = "rounded(radius=" + mRadius + ", margin=" + mMargin + ")";
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(
                new RectF(mMargin, mMargin, source.getWidth() - mMargin, source.getHeight() - mMargin),
                mRadius, mRadius, paint
        );

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return mKey;
    }
}