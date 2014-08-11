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

import com.picassotransformations.Color;

public class HSBAdjustTransformation extends PointTransformation {

    private String key;
    
    public float hFactor, sFactor, bFactor;
    private float[] hsb = new float[3];
    
    public HSBAdjustTransformation() {
        this(0, 0, 0);
    }

    public HSBAdjustTransformation(float r, float g, float b) {
        hFactor = r;
        sFactor = g;
        bFactor = b;
        canFilterIndexColorModel = true;
        buildKey();
    }

    public HSBAdjustTransformation setHFactor( float hFactor ) {
        this.hFactor = hFactor;
        buildKey();
        return this;
    }
    
    public float getHFactor() {
        return hFactor;
    }
    
    public HSBAdjustTransformation setSFactor( float sFactor ) {
        this.sFactor = sFactor;
        buildKey();
        return this;
    }
    
    public float getSFactor() {
        return sFactor;
    }
    
    public HSBAdjustTransformation setBFactor( float bFactor ) {
        this.bFactor = bFactor;
        buildKey();
        return this;
    }
    
    public float getBFactor() {
        return bFactor;
    }
    
    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        Color.RGBtoHSB(r, g, b, hsb);
        hsb[0] += hFactor;
        while (hsb[0] < 0)
            hsb[0] += Math.PI*2;
        hsb[1] += sFactor;
        if (hsb[1] < 0)
            hsb[1] = 0;
        else if (hsb[1] > 1.0)
            hsb[1] = 1.0f;
        hsb[2] += bFactor;
        if (hsb[2] < 0)
            hsb[2] = 0;
        else if (hsb[2] > 1.0)
            hsb[2] = 1.0f;
        rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return a | (rgb & 0xffffff);
    }

    public String toString() {
        return "Colors/Adjust HSB...";
    }

    private void buildKey() {
        key = HSBAdjustTransformation.class.getCanonicalName() + "-" + hFactor + "-" + sFactor + "-" + bFactor;
    }
    
    @Override
    public String key() {
        return key;
    }
}
