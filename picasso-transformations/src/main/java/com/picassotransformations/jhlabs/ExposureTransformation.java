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
 * A filter which changes the exposure of an image.
 */
public class ExposureTransformation extends TransferTransformation {

    private String key;
    
    private float exposure = 1.0f;
    
    public ExposureTransformation() {
        buildKey();
    }

    protected float transferFunction(float f) {
        return 1 - (float) Math.exp(-f * exposure);
    }

    /**
     * Set the exposure level.
     * 
     * @param exposure the exposure level
     * @min-value 0
     * @max-value 5+
     * @see #getExposure
     */
    public void setExposure(float exposure) {
        this.exposure = exposure;
        initialized = false;
        buildKey();
    }

    /**
     * Get the exposure level.
     * 
     * @return the exposure level
     * @see #setExposure
     */
    public float getExposure() {
        return exposure;
    }

    public String toString() {
        return "Colors/Exposure...";
    }

    private void buildKey() {
        key = ExposureTransformation.class.getCanonicalName() + "-" + exposure;
    }
    
    @Override
    public String key() {
        return key;
    }

}
