package com.location.amateolocationengin.ui.engins;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Build;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.adapters.EnginAdapter;
import com.location.amateolocationengin.models.EnginModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class EnginActivity extends AppCompatActivity {
    TextView EnginTitle;
    LinearLayout containerView;
    EnginAdapter enginAdapter;
    List<EnginModel> listEngins;
    ProgressBar mProgressBar;
    RecyclerView recyclerViewEngins;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int id = getIntent().getExtras().getInt("id");
        EnginTitle = (TextView) findViewById(R.id.EnginTitle);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerViewEngins = (RecyclerView) findViewById(R.id.recyclerViewEngins);
        containerView = (LinearLayout) findViewById(R.id.containerView);
        listEngins = new ArrayList<>();
        enginAdapter = new EnginAdapter(this, listEngins, new View.OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(EnginActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Voulez-vous appeler pour réserver?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent("android.intent.action.CALL");
                        intent.setData(Uri.parse("tel:679101218"));
                        String str = "android.permission.CALL_PHONE";
                        if (ActivityCompat.checkSelfPermission(EnginActivity.this, str) == 0) {
                            startActivity(intent);
                        } else if (EnginActivity.this.shouldShowRequestPermissionRationale(str)) {
                            explain();
                        } else {
                            askForPermission();
                        }
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle("Confirmation");
                create.show();
            }
        });
        recyclerViewEngins.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEngins.addItemDecoration(new DividerItemDecoration(this, 1));
        recyclerViewEngins.setAdapter(enginAdapter);
        getTypeEngin(id);
    }

    private void getTypeEngin(int id) {
        String URL = "https://amateo.net/api/typeengins/" + id;
        Ion.with(this)
                .load(URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception exc, JsonObject result) {
                        if (exc != null) {
                            // Une exception s'est produite lors de la requête
                            Log.e("EnginActivity", "Erreur lors de la requête API", exc);
                            Toast.makeText(EnginActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            String nomType = result.get("nom").getAsString();
                            EnginTitle.setText(nomType);
                            JsonArray enginArray = result.get("engine").getAsJsonArray();

                            for (int i = 0; i < enginArray.size(); i++) {
                                JsonObject enginObject = enginArray.get(i).getAsJsonObject();

                                int id = enginObject.get("id").getAsInt();
                                String nom = enginObject.get("nom").getAsString();
                                int prixlocation = enginObject.get("prixlocation").getAsInt();
                                int prix = enginObject.get("prix").getAsInt();
                                int statut = enginObject.get("statut").getAsInt();
                                String energie = enginObject.get("energie").getAsString();
                                String poidsVide = enginObject.get("poidsVide").getAsString();
                                String poidsTotal = enginObject.get("poidsTotal").getAsString();
                                String imageEngin = enginObject.get("imageEngin").getAsString();
                                String puissance = enginObject.get("puissance").getAsString();
                                String marque = enginObject.get("marque").getAsString();
                                int publication = enginObject.get("publication").getAsInt();
                                String title = enginObject.get("title").getAsString();
                                String nomVille = enginObject.get("nomVille").getAsString();
                                int vendu = enginObject.get("vendu").getAsInt();
                                if (publication == 1) {
                                    if (title.equals("Louer")) {
                                        listEngins.add(new EnginModel(id, nom, prixlocation, prix, statut, energie, poidsVide, poidsTotal, imageEngin, puissance, marque, publication, title, nomVille, vendu));
                                    } else {
                                        if (title.equals("Vendre") && vendu == 0) {
                                            listEngins.add(new EnginModel(id, nom, prixlocation, prix, statut, energie, poidsVide, poidsTotal, imageEngin, puissance, marque, publication, title, nomVille, vendu));
                                        }
                                    }
                                }
                            }
                            enginAdapter.notifyDataSetChanged();
                            recyclerViewEngins.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                        } catch (Exception error) {
                            Log.e("EnginActivity", "Erreur lors de la récupération des données", error);
                            Toast.makeText(EnginActivity.this, "Erreur de connexion: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void askForPermission() {
        requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 2);
    }

    public void explain() {
        Snackbar.make(containerView, "Cette permission est nécessaire pour appeler", Snackbar.LENGTH_LONG).setAction((CharSequence) "Activer", (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                EnginActivity.this.askForPermission();
            }
        }).show();
    }
}
