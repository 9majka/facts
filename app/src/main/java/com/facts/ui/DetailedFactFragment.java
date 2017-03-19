package com.facts.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facts.FactItem;
import com.facts.R;
import com.facts.model.FactsLoader;


public class DetailedFactFragment extends Fragment {
    public static final String ARG_FACT_ID = "com.facts.ui.fact_id";
    private FactItem mFact = null;

    public static Fragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FACT_ID, id);
        DetailedFactFragment fragment = new DetailedFactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int factID = (int) getArguments().getSerializable(ARG_FACT_ID);
        mFact = FactsLoader.getInstance().getFactById(factID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_fact_fragment, container, false);
        if (mFact != null) {
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(mFact.getContent()), TextView.BufferType.SPANNABLE);

            if(mFact.getBitmap() != null) {
                ImageView img= (ImageView) view.findViewById(R.id.imageView);
                img.setImageBitmap(mFact.getBitmap());
            }
        }
        return view;
    }
}
