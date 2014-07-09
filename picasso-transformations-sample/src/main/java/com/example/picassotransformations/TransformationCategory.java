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

package com.example.picassotransformations;

import java.util.Arrays;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.picassotransformations.CircleTransformation;
import com.picassotransformations.StackBlurTransformation;
import com.picassotransformations.jhlabs.BlockTransformation;
import com.picassotransformations.jhlabs.BlurTransformation;
import com.picassotransformations.jhlabs.ChannelMixTransformation;
import com.picassotransformations.jhlabs.ContrastTransformation;
import com.picassotransformations.jhlabs.CurvesTransformation;
import com.picassotransformations.jhlabs.DiffusionTransformation;
import com.picassotransformations.jhlabs.DitherTransformation;
import com.picassotransformations.jhlabs.EdgeTransformation;
import com.picassotransformations.jhlabs.EmbossTransformation;
import com.picassotransformations.jhlabs.EqualizeTransformation;
import com.picassotransformations.jhlabs.ExposureTransformation;
import com.picassotransformations.jhlabs.GainTransformation;
import com.picassotransformations.jhlabs.GrayTransformation;
import com.picassotransformations.jhlabs.GrayscaleTransformation;
import com.picassotransformations.jhlabs.HSBAdjustTransformation;
import com.picassotransformations.jhlabs.InvertTransformation;
import com.picassotransformations.jhlabs.KaleidoscopeTransformation;
import com.picassotransformations.jhlabs.LevelsTransformation;
import com.picassotransformations.jhlabs.LookupTransformation;
import com.picassotransformations.jhlabs.MapColorsTransformation;
import com.picassotransformations.jhlabs.MarbleTransformation;
import com.picassotransformations.jhlabs.MaskTransformation;
import com.picassotransformations.jhlabs.MirrorTransformation;
import com.picassotransformations.jhlabs.PolarTransformation;
import com.picassotransformations.jhlabs.PosterizeTransformation;
import com.picassotransformations.jhlabs.QuantizeTransformation;
import com.picassotransformations.jhlabs.RGBAdjustTransformation;
import com.picassotransformations.jhlabs.RescaleTransformation;
import com.picassotransformations.jhlabs.SolarizeTransformation;
import com.picassotransformations.jhlabs.ThresholdTransformation;
import com.picassotransformations.jhlabs.TritoneTransformation;
import com.picassotransformations.renderscript.RSGaussianBlurTransformation;
import com.squareup.picasso.Transformation;


public enum TransformationCategory implements Parcelable {

    COLOR_ADJUSTMENT("Color Adjustment",
            Arrays.asList(
                    new ChannelMixTransformation(),
                    new ContrastTransformation().setBrightness(.7f).setContrast(.5f),
                    new CurvesTransformation(),
                    new DiffusionTransformation(),
                    new DitherTransformation(),
                    new ExposureTransformation(),
                    new GainTransformation(),
                    new GrayTransformation(),
                    new GrayscaleTransformation(),
                    new HSBAdjustTransformation(.5f,.5f,.5f),
                    // new InvertAlphaTransformation(),
                    new InvertTransformation(),
                    new LevelsTransformation(),
                    new LookupTransformation(),
                    new MapColorsTransformation(),
                    new MaskTransformation(0xffffff00),
                    new PosterizeTransformation(),
                    new QuantizeTransformation(),
                    new RGBAdjustTransformation(),
                    new RescaleTransformation(),
                    new SolarizeTransformation(),
                    new ThresholdTransformation(),
                    new TritoneTransformation()
                    )),
    BLURRING_SHARPENING("Blurring and Sharpening",
            Arrays.asList(
                    new StackBlurTransformation(20),
                    new BlurTransformation(),
                    new RSGaussianBlurTransformation(SampleApplication.getContext(), 4)
                    )),
    DISTORTION_WARPING("Distortion and Warping",
            Arrays.asList(
                    (Transformation) new KaleidoscopeTransformation().setSides(5),
                    new MarbleTransformation().setXScale(7).setYScale(9),
                    new MirrorTransformation().setGap(.05f),
                    new PolarTransformation()
                    )),
    EFFECTS("Effects",
            Arrays.asList(
                    new BlockTransformation(20),
                    new EmbossTransformation()
                    )),
    EDGE_DETECTION("Edge Detection",
            Arrays.asList(
                    (Transformation) new EdgeTransformation()
                    )),
    OTHER("Other",
            Arrays.asList(
                    new EqualizeTransformation(),
                    new CircleTransformation()
                    ));
    
    private String mName;
    
    private List<Transformation> mTransformations;
    
    private TransformationCategory(String name, List<Transformation> transformations) {
        mName = name;
        mTransformations = transformations;
    }
    
    @Override
    public String toString() {
        return mName;
    }

    public List<Transformation> getTransformations() {
        return mTransformations;
    }
    
    public static final Parcelable.Creator<TransformationCategory> CREATOR = new Parcelable.Creator<TransformationCategory>() {  
        
        public TransformationCategory createFromParcel(Parcel in) {  
            return TransformationCategory.values()[in.readInt()];  
        }  
   
        public TransformationCategory[] newArray(int size) {  
            return new TransformationCategory[size];  
        }  
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

}
