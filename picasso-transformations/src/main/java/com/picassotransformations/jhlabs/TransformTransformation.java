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
 * An abstract superclass for filters which distort images in some way. The subclass only needs to override
 * two methods to provide the mapping between source and destination pixels.
 */
public abstract class TransformTransformation implements Transformation {

    /**
     * Treat pixels off the edge as zero.
     */
    public final static int ZERO = 0;

    /**
     * Clamp pixels to the image edges.
     */
    public final static int CLAMP = 1;

    /**
     * Wrap pixels off the edge onto the oppsoite edge.
     */
    public final static int WRAP = 2;

    /**
     * Clamp pixels RGB to the image edges, but zero the alpha. This prevents gray borders on your image.
     */
    public final static int RGB_CLAMP = 3;

    /**
     * Use nearest-neighbout interpolation.
     */
    public final static int NEAREST_NEIGHBOUR = 0;

    /**
     * Use bilinear interpolation.
     */
    public final static int BILINEAR = 1;

    /**
     * The action to take for pixels off the image edge.
     */
    protected int edgeAction = RGB_CLAMP;

    /**
     * The type of interpolation to use.
     */
    protected int interpolation = BILINEAR;

    /**
     * The output image rectangle.
     */
    protected Rect transformedSpace;

    /**
     * The input image rectangle.
     */
    protected Rect originalSpace;

    /**
     * Set the action to perform for pixels off the edge of the image.
     * 
     * @param edgeAction one of ZERO, CLAMP or WRAP
     * @see #getEdgeAction
     */
    public void setEdgeAction(int edgeAction) {
        this.edgeAction = edgeAction;
    }

    /**
     * Get the action to perform for pixels off the edge of the image.
     * 
     * @return one of ZERO, CLAMP or WRAP
     * @see #setEdgeAction
     */
    public int getEdgeAction() {
        return edgeAction;
    }

    /**
     * Set the type of interpolation to perform.
     * 
     * @param interpolation one of NEAREST_NEIGHBOUR or BILINEAR
     * @see #getInterpolation
     */
    public void setInterpolation(int interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * Get the type of interpolation to perform.
     * 
     * @return one of NEAREST_NEIGHBOUR or BILINEAR
     * @see #setInterpolation
     */
    public int getInterpolation() {
        return interpolation;
    }

    /**
     * Inverse transform a point. This method needs to be overriden by all subclasses.
     * 
     * @param x the X position of the pixel in the output image
     * @param y the Y position of the pixel in the output image
     * @param out the position of the pixel in the input image
     */
    protected abstract void transformInverse(int x, int y, float[] out);

    /**
     * Forward transform a rectangle. Used to determine the size of the output image.
     * 
     * @param rect the rectangle to transform
     */
    protected void transformSpace(Rect rect) {
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

        if (interpolation == NEAREST_NEIGHBOUR)
            return filterPixelsNN(bitmap, width, height, inPixels, transformedSpace);

        int srcWidth = width;
        int srcHeight = height;
        int srcWidth1 = width - 1;
        int srcHeight1 = height - 1;
        int outWidth = transformedSpace.width();
        int outHeight = transformedSpace.height();
        int outX, outY;
        int[] outPixels = new int[outWidth];

        outX = transformedSpace.left;
        outY = transformedSpace.top;
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(outX + x, outY + y, out);
                int srcX = (int) Math.floor(out[0]);
                int srcY = (int) Math.floor(out[1]);
                float xWeight = out[0] - srcX;
                float yWeight = out[1] - srcY;
                int nw, ne, sw, se;

                if (srcX >= 0 && srcX < srcWidth1 && srcY >= 0 && srcY < srcHeight1) {
                    // Easy case, all corners are in the image
                    int i = srcWidth * srcY + srcX;
                    nw = inPixels[i];
                    ne = inPixels[i + 1];
                    sw = inPixels[i + srcWidth];
                    se = inPixels[i + srcWidth + 1];
                } else {
                    // Some of the corners are off the image
                    nw = getPixel(inPixels, srcX, srcY, srcWidth, srcHeight);
                    ne = getPixel(inPixels, srcX + 1, srcY, srcWidth, srcHeight);
                    sw = getPixel(inPixels, srcX, srcY + 1, srcWidth, srcHeight);
                    se = getPixel(inPixels, srcX + 1, srcY + 1, srcWidth, srcHeight);
                }
                outPixels[x] = ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
            }
            bitmap.setPixels(outPixels, 0, transformedSpace.width(), 0, y, transformedSpace.width(), 1);
        }
        return bitmap;
    }

    final private int getPixel(int[] pixels, int x, int y, int width, int height) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            switch (edgeAction) {
            case ZERO:
            default:
                return 0;
            case WRAP:
                return pixels[ (ImageMath.mod(y, height) * width) + ImageMath.mod(x, width)];
            case CLAMP:
                return pixels[ (ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)];
            case RGB_CLAMP:
                return pixels[ (ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)] & 0x00ffffff;
            }
        }
        return pixels[y * width + x];
    }

    protected Bitmap filterPixelsNN(Bitmap dst, int width, int height, int[] inPixels, Rect transformedSpace) {
        int srcWidth = width;
        int srcHeight = height;
        int outWidth = transformedSpace.width();
        int outHeight = transformedSpace.height();
        int outX, outY, srcX, srcY;
        int[] outPixels = new int[outWidth];

        outX = transformedSpace.left;
        outY = transformedSpace.top;
        int[] rgb = new int[4];
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(outX + x, outY + y, out);
                srcX = (int) out[0];
                srcY = (int) out[1];
                // int casting rounds towards zero, so we check out[0] < 0, not srcX < 0
                if (out[0] < 0 || srcX >= srcWidth || out[1] < 0 || srcY >= srcHeight) {
                    int p;
                    switch (edgeAction) {
                    case ZERO:
                    default:
                        p = 0;
                        break;
                    case WRAP:
                        p = inPixels[ (ImageMath.mod(srcY, srcHeight) * srcWidth) + ImageMath.mod(srcX, srcWidth)];
                        break;
                    case CLAMP:
                        p = inPixels[ (ImageMath.clamp(srcY, 0, srcHeight - 1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth - 1)];
                        break;
                    case RGB_CLAMP:
                        p = inPixels[ (ImageMath.clamp(srcY, 0, srcHeight - 1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth - 1)] & 0x00ffffff;
                    }
                    outPixels[x] = p;
                } else {
                    int i = srcWidth * srcY + srcX;
                    rgb[0] = inPixels[i];
                    outPixels[x] = inPixels[i];
                }
            }
            dst.setPixels(outPixels, 0, transformedSpace.width(), 0, y, transformedSpace.width(), 1);
        }
        return dst;
    }

}
