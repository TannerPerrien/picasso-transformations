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
 * A filter which replaces one color by another in an image. This is frankly, not often useful, but has its occasional
 * uses when dealing with GIF transparency and the like.
 */
public class MapColorsTransformation extends PointTransformation {

    private String key;
    
    private int oldColor;

    private int newColor;

    /**
     * Construct a MapColorsFilter.
     */
    public MapColorsTransformation() {
        this(0xffffffff, 0xff000000);
    }

    /**
     * Construct a MapColorsFilter.
     * 
     * @param oldColor the color to replace
     * @param newColor the color to replace it with
     */
    public MapColorsTransformation(int oldColor, int newColor) {
        canFilterIndexColorModel = true;
        this.oldColor = oldColor;
        this.newColor = newColor;
        
        key = MapColorsTransformation.class.getCanonicalName() + "-" + oldColor + "-" + newColor;
    }

    public int filterRGB(int x, int y, int rgb) {
        if (rgb == oldColor)
            return newColor;
        return rgb;
    }

    @Override
    public String key() {
        return key;
    }
}
