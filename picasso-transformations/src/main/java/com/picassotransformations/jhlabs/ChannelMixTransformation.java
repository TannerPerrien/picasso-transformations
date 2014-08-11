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

/**
 * A filter which allows the red, green and blue channels of an image to be mixed into each other.
 */
public class ChannelMixTransformation extends PointTransformation {

    private String key;
    
    private int blueGreen, redBlue, greenRed;

    private int intoR, intoG, intoB;

    public ChannelMixTransformation() {
        canFilterIndexColorModel = true;
        buildKey();
    }

    public void setBlueGreen(int blueGreen) {
        this.blueGreen = blueGreen;
        buildKey();
    }

    public int getBlueGreen() {
        return blueGreen;
    }

    public void setRedBlue(int redBlue) {
        this.redBlue = redBlue;
        buildKey();
    }

    public int getRedBlue() {
        return redBlue;
    }

    public void setGreenRed(int greenRed) {
        this.greenRed = greenRed;
        buildKey();
    }

    public int getGreenRed() {
        return greenRed;
    }

    public void setIntoR(int intoR) {
        this.intoR = intoR;
        buildKey();
    }

    public int getIntoR() {
        return intoR;
    }

    public void setIntoG(int intoG) {
        this.intoG = intoG;
        buildKey();
    }

    public int getIntoG() {
        return intoG;
    }

    public void setIntoB(int intoB) {
        this.intoB = intoB;
        buildKey();
    }

    public int getIntoB() {
        return intoB;
    }

    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        int nr = PixelUtils.clamp( (intoR * (blueGreen * g + (255 - blueGreen) * b) / 255 + (255 - intoR) * r) / 255);
        int ng = PixelUtils.clamp( (intoG * (redBlue * b + (255 - redBlue) * r) / 255 + (255 - intoG) * g) / 255);
        int nb = PixelUtils.clamp( (intoB * (greenRed * r + (255 - greenRed) * g) / 255 + (255 - intoB) * b) / 255);
        return a | (nr << 16) | (ng << 8) | nb;
    }

    public String toString() {
        return "Colors/Mix Channels...";
    }

    private void buildKey() {
        key = ChannelMixTransformation.class.getCanonicalName() + "-" + blueGreen + "-" + redBlue + "-" + greenRed + "-" + intoR + "-"
                + intoG + "-" + intoB;
    }
    
    @Override
    public String key() {
        return key;
    }
}
