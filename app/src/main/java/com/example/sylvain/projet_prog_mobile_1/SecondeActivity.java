package com.example.sylvain.projet_prog_mobile_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondeActivity extends AppCompatActivity {

    public static final String BIERS_UPDATE ="com.octip.cours.inf4042_11.BIERS_UPDATE";
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);

        // Service de Téléchargement

        GetBiersServices.startActionGetBiersServices(this);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);

        //RecyclerView


        rv = (RecyclerView)findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new BiersAdapter(getBiersFromFile()));


        // Autres

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public class BierUpdate extends BroadcastReceiver{

        public static final String TAG = "SecondActivity";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getAction());
            ((BiersAdapter)rv.getAdapter()).setNewBiere(getBiersFromFile());
        }

    }

    // Permet de charger le JSON de manière à ce que le RecyclerView puisse l'utiliser

    public JSONArray getBiersFromFile(){

        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));

        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();

        }catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder>{

        private JSONArray biers;

        public BiersAdapter(JSONArray biers) {

           this.biers = biers;

        }

        public void setNewBiere(JSONArray b) {
            biers=b;
            notifyDataSetChanged();

        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inf = LayoutInflater.from(getBaseContext());
            View v = inf.inflate(R.layout.rv_biere_element,parent,false);

            return new BierHolder(v);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {

            try {

                holder.name.setText(biers.getJSONObject(position).getString("name"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {

            return biers.length();
        }

        public class BierHolder extends RecyclerView.ViewHolder{

            public TextView name;

            public BierHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.rv_biere_elem);
            }
        }
    }





}
