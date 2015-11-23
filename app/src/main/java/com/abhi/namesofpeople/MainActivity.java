package com.abhi.namesofpeople;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NamesOfPeople";

    @Bind(R.id.list_people) RecyclerView vPeopleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ParseJsonTask().execute();
    }

    private static class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.NameViewHolder> {

        private List<Data.Name> names;

        public PeopleAdapter(List<Data.Name> names) {
            this.names = names;
        }

        public class NameViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public NameViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        @Override
        public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new NameViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(NameViewHolder holder, int position) {
            Data.Name name = names.get(position);
            String nameString = name.firstName;

            if(!TextUtils.isEmpty(name.lastName)) {
                nameString = nameString + " " +  name.lastName;
            }

            holder.textView.setText(nameString);
        }

        @Override
        public int getItemCount() {
            return names.size();
        }
    }

    /**
     * A subclass of AsyncTask that reads a json file and parses out the json
     * data
     */
    private class ParseJsonTask extends AsyncTask<Void, Void, List<Data.Name>> {

        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG, "Calling onPreExecute");
        }

        @Override
        protected List<Data.Name> doInBackground(Void... params) {
            List<Data.Name> namesList = null;

            try {
                // read json file
                InputStream stream = getResources().openRawResource(R.raw.people);
                byte[] streamBuffer = new byte[stream.available()];
                stream.read(streamBuffer);
                stream.close();

                // construct models from json
                String jsonString = new String(streamBuffer);
                Data data = new GsonBuilder().create().fromJson(jsonString, Data.class);
                namesList = data.people;
            } catch (IOException ie) {
                Log.d(LOG_TAG, "Error reading json data!");
            }
            return namesList;
        }

        @Override
        protected void onPostExecute(List<Data.Name> names) {
            Log.d(LOG_TAG, "Calling onPostExecute");
            vPeopleRecyclerView.setAdapter(new PeopleAdapter(names));
        }
    }

}
