package com.greencustard.datatest.android.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.greencustard.data.android.pojo.Post;
import com.greencustard.datatest.android.R;

import java.util.List;

class PostArrayAdapter extends ArrayAdapter <Post> {
    public PostArrayAdapter(Context context, int layout, List<Post> results) {
        super(context, R.layout.support_simple_spinner_dropdown_item, layout, results);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Post result = getItem(position);

        final View view;
        if (convertView != null)
            view = convertView;
        else
            view = LayoutInflater.from(getContext()).inflate(R.layout.result_post,parent,false);

        TextView title = view.findViewById(R.id.result_post_title);
        TextView body = view.findViewById(R.id.result_post_body);

        title.setText(result.getTitle());
        body.setText(result.getBody());

        return view;
    }
}
