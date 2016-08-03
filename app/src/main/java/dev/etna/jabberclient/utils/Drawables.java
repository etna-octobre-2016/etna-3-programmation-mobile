package dev.etna.jabberclient.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class Drawables {

    public static byte[] getDrawableAsByteArray(Context context, int drawableID, Bitmap.CompressFormat format) throws Exception {

        Bitmap bitmap;
        BitmapDrawable bitmapDrawable;
        Drawable drawable;
        Resources resources;
        ByteArrayOutputStream outStream;

        resources = context.getResources();
        drawable = resources.getDrawable(drawableID, null);
        bitmapDrawable = (BitmapDrawable) drawable;
        bitmap = bitmapDrawable.getBitmap();
        outStream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, outStream);
        return outStream.toByteArray();
    }
}
