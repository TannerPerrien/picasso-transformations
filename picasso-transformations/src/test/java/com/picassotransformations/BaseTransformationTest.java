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
package com.picassotransformations;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBitmapFactory;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public abstract class BaseTransformationTest {

    private static final String TULIP_IMG_URL = "http://upload.wikimedia.org/wikipedia/commons/4/44/Tulip_-_floriade_canberra.jpg";

    private static Bitmap sBitmap;

    protected Bitmap getCopyOfBitmap() {
        if (sBitmap == null) {
            try {
                URL url = new URL(TULIP_IMG_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                sBitmap = ShadowBitmapFactory.decodeStream(input);
            } catch (IOException e) {
            }
        }
        return sBitmap.copy(Bitmap.Config.ARGB_8888, false);
    }

    @Test
    public void checkTransform() throws Exception {
        Transformation t = getTransformation();
        Bitmap copy = getCopyOfBitmap();
        Bitmap bm = t.transform(copy);
        assertThat(bm).isNotNull();
        assertThat(bm).isNotEqualTo(sBitmap);
        assertThat(copy).isRecycled();
    }
    
    @Test
    public void checkKey() {
        assertThat(getTransformation().key()).isNotNull();
    }

    protected abstract Transformation getTransformation();

}
