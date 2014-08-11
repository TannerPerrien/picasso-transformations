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
 * A filter which simply multiplies pixel values by a given scale factor.
 */
public class RescaleTransformation extends TransferTransformation {

    private String key;
    
    private float scale = 1.0f;

    public RescaleTransformation() {
        buildKey();
    }

    public RescaleTransformation(float scale) {
        this.scale = scale;
        buildKey();
    }

    protected float transferFunction(float v) {
        return v * scale;
    }

    /**
     * Specifies the scale factor.
     * 
     * @param scale the scale factor.
     * @min-value 1
     * @max-value 5+
     * @see #getScale
     */
    public void setScale(float scale) {
        this.scale = scale;
        buildKey();
        initialized = false;
    }

    /**
     * Returns the scale factor.
     * 
     * @return the scale factor.
     * @see #setScale
     */
    public float getScale() {
        return scale;
    }

    public String toString() {
        return "Colors/Rescale...";
    }
    
    private void buildKey() {
        key = RescaleTransformation.class.getCanonicalName() + "-" + scale;
    }

    @Override
    public String key() {
        return key;
    }

}
