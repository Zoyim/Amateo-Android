package com.location.amateolocationengin.ui.catalogue;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.adapters.CatalogueAdapter;
import com.location.amateolocationengin.models.CatalogueModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity implements CatalogueAdapter.OnNoteListener {
    private static final String TAG = "CatalogueActivity";
    CatalogueAdapter catalogueAdapter;
    private CatalogueViewModel catalogueViewModel;
    List<CatalogueModel> listCatalogue;
    ProgressBar mProgressBar;
    RecyclerView recyclerViewCategories;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.progress_bar);
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);

        listCatalogue = new ArrayList<>();
        catalogueAdapter = new CatalogueAdapter(this, listCatalogue, this);

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategories.addItemDecoration(new DividerItemDecoration(this, 1));
        recyclerViewCategories.setAdapter(catalogueAdapter);

        listeCategories();
    }

    private void listeCategories() {
        Ion.with(this)
                .load("https://amateo.net/api/categories")
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception exc, JsonArray result) {
                        try {
                            for(int i = 0; i<result.size(); i++){
                                JsonObject catalogue = result.get(i).getAsJsonObject();
                                CatalogueModel catalogueModel = new CatalogueModel();
                                catalogueModel.setId(catalogue.get("id").getAsInt());
                                catalogueModel.setNom(catalogue.get("nom").getAsString());
                                catalogueModel.setRead(catalogue.get("read").getAsInt());
                                catalogueModel.setImageCat(catalogue.get("imageCat").getAsString());
                                listCatalogue.add(catalogueModel);
                            }
                            catalogueAdapter.notifyDataSetChanged();
                        }catch (Exception error){
                            Toast.makeText(CatalogueActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                        catalogueAdapter.notifyDataSetChanged();
                        recyclerViewCategories.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void onNoteListener(int position) {
        Log.d(TAG, "clicked on the position:" + position);
    }
}
