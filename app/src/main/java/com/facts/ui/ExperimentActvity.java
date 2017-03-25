package com.facts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.facts.R;

public class ExperimentActvity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_layout);

        RecyclerView rv = (RecyclerView)findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter();
        rv.setAdapter(adapter);
    }

    private class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder holder, int position) {
//            personViewHolder.factContent.setText(persons.get(i).name);
//            personViewHolder.personAge.setText(persons.get(i).age);
//            personViewHolder.factIcon.setImageResource(persons.get(i).photoId);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                personName = (TextView)itemView.findViewById(R.id.fact_content);
                personPhoto = (ImageView)itemView.findViewById(R.id.fact_icon);
            }
        }

    }

}
