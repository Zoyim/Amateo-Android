package com.location.amateolocationengin.ui.typesEngines;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.adapters.TypeEnginAdapter;
import com.location.amateolocationengin.adapters.TypeEnginAdapter.OnNoteListener;
import com.location.amateolocationengin.models.TypeEngin;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class TypesEnginesActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "TypesEnginesActivity";
    List<TypeEngin> listType;
    ProgressBar mProgressBar;
    RecyclerView recyclerViewTypesEngins;
    TypeEnginAdapter typeEnginAdapter;
    TextView typeTitle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_engines);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int id = getIntent().getExtras().getInt("id");
        this.typeTitle = (TextView) findViewById(R.id.typeTitle);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.recyclerViewTypesEngins = (RecyclerView) findViewById(R.id.recyclerViewTypesEngins);
        listType = new ArrayList<>();
        typeEnginAdapter = new TypeEnginAdapter(this, listType, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTypesEngins.setLayoutManager(layoutManager);
        recyclerViewTypesEngins.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewTypesEngins.setAdapter(typeEnginAdapter);
        getCategerie(id);
    }

    private void getCategerie(int id) {
        String URL = "https://amateo.net/api/categories/" + id;
        Ion.with(this)
                .load(URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            String nomCat = result.get("nom").getAsString();
                            typeTitle.setText(nomCat);

                            JsonArray typeArray = result.get("typeengin").getAsJsonArray();

                            for (int i = 0; i < typeArray.size(); i++) {
                                JsonObject typeObject = typeArray.get(i).getAsJsonObject();
                                int id = typeObject.get("id").getAsInt();
                                String nomType = typeObject.get("nom").getAsString();
                                String prixBas = typeObject.get("prixBas").getAsString();
                                String imageType = typeObject.get("imageType").getAsString();
                                listType.add(new TypeEngin(id, imageType, nomType, prixBas));
                            }
                            typeEnginAdapter.notifyDataSetChanged();
                            recyclerViewTypesEngins.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                        } catch (Exception error) {
                            Toast.makeText(TypesEnginesActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }

    @Override
    public void onNoteListener(int position) {
        Log.d(TAG, "clicked on the position:" + position);
    }
}
