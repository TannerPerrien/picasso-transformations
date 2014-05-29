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

    private float brightness = 1.0f;

    private float contrast = 1.0f;

    /**
     * 
     * @param brightness
     *            the brightness in the range 0 to 1
     * @param contrast
     *            the contrast in the range 0 to 1
     */
    public ContrastTransformation(float brightness, float contrast) {
        this.brightness = brightness;
        this.contrast = contrast;
    }

    protected float transferFunction(float f) {
        f = f * brightness;
        f = (f - 0.5f) * contrast + 0.5f;
        return f;
    }

    public String toString() {
        return "Colors/Contrast...";
    }

    @Override
    public String key() {
        return ContrastTransformation.class.getCanonicalName() + "-" + brightness + "-" + contrast;
    }

}
