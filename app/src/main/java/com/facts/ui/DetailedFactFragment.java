package com.facts.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facts.FactItem;
import com.facts.R;
import com.facts.controller.FactsHolder;
import com.facts.database.SQLiteFactsLoader;


public class DetailedFactFragment extends Fragment {
    public static final String ARG_FACT_ID = "com.facts.ui.fact_id";
    public static final String ARG_FACT_POS = "com.facts.ui.fact_list_position";
    public static final String ARG_FACT_MODE = "com.facts.ui.fact_mode";

    private FactItem mFact = null;
    private int mViewMode;

    public static Fragment newInstance(int id, int viewMode) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FACT_ID, id);
        args.putSerializable(ARG_FACT_MODE, viewMode);
        DetailedFactFragment fragment = new DetailedFactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int factID = (int) getArguments().getSerializable(ARG_FACT_ID);
        mViewMode = (int) getArguments().getSerializable(ARG_FACT_MODE);
        mFact = FactsHolder.getInstance().getFactById(factID);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detailed_menu, menu);
        if(mViewMode == 0) {
            MenuItem item = menu.findItem(R.id.mDelete);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.mSave);
            item.setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back button

                Intent resultIntent = new Intent();
                getActivity().setResult(1, resultIntent);
                getActivity().finish();
                return true;

            case R.id.mShare:
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_TEXT, mFact.getContent());
                startActivity(Intent.createChooser(i, "Share fact"));
                break;
            case R.id.mSave:
                SQLiteFactsLoader.getInstance(getActivity()).saveFact(mFact);
                break;
            case R.id.mDelete:
                SQLiteFactsLoader.getInstance(getActivity()).deleteFact(mFact);
                break;
        }
        return super.onOptionsItemSelected(item);
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
        setHasOptionsMenu(true);
        return view;
    }
}
