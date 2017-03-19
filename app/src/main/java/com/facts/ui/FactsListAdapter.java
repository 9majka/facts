package com.facts.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facts.FactItem;
import com.facts.R;

import java.util.List;

public class FactsListAdapter extends ArrayAdapter<FactItem> {

    private LayoutInflater mLayoutInflater = null;

    public FactsListAdapter(Context context, List<FactItem> items) {
        super(context, 0, items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.factlist, parent, false);
        }

        FactItem factItem = getItem(position);

        if (factItem != null) {
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(factItem.getContent()), TextView.BufferType.SPANNABLE);

            if(factItem.getBitmap() != null) {
                ImageView img= (ImageView) view.findViewById(R.id.icon);
                img.setImageBitmap(factItem.getBitmap());
            }
        }

        return view;
    }

}