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

public class RGBAdjustTransformation extends PointTransformation {

    private String key;
    
    public float rFactor, gFactor, bFactor;

    public RGBAdjustTransformation() {
        this(0, 0, 0);
    }

    public RGBAdjustTransformation(float r, float g, float b) {
        rFactor = 1 + r;
        gFactor = 1 + g;
        bFactor = 1 + b;
        canFilterIndexColorModel = true;
        
        buildKey();
    }

    public void setRFactor(float rFactor) {
        this.rFactor = 1 + rFactor;
        buildKey();
    }

    public float getRFactor() {
        return rFactor - 1;
    }

    public void setGFactor(float gFactor) {
        this.gFactor = 1 + gFactor;
        buildKey();
    }

    public float getGFactor() {
        return gFactor - 1;
    }

    public void setBFactor(float bFactor) {
        this.bFactor = 1 + bFactor;
        buildKey();
    }

    public float getBFactor() {
        return bFactor - 1;
    }

    public int[] getLUT() {
        int[] lut = new int[256];
        for (int i = 0; i < 256; i++) {
            lut[i] = filterRGB(0, 0, (i << 24) | (i << 16) | (i << 8) | i);
        }
        return lut;
    }

    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        r = PixelUtils.clamp((int) (r * rFactor));
        g = PixelUtils.clamp((int) (g * gFactor));
        b = PixelUtils.clamp((int) (b * bFactor));
        return a | (r << 16) | (g << 8) | b;
    }

    public String toString() {
        return "Colors/Adjust RGB...";
    }

    private void buildKey() {
        key = RGBAdjustTransformation.class.getCanonicalName() + "-" + rFactor + "-" + gFactor + "-" + bFactor;
    }
    
    @Override
    public String key() {
        return key;
    }
}
