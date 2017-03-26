package com.facts.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facts.FactItem;
import com.facts.FactItems;
import com.facts.R;
import com.facts.controller.FactsHolder;
import com.facts.controller.NavigationController;
import com.facts.model.FactsLoaderCallbacks;

public class FactsListFragment extends Fragment implements FactsLoaderCallbacks {
    private final static String TAG = "FactsListFragment";
    private static final int REQUEST_FACT = 1;
    private RecycleViewAdapter mRecycleViewAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view_layout, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        return view;
    }

    public FactsListFragment() {
        super();
        Log.i(TAG, "FactsListFragment\n");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate\n");
        super.onCreate(savedInstanceState);
        NavigationController.getInstance().addListener(this);
        FactItems items = FactsHolder.getInstance().getCurrentFactItems();
        if(items == null) {
            mRecycleViewAdapter = new RecycleViewAdapter(new FactItems());
            NavigationController.getInstance().restore();
        } else {
            mRecycleViewAdapter = new RecycleViewAdapter(items);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NavigationController.getInstance().removeListener(this);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume\n");
        super.onResume();
        mRecycleViewAdapter.notifyDataSetChanged();
    }

    private void updateList(FactItems facts) {
        if(mRecycleViewAdapter != null) {
            mRecycleViewAdapter.swap(facts);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FACT) {

        }
    }

    @Override
    public void onFactsCreated(FactItems items) {
        Log.i(TAG, "onFactsCreated\n");
        updateList(items);
        if(mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void onFactsUpdated(FactItems items) {
        Log.i(TAG, "onFactsUpdated\n");
        mRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Log.e(TAG, "onError ");
    }

    private class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.FactViewHolder>{
        FactItems mItems;
        RecycleViewAdapter(FactItems items) {
            mItems = items;
        }
        @Override
        public FactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new FactViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FactViewHolder holder, int position) {
            FactItem factItem = mItems.get(position);
            holder.factContent.setText(Html.fromHtml(factItem.getContent()), TextView.BufferType.SPANNABLE);

            if(factItem.getBitmap() != null) {
                holder.factIcon.setImageBitmap(factItem.getBitmap());
            } else {
                holder.factIcon.setImageResource(R.mipmap.default_icon);
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        void swap(FactItems datas){
            mItems.clear();
            mItems.addAll(datas);
            notifyDataSetChanged();
        }

        class FactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView factContent;
            ImageView factIcon;

            FactViewHolder(View itemView) {
                super(itemView);
                factContent = (TextView)itemView.findViewById(R.id.fact_content);
                factIcon = (ImageView)itemView.findViewById(R.id.fact_icon);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                getAdapterPosition();
                int viewMode = NavigationController.getInstance().isOffline() ? 1 : 0;
                Intent intent = DetailedFactActivity.newIntent(getActivity(), mItems.get(getAdapterPosition()).getID(), getAdapterPosition(), viewMode);
                startActivityForResult(intent, REQUEST_FACT);
            }
        }

    }
}
