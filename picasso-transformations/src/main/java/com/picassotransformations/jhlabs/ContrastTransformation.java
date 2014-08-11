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
 * A filter to change the brightness and contrast of an image.
 */
public class ContrastTransformation extends TransferTransformation {

    private String key;
    
    private float brightness = 1.0f;

    private float contrast = 1.0f;

    public ContrastTransformation() {
        buildKey();
    }
    
    protected float transferFunction(float f) {
        f = f * brightness;
        f = (f - 0.5f) * contrast + 0.5f;
        return f;
    }

    /**
     * Set the filter brightness.
     * 
     * @param brightness the brightness in the range 0 to 1
     * @min-value 0
     * @max-value 0
     * @see #getBrightness
     */
    public ContrastTransformation setBrightness(float brightness) {
        this.brightness = brightness;
        buildKey();
        initialized = false;
        return this;
    }

    /**
     * Get the filter brightness.
     * 
     * @return the brightness in the range 0 to 1
     * @see #setBrightness
     */
    public float getBrightness() {
        return brightness;
    }

    /**
     * Set the filter contrast.
     * 
     * @param contrast the contrast in the range 0 to 1
     * @min-value 0
     * @max-value 0
     * @see #getContrast
     */
    public ContrastTransformation setContrast(float contrast) {
        this.contrast = contrast;
        buildKey();
        initialized = false;
        return this;
    }

    /**
     * Get the filter contrast.
     * 
     * @return the contrast in the range 0 to 1
     * @see #setContrast
     */
    public float getContrast() {
        return contrast;
    }

    public String toString() {
        return "Colors/Contrast...";
    }

    private void buildKey() {
        key = ContrastTransformation.class.getCanonicalName() + "-" + brightness + "-" + contrast;
    }
    
    @Override
    public String key() {
        return key;
    }

}
