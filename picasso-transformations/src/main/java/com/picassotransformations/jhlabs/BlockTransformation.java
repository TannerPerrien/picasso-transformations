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
 * A Filter to pixellate images.
 */
public class BlockTransformation implements Transformation {

    private int blockSize = 2;

    /**
     * Construct a BlockFilter.
     */
    public BlockTransformation() {
    }

    /**
     * Construct a BlockFilter.
     * 
     * @param blockSize
     *            the number of pixels along each block edge. Range: 1-100+
     */
    public BlockTransformation(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = source.copy(source.getConfig(), true);
        source.recycle();

        int[] pixels = new int[blockSize * blockSize];
        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                int w = Math.min(blockSize, width - x);
                int h = Math.min(blockSize, height - y);
                int t = w * h;
                bitmap.getPixels(pixels, 0, w, x, y, w, h);
                int r = 0, g = 0, b = 0;
                int argb;
                int i = 0;
                for (int by = 0; by < h; by++) {
                    for (int bx = 0; bx < w; bx++) {
                        argb = pixels[i];
                        r += (argb >> 16) & 0xff;
                        g += (argb >> 8) & 0xff;
                        b += argb & 0xff;
                        i++;
                    }
                }
                argb = ((r / t) << 16) | ((g / t) << 8) | (b / t);
                i = 0;
                for (int by = 0; by < h; by++) {
                    for (int bx = 0; bx < w; bx++) {
                        pixels[i] = (pixels[i] & 0xff000000) | argb;
                        i++;
                    }
                }
                bitmap.setPixels(pixels, 0, w, x, y, w, h);
            }
        }

        return bitmap;
    }

    public String toString() {
        return "Pixellate/Mosaic...";
    }

    @Override
    public String key() {
        return BlockTransformation.class.getCanonicalName() + "-" + blockSize;
    }
}
