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

/**
 * This filter applies a marbling effect to an image, displacing pixels by random amounts.
 */
public class MarbleTransformation extends TransformTransformation {

    private String key;
    
    private float[] sinTable, cosTable;

    private float xScale = 4;

    private float yScale = 4;

    private float amount = 1;

    private float turbulence = 1;

    public MarbleTransformation() {
        setEdgeAction(CLAMP);
        buildKey();
    }

    /**
     * Set the X scale of the effect.
     * 
     * @param xScale the scale.
     * @see #getXScale
     */
    public MarbleTransformation setXScale(float xScale) {
        this.xScale = xScale;
        buildKey();
        return this;
    }

    /**
     * Get the X scale of the effect.
     * 
     * @return the scale.
     * @see #setXScale
     */
    public float getXScale() {
        return xScale;
    }

    /**
     * Set the Y scale of the effect.
     * 
     * @param yScale the scale.
     * @see #getYScale
     */
    public MarbleTransformation setYScale(float yScale) {
        this.yScale = yScale;
        buildKey();
        return this;
    }

    /**
     * Get the Y scale of the effect.
     * 
     * @return the scale.
     * @see #setYScale
     */
    public float getYScale() {
        return yScale;
    }

    /**
     * Set the amount of effect.
     * 
     * @param amount the amount
     * @min-value 0
     * @max-value 1
     * @see #getAmount
     */
    public MarbleTransformation setAmount(float amount) {
        this.amount = amount;
        buildKey();
        return this;
    }

    /**
     * Get the amount of effect.
     * 
     * @return the amount
     * @see #setAmount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Specifies the turbulence of the effect.
     * 
     * @param turbulence the turbulence of the effect.
     * @min-value 0
     * @max-value 1
     * @see #getTurbulence
     */
    public MarbleTransformation setTurbulence(float turbulence) {
        this.turbulence = turbulence;
        buildKey();
        return this;
    }

    /**
     * Returns the turbulence of the effect.
     * 
     * @return the turbulence of the effect.
     * @see #setTurbulence
     */
    public float getTurbulence() {
        return turbulence;
    }

    private void initialize() {
        sinTable = new float[256];
        cosTable = new float[256];
        for (int i = 0; i < 256; i++) {
            float angle = ImageMath.TWO_PI * i / 256f * turbulence;
            sinTable[i] = (float) (-yScale * Math.sin(angle));
            cosTable[i] = (float) (yScale * Math.cos(angle));
        }
    }

    private int displacementMap(int x, int y) {
        return PixelUtils.clamp((int) (127 * (1 + Noise.noise2(x / xScale, y / xScale))));
    }

    protected void transformInverse(int x, int y, float[] out) {
        int displacement = displacementMap(x, y);
        out[0] = x + sinTable[displacement];
        out[1] = y + cosTable[displacement];
    }

    @Override
    public Bitmap transform(Bitmap source) {
        initialize();
        return super.transform(source);
    }

    public String toString() {
        return "Distort/Marble...";
    }
    
    private void buildKey() {
        key = MarbleTransformation.class.getCanonicalName() + "-" + xScale + "-" + yScale + "-" + amount + "-" + turbulence;
    }

    @Override
    public String key() {
        return key;
    }
}
