package com.facts.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facts.FactItem;
import com.facts.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<FactItem> {

    private LayoutInflater mLayoutInflater = null;

    public ListAdapter(Context context, int resource, List<FactItem> items) {
        super(context, resource, items);
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
        }

        return view;
    }

}