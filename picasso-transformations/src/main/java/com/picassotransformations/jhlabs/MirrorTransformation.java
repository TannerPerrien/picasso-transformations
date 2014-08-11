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

package com.picassotransformations.jhlabs;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class MirrorTransformation implements Transformation {

    private String key;
    
    private float opacity = 1.0f;

    private float centreY = 0.5f;

    private float distance;

    private float gap;

    public MirrorTransformation() {
        buildKey();
    }

    public MirrorTransformation setDistance(float distance) {
        this.distance = distance;
        buildKey();
        return this;
    }

    public float getDistance() {
        return distance;
    }

    public MirrorTransformation setGap(float gap) {
        this.gap = gap;
        buildKey();
        return this;
    }

    public float getGap() {
        return gap;
    }

    /**
     * Set the opacity of the reflection.
     * 
     * @param opacity the opacity.
     * @see #getOpacity
     */
    public MirrorTransformation setOpacity(float opacity) {
        this.opacity = opacity;
        buildKey();
        return this;
    }

    /**
     * Get the opacity of the reflection.
     * 
     * @return the opacity.
     * @see #setOpacity
     */
    public float getOpacity() {
        return opacity;
    }

    public MirrorTransformation setCentreY(float centreY) {
        this.centreY = centreY;
        buildKey();
        return this;
    }

    public float getCentreY() {
        return centreY;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        int h = (int) (centreY * height);
        int d = (int) (gap * height);

        Canvas canvas = new Canvas();
        Paint paint = new Paint();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        // Set background color
        canvas.drawColor(Color.BLACK);

        // Draw upper region
        RectF clipSrcF = new RectF(0, 0, width, height);
        RectF clipDestF = new RectF(0, 0, width, h);
        Matrix m = new Matrix();
        m.setRectToRect(clipSrcF, clipDestF, ScaleToFit.CENTER);
        canvas.drawBitmap(source, m, paint);

        // Draw mirror
        clipSrcF = new RectF(0, 0, width, height);
        clipDestF = new RectF(0, h + d, width, height + d);
        m = new Matrix();
        m.setRectToRect(clipSrcF, clipDestF, ScaleToFit.CENTER);
        m.preTranslate(0, height);
        m.preScale(1, -1);
        canvas.drawBitmap(source, m, paint);

        // Done with source image
        source.recycle();

        // Draw gradient over mirror
        RectF clipGradientF = new RectF(0, 0, width, height);
        m.mapRect(clipGradientF);
        paint.setShader(new LinearGradient(0, h, 0, height, Color.argb(50, 0, 0, 0), Color.argb((int) (opacity * 255), 0, 1, 0),
                Shader.TileMode.CLAMP));
        canvas.drawRect(clipGradientF, paint);

        return bitmap;
    }

    public String toString() {
        return "Effects/Mirror...";
    }
    
    private void buildKey() {
        key = MirrorTransformation.class.getCanonicalName() + "-" + opacity + "-" + centreY + "-" + distance + "-" + gap;
    }

    @Override
    public String key() {
        return key;
    }

}
