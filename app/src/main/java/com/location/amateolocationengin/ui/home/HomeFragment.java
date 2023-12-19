package com.location.amateolocationengin.ui.home;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.location.amateolocationengin.adapters.AutoCompleteTypeAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.location.amateolocationengin.adapters.HomeAdapter;
import com.location.amateolocationengin.ui.catalogue.CatalogueActivity;
import com.location.amateolocationengin.models.TypeEngin;
import com.location.amateolocationengin.R;
import androidx.lifecycle.Observer;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnNoteListener {
    private static final String TAG = "HomeFragment";

    AutoCompleteTypeAdapter adapter;
    Button btn_catalogue;
    AutoCompleteTextView editText;
    HomeAdapter homeAdapter;
    HomeViewModel homeViewModel;
    List<TypeEngin> listType;
    List<TypeEngin> listTypeCatlogue;
    ProgressBar mProgressBar;
    RecyclerView recyclerViewCategoriesType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_accueil);
        homeViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        recyclerViewCategoriesType = (RecyclerView) root.findViewById(R.id.recyclerViewCategoriesType);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        btn_catalogue = (Button) root.findViewById(R.id.btn_catalogue);
        listType = new ArrayList<>();
        listTypeCatlogue = new ArrayList<>();
        editText = (AutoCompleteTextView) root.findViewById(R.id.actv);
        adapter = new AutoCompleteTypeAdapter(getActivity(), listType);
        homeAdapter = new HomeAdapter(getActivity(), listTypeCatlogue, this);
        recyclerViewCategoriesType.setLayoutManager(gridLayoutManager);
        recyclerViewCategoriesType.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recyclerViewCategoriesType.setAdapter(homeAdapter);
        listeCategoriesType();
        btn_catalogue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.startActivity(new Intent(HomeFragment.this.getActivity(), CatalogueActivity.class));
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Appelez searchListe() avec le texte actuel
                searchListe(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.setAdapter(adapter);
        return root;
    }

    private void searchListe(String query) {
        Ion.with(getActivity())
                .load("https://amateo.net/api/typeEngines")
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception exc, JsonArray result) {
                try {
                    listType.clear(); // Effacez la liste existante avant de la remplir à nouveau

                    for(int i = 0; i<result.size(); i++){
                        JsonObject typeSearch = result.get(i).getAsJsonObject();
                        int id = typeSearch.get("id").getAsInt();
                        String nom = typeSearch.get("nom").getAsString();
                        String prixBas = typeSearch.get("prixBas").getAsString();
                        String imageType = typeSearch.get("imageType").getAsString();

                        // Ajoutez uniquement les éléments qui correspondent à la requête
                        if (nom.toLowerCase().contains(query.toLowerCase())) {
                            listType.add(new TypeEngin(id, imageType, nom, prixBas));
                        }
                    }
                } catch (Exception error) {
                    Toast.makeText(HomeFragment.this.getActivity(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
                HomeFragment.this.adapter.notifyDataSetChanged();
            }
        });
    }


    private void listeCategoriesType() {
        Ion.with(getActivity())
                .load("https://amateo.net/api/catologuetypes")
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception exc, JsonArray result) {

                        try {
                            for(int i = 0; i<result.size(); i++){
                                JsonObject typeResult = result.get(i).getAsJsonObject();
                                TypeEngin typeEngin = new TypeEngin();
                                typeEngin.setId(typeResult.get("id").getAsInt());
                                typeEngin.setNom(typeResult.get("nom").getAsString());
                                typeEngin.setImageType(typeResult.get("imageType").getAsString());
                                listTypeCatlogue.add(typeEngin);
                            }
                        }catch (Exception error){
                            Toast.makeText(HomeFragment.this.getActivity(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        homeAdapter.notifyDataSetChanged();
                        recyclerViewCategoriesType.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void onNoteListener(int position) {
        Log.d(TAG, "clicked on the position:" + position);
    }
}