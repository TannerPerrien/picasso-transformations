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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class GridAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<Transformation> mTransformations;

    private String mImageUrl;

    private View.OnClickListener mOnClickListener;

    public GridAdapter(Context context, List<Transformation> transformations, String imageUrl) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTransformations = transformations;
        mImageUrl = imageUrl;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mTransformations.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransformations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image, parent, false);
        }

        ImageView image = (ImageView) convertView;
        image.setTag(position);
        final Transformation transformation = mTransformations.get(position);
        Picasso.with(mContext).load(mImageUrl).transform(transformation).centerCrop().fit().into(image);
        image.setOnClickListener(mOnClickListener);
        image.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                String title = transformation.getClass().getSimpleName() + " - " + transformation;
                Toast.makeText(mContext, title, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return convertView;
    }

}
