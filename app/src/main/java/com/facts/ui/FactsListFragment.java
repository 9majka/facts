package com.facts.ui;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facts.FactItem;
import com.facts.FactItems;
import com.facts.controller.NavigationController;
import com.facts.controller.FactsHolder;
import com.facts.model.FactsLoaderCallbacks;

public class FactsListFragment extends ListFragment implements FactsLoaderCallbacks {
    private final static String TAG = "FactsListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        if(items != null) {
            updateList(items);
        } else {
            NavigationController.getInstance().restore();
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
    }

    private void updateList(FactItems facts) {
        FactsListAdapter listAdapter = (FactsListAdapter)getListAdapter();
        if(listAdapter == null) {
            listAdapter = new FactsListAdapter(getActivity(), facts);
            setListAdapter(listAdapter);
        } else {
            listAdapter.clear();
            listAdapter.addAll(facts);
            setListAdapter(listAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        FactsListAdapter listAdapter = (FactsListAdapter)getListAdapter();
        FactItem item = listAdapter.getItem(position);
        Intent intent = DetailedFactActivity.newIntent(getActivity(), item.getID());
        startActivity(intent);
    }

    @Override
    public void onFactsCreated(FactItems items) {
        Log.i(TAG, "onFactsCreated\n");
        updateList(items);
    }

    @Override
    public void onFactsUpdated(FactItems items) {
        Log.i(TAG, "onFactsUpdated\n");
        FactsListAdapter listAdapter = (FactsListAdapter)getListAdapter();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Log.e(TAG, "onError ");
    }
}
