package com.location.amateolocationengin.ui.dashboard;

import android.content.Intent;
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
import com.location.amateolocationengin.TokenManager;
import com.location.amateolocationengin.adapters.ListeEnginsAdapter;
import com.location.amateolocationengin.entities.AccessToken;
import com.location.amateolocationengin.models.EnginModel;
import com.location.amateolocationengin.models.PartenaireModel;
import com.location.amateolocationengin.network.ApiService;
import com.location.amateolocationengin.network.RetrofitBuilder;
import com.location.amateolocationengin.ui.espace.EspaceConnexionActivity;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    ListeEnginsAdapter adapter;
    Call<List<EnginModel>> call;
    Call<AccessToken> callLog;
    Call<PartenaireModel> calls;
    List<EnginModel> listEngins;
    ProgressBar mProgressBar;
    ProgressBar mProgressBarDeconnexion;
    @BindView(R.id.recyclerViewListe)
    RecyclerView recyclerViewListe;
    ApiService service;
    TokenManager tokenManager;
    TextView txtName;
    TextView txtPrenom;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listEngins = new ArrayList<>();
        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = (ApiService) RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        if (tokenManager.getToken() == null) {
            startActivity(new Intent(this, EspaceConnexionActivity.class));
            finish();
        }
        adapter = new ListeEnginsAdapter(this, listEngins);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBarDeconnexion = (ProgressBar) findViewById(R.id.progressDeconnexion);
        recyclerViewListe.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListe.addItemDecoration(new DividerItemDecoration(this, 1));
        recyclerViewListe.setAdapter(this.adapter);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPrenom = (TextView) findViewById(R.id.txtPrenom);
        getEngin();
        getPartenaire();
    }

    private void getEngin() {
        call = service.engins();
        call.enqueue(new Callback<List<EnginModel>>() {
            public void onResponse(Call<List<EnginModel>> call, Response<List<EnginModel>> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    try {
                        for(int i = 0; i< response.body().size(); i++){
                            int id = response.body().get(i).getId();
                            String nom = response.body().get(i).getNom();
                            int prixLocation = response.body().get(i).getPrixLocation();
                            int prix = response.body().get(i).getPrix();
                            int statut = response.body().get(i).getStatut();
                            String energie = response.body().get(i).getEnergie();
                            String poidsVide = response.body().get(i).getPoidsVide();
                            String poidsTotal = response.body().get(i).getPoidsTotal();
                            String imageEngin = response.body().get(i).getImageEngin();
                            String puissance = response.body().get(i).getPuissance();
                            String marque = response.body().get(i).getMarque();
                            int publication = response.body().get(i).getPublication();
                            String title = response.body().get(i).getTitle();
                            String nomVille = response.body().get(i).getNomVille();
                            int vendu = response.body().get(i).getVendu();
                            if (title.equals("Louer")) {
                                listEngins.add(new EnginModel(id, nom, prixLocation, prix, statut, energie, poidsVide, poidsTotal, imageEngin, puissance, marque, publication, title, nomVille, vendu));
                            } else if (title.equals("Vendre")) {
                                listEngins.add(new EnginModel(id, nom, prixLocation, prix, statut, energie, poidsVide, poidsTotal, imageEngin, puissance, marque, publication, title, nomVille, vendu));
                            }
                        }
                    }catch (Exception error){
                        Toast.makeText(DashboardActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    recyclerViewListe.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                tokenManager.deleteToken();
                startActivity(new Intent(DashboardActivity.this, EspaceConnexionActivity.class));
                finish();
            }
            public void onFailure(Call<List<EnginModel>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getPartenaire() {
        Call<PartenaireModel> partenaires = service.partenaires();
        this.calls = partenaires;
        partenaires.enqueue(new Callback<PartenaireModel>() {
            public void onResponse(Call<PartenaireModel> call, Response<PartenaireModel> response) {
                Log.w(DashboardActivity.TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    try {
                        response.body().getId();
                        String name = response.body().getName();
                        String prenom = response.body().getPrenom();
                        txtName.setText(name);
                        txtPrenom.setText(prenom);
                    } catch (Exception unused) {
                        Toast.makeText(DashboardActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public void onFailure(Call<PartenaireModel> call, Throwable th) {
                Log.w(TAG, "onFailure: " + th.getMessage());
            }
        });
    }


    public void logout(View view) {
        recyclerViewListe.setVisibility(View.GONE);
        mProgressBarDeconnexion.setVisibility(View.VISIBLE);
        Call<AccessToken> logout = service.logout(this.tokenManager.getToken());
        this.callLog = logout;
        logout.enqueue(new Callback<AccessToken>() {
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.w(DashboardActivity.TAG, "onResponse: " + response);
                DashboardActivity.this.tokenManager.deleteToken();
                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this, EspaceConnexionActivity.class));
                DashboardActivity.this.finish();
            }

            public void onFailure(Call<AccessToken> call, Throwable th) {
                Log.w(DashboardActivity.TAG, "onFailure: " + th.getMessage());
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        Call<List<EnginModel>> call2 = this.call;
        if (call2 != null) {
            call2.cancel();
            this.call = null;
        }
        Call<AccessToken> call3 = this.callLog;
        if (call3 != null) {
            call3.cancel();
            this.callLog = null;
        }
    }
}
