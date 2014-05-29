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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.picassotransformations.GrayscaleTransformation;
import com.picassotransformations.StackBlurTransformation;
import com.picassotransformations.jhlabs.BlockTransformation;
import com.picassotransformations.jhlabs.BlurTransformation;
import com.picassotransformations.jhlabs.ContrastTransformation;
import com.picassotransformations.jhlabs.DiffusionTransformation;
import com.picassotransformations.jhlabs.EdgeTransformation;
import com.picassotransformations.jhlabs.EmbossTransformation;
import com.picassotransformations.jhlabs.EqualizeTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class SampleActivity extends Activity {

    private static final String TULIP_IMG_URL = "http://upload.wikimedia.org/wikipedia/commons/4/44/Tulip_-_floriade_canberra.jpg";

    private static List<Transformation> sTransformations = new ArrayList<Transformation>();

    static {
        sTransformations.add(new GrayscaleTransformation());
        sTransformations.add(new StackBlurTransformation(20));
        sTransformations.add(new BlurTransformation());
        sTransformations.add(new ContrastTransformation(.7f, 1f));
        sTransformations.add(new BlockTransformation(20));
        sTransformations.add(new EdgeTransformation());
        sTransformations.add(new EmbossTransformation());
        sTransformations.add(new EqualizeTransformation());
        sTransformations.add(new DiffusionTransformation());
    }

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mImageView = (ImageView) findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        GridView grid = (GridView) findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(this, sTransformations, TULIP_IMG_URL);
        grid.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Picasso.with(SampleActivity.this).load(TULIP_IMG_URL).transform(sTransformations.get(position))
                        .centerInside().fit().into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            }
        });
    }

}
