/*
 * Copyright (C) 2006 Jerry Huxtable
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

import com.squareup.picasso.Transformation;

/**
 * An abstract superclass for point filters. The interface is the same as the
 * old RGBImageFilter.
 */
public abstract class PointTransformation implements Transformation {

    protected boolean canFilterIndexColorModel = false;

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        setDimensions(width, height);
        
        Bitmap bitmap = source.copy(source.getConfig(), true);
        source.recycle();

        int[] inPixels = new int[width];
        for (int y = 0; y < height; y++) {
            bitmap.getPixels(inPixels, 0, width, 0, y, width, 1);
            for (int x = 0; x < width; x++) {
                inPixels[x] = filterRGB(x, y, inPixels[x]);
            }
            bitmap.setPixels(inPixels, 0, width, 0, y, width, 1);
        }

        return bitmap;
    }

    public void setDimensions(int width, int height) {
    }

    public abstract int filterRGB(int x, int y, int rgb);
}
