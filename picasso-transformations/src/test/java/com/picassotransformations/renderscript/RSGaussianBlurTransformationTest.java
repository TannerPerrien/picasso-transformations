/*
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

package com.picassotransformations.renderscript;

import static org.fest.assertions.api.ANDROID.assertThat;

import org.junit.Test;
import org.robolectric.Robolectric;

import android.graphics.Bitmap;

import com.picassotransformations.BaseTransformationTest;
import com.squareup.picasso.Transformation;

public class RSGaussianBlurTransformationTest extends BaseTransformationTest {

    @Test
    @Override
    public void checkTransform() throws Exception {
        Transformation t = getTransformation();
        Bitmap copy = getCopyOfBitmap();
        Bitmap bm = t.transform(copy);
        assertThat(bm).isNotNull();
    }
    
    @Override
    protected Transformation getTransformation() {
        return new RSGaussianBlurTransformation(Robolectric.application, 5);
    }

}
