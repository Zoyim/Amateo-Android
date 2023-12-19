package com.location.amateolocationengin.ui.catalogue;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.adapters.CatalogueAdapter;
import com.location.amateolocationengin.adapters.CatalogueAdapter.OnNoteListener;
import com.location.amateolocationengin.models.CatalogueModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class CatalogueFragment extends Fragment implements OnNoteListener {
    private static final String TAG = "CatalogueFragment";
    CatalogueAdapter catalogueAdapter;
    private CatalogueViewModel catalogueViewModel;
    List<CatalogueModel> listCatalogue;
    ProgressBar mProgressBar;
    RecyclerView recyclerViewCategories;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalogue, container, false);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        recyclerViewCategories = (RecyclerView) root.findViewById(R.id.recyclerViewCategories);
        listCatalogue = new ArrayList<>();
        catalogueAdapter = new CatalogueAdapter(getActivity(), listCatalogue, this);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(CatalogueFragment.this.getActivity()));
        recyclerViewCategories.addItemDecoration(new DividerItemDecoration(CatalogueFragment.this.getActivity(), 1));
        recyclerViewCategories.setAdapter(catalogueAdapter);
        listeCategories();
        return root;
    }

    private void listeCategories() {
        Ion.with(CatalogueFragment.this.getActivity())
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
                            Toast.makeText(CatalogueFragment.this.getActivity(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
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
