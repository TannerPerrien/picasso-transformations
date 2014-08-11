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

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

public class RSGaussianBlurTransformation implements Transformation {

    private String key;
    
    private Context mContext;

    private int mRadius;
    
    /**
     * Constructor.
     * 
     * @param context The context.
     * @param radius The blur radius: (0, 25]
     */
    public RSGaussianBlurTransformation(Context context, int radius) {
        mContext = context;
        mRadius = radius;
        
        key = RSGaussianBlurTransformation.class.getCanonicalName() + "-" + radius;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (Build.VERSION.SDK_INT < 17) {
            return source;
        }
        
        RenderScript rs = RenderScript.create(mContext);
        Allocation input = Allocation.createFromBitmap(rs, source, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(mRadius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(source);
        return source;
    }

    @Override
    public String key() {
        return key;
    }

}
