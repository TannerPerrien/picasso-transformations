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
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

/**
 * A filter which acts as a superclass for filters which need to have the whole
 * image in memory to do their stuff.
 */
public abstract class WholeImageTransformation implements Transformation {

    /**
     * The output image bounds.
     */
    protected Rect transformedSpace;

    /**
     * The input image bounds.
     */
    protected Rect originalSpace;

    /**
     * Construct a WholeImageFilter.
     */
    public WholeImageTransformation() {
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = source.copy(source.getConfig(), true);
        source.recycle();

        originalSpace = new Rect(0, 0, width, height);
        transformedSpace = new Rect(0, 0, width, height);
        transformSpace(transformedSpace);

        int[] inPixels = new int[width * height];
        bitmap.getPixels(inPixels, 0, width, 0, 0, width, height);
        inPixels = filterPixels(width, height, inPixels, transformedSpace);

        bitmap.setPixels(inPixels, 0, transformedSpace.width(), 0, 0, transformedSpace.width(),
                transformedSpace.height());

        return bitmap;
    }

    /**
     * Calculate output bounds for given input bounds.
     * 
     * @param rect
     *            input and output rectangle
     */
    protected void transformSpace(Rect rect) {
    }

    /**
     * Actually filter the pixels.
     * 
     * @param width
     *            the image width
     * @param height
     *            the image height
     * @param inPixels
     *            the image pixels
     * @param transformedSpace
     *            the output bounds
     * @return the output pixels
     */
    protected abstract int[] filterPixels(int width, int height, int[] inPixels, Rect transformedSpace);
}
