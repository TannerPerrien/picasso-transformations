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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class GridFragment extends Fragment {

    private static final String TULIP_IMG_URL = "http://upload.wikimedia.org/wikipedia/commons/4/44/Tulip_-_floriade_canberra.jpg";

    private static final String KEY_CATEGORY = "category";

    private TransformationCategory mCategory;

    private ImageView mImageView;

    public static GridFragment newInstance(TransformationCategory category) {
        GridFragment f = new GridFragment();
        Bundle b = new Bundle();
        b.putParcelable(KEY_CATEGORY, category);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mCategory = args.getParcelable(KEY_CATEGORY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid, container, false);

        mImageView = (ImageView) view.findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        GridView grid = (GridView) view.findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(getActivity(), mCategory.getTransformations(), TULIP_IMG_URL);
        grid.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Picasso.with(getActivity()).load(TULIP_IMG_URL).transform(mCategory.getTransformations().get(position)).centerInside()
                        .fit().into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

}
