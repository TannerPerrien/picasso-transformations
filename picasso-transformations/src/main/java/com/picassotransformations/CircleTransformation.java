/*
 * Copyright (C) 2014 Tanner Perrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class CircleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        // Dimensions
        int width = source.getWidth();
        int height = source.getHeight();
        int diameter = Math.min(width, height);
        float radius = diameter / 2f;

        // Compute x/y for centered square
        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;

        // Create squared bitmap
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, diameter, diameter);
        if (squaredBitmap != source) {
            source.recycle();
        }

        // Set up paint
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);

        // Draw circular bitmap
        Bitmap bitmap = Bitmap.createBitmap(diameter, diameter, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(radius, radius, radius, paint);

        // Cleanup
        squaredBitmap.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return CircleTransformation.class.getCanonicalName();
    }

    @Override
    public String toString() {
        return "Circle";
    }

}
