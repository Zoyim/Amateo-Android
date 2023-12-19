package com.location.amateolocationengin.ui.infos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.ui.dashboard.DashboardActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpPut;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

public class InfosActivity extends AppCompatActivity {
    Button btn_valide;
    EditText edit_energie;
    EditText edit_id;
    EditText edit_immatricualtion;
    EditText edit_marque;
    EditText edit_nom;
    ImageView imageEngin;
    LinearLayout linearId;
    LinearLayout linearImage;
    LinearLayout linearInfos1;
    LinearLayout linearInfos2;
    ProgressBar mProgressBar;
    RadioGroup radioGroup;
    RadioButton radio_disponible;
    RadioButton radio_location;
    TextView txtTitle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linearId = (LinearLayout) findViewById(R.id.linearId);
        linearInfos2 = (LinearLayout) findViewById(R.id.linearInfos2);
        linearInfos1 = (LinearLayout) findViewById(R.id.linearInfos1);
        linearImage = (LinearLayout) findViewById(R.id.linearImage);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imageEngin = (ImageView) findViewById(R.id.imageEngin);
        edit_nom = (EditText) findViewById(R.id.edit_nom);
        edit_energie = (EditText) findViewById(R.id.edit_energie);
        edit_marque = (EditText) findViewById(R.id.edit_marque);
        edit_immatricualtion = (EditText) findViewById(R.id.edit_immatriculation);
        edit_id = (EditText) findViewById(R.id.id_Engin);
        radio_disponible = (RadioButton) findViewById(R.id.radio_disponible);
        radio_location = (RadioButton) findViewById(R.id.radio_location);
        btn_valide = (Button) findViewById(R.id.btn_valide);
        getEngin(getIntent().getExtras().getInt("id"));
    }

    public void goSave(View view) {
        radioGroup = findViewById(R.id.radioGroupe);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        String id = edit_id.getText().toString();
        String title = txtTitle.getText().toString();
        if (title.equals("À Louer")) {
            switch (checkedRadioButtonId) {
                case R.id.radio_disponible:
                    actualizarDisponibilite(id);
                    return;
                case R.id.radio_location:
                    Toast toast = Toast.makeText(InfosActivity.this, "Veuillez prendre des précautions vous même pour l'indisponibilté de votre machine.", Toast.LENGTH_SHORT);
                    toast.show();
                    // Utiliser un Handler pour retarder la suppression du Toast
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Annuler le Toast après 6 secondes
                            toast.cancel();
                        }
                    }, 10000);
                    actualizarLocation(id);
                    return;
                default:
                    return;
            }
        } else if (title.equals("À Vendre")) {
            switch (checkedRadioButtonId) {
                case R.id.radio_disponible:
                    actualizarDisponibiliteAchat(id);
                    return;
                case R.id.radio_location:
                    Toast.makeText(this, "Veuillez prendre des précautions vous même pour la vente de votre machine.", Toast.LENGTH_SHORT).show();
                    // Utiliser un Handler pour retarder la suppression du Toast
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Annuler le Toast après 6 secondes
                            Toast.makeText(InfosActivity.this, "", Toast.LENGTH_SHORT).cancel();
                        }
                    }, 10000);
                    actualizarAchat(id);
                    return;
                default:
                    return;
            }
        }
    }

    public void getEngin(int id) {
        String URL = "https://amateo.net/api/engindetails/" + id;
        Ion.with(this)
                .load(URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            int id = result.get("id").getAsInt();
                            String nomEngin = result.get("nom").getAsString();
                            String marque = result.get("marque").getAsString();
                            String energie = result.get("energie").getAsString();
                            String immatriculation = result.get("immatriculation").getAsString();
                            int statut = result.get("statut").getAsInt();
                            int rmb = result.get("rmb").getAsInt();
                            String title = result.get("title").getAsString();
                            String imageEngin = result.get("imageEngin").getAsString();
                            if (title.equals("Louer")) {
                                Picasso picasso = Picasso.get();
                                picasso.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + imageEngin).into(InfosActivity.this.imageEngin);
                                edit_nom.setText(nomEngin);
                                edit_marque.setText(marque);
                                edit_energie.setText(energie);
                                edit_immatricualtion.setText(immatriculation);
                                edit_id.setText(String.valueOf(id));
                                txtTitle.setText("À Louer");
                                txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                                txtTitle.setBackgroundResource(R.drawable.rounded_button);
                                linearId.setVisibility(View.VISIBLE);
                                linearInfos2.setVisibility(View.VISIBLE);
                                linearInfos1.setVisibility(View.VISIBLE);
                                linearImage.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                if (statut == 1) {
                                    radio_disponible.setChecked(true);
                                    radio_location.setChecked(false);
                                    btn_valide.setText("Louer au client");
                                    btn_valide.setBackgroundResource(R.drawable.rounded_button);
                                } else if (statut == 0) {
                                    radio_disponible.setChecked(false);
                                    radio_location.setChecked(true);
                                    if (rmb == 0) {
                                        Toast.makeText(InfosActivity.this, "Cet engin est en cour de location, veuillez contacter la direction pour plus d'information.", Toast.LENGTH_SHORT).show();
                                        btn_valide.setClickable(false);
                                    } else if (rmb == 1) {
                                        Toast.makeText(InfosActivity.this, "Bien vouloir vérifier; l'état de l'engin après un retour.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else if (title.equals("Vendre")) {
                                Picasso picasso2 = Picasso.get();
                                picasso2.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + imageEngin).into(InfosActivity.this.imageEngin);
                                edit_nom.setText(nomEngin);
                                edit_marque.setText(marque);
                                edit_energie.setText(energie);
                                edit_immatricualtion.setText(immatriculation);
                                edit_id.setText(String.valueOf(id));
                                txtTitle.setText("À Vendre");
                                txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                                txtTitle.setBackgroundResource(R.drawable.rounded_button_achat);
                                linearId.setVisibility(View.VISIBLE);
                                linearInfos2.setVisibility(View.VISIBLE);
                                linearInfos1.setVisibility(View.VISIBLE);
                                linearImage.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                if (statut == 1) {
                                    radio_disponible.setChecked(true);
                                    radio_location.setChecked(false);
                                    radio_location.setText("À Vendre");
                                    btn_valide.setText("Vendre au client");
                                    btn_valide.setBackgroundResource(R.drawable.rounded_button_achat);
                                } else if (statut == 0) {
                                    radio_disponible.setChecked(false);
                                    radio_location.setChecked(true);
                                    radio_location.setText("À Vendre");
                                    if (rmb == 0) {
                                        Toast.makeText(InfosActivity.this, "Cet engin est en cour de location, veuillez contacter la direction pour plus d'information.", Toast.LENGTH_SHORT).show();
                                        // Utiliser un Handler pour retarder la suppression du Toast
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Annuler le Toast après 6 secondes
                                                Toast.makeText(InfosActivity.this, "Cet engin est en cour de location, veuillez contacter la direction pour plus d'information.", Toast.LENGTH_SHORT).cancel();
                                            }
                                        }, 10000);
                                        btn_valide.setClickable(false);
                                    } else if (rmb == 1) {
                                        Toast.makeText(InfosActivity.this, "Bien vouloir vérifier; l'état de l'engin après un retour.", Toast.LENGTH_SHORT).show();
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Annuler le Toast après 6 secondes
                                                Toast.makeText(InfosActivity.this, "Bien vouloir vérifier; l'état de l'engin après un retour.", Toast.LENGTH_SHORT).cancel();
                                            }
                                        }, 10000);
                                    }
                                }
                            }
                        } catch (Exception error) {
                            Toast.makeText(InfosActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }

    public void actualizarDisponibilite(String id) {
        Ion.with(this)
                .load(AsyncHttpPut.METHOD, "https://amateo.net/api/enginsdis/" + id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception exc, JsonObject result) {
                        try {
                            if (result.get("update").getAsString().equals("ok")) {
                                startActivity(new Intent(InfosActivity.this, DashboardActivity.class));
                                finish();
                            }
                            Toast.makeText(InfosActivity.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                        } catch (Exception unused) {
                            Toast.makeText(InfosActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void actualizarLocation(String id) {
        Ion.with(this)
                .load(AsyncHttpPut.METHOD, "https://amateo.net/api/enginsloc/" + id)
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception exc, JsonObject result) {
                        try {
                            if (result.get("update").getAsString().equals("ok")) {
                                InfosActivity.this.startActivity(new Intent(InfosActivity.this, DashboardActivity.class));
                                InfosActivity.this.finish();
                                return;
                            }
                            Toast.makeText(InfosActivity.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                        } catch (Exception unused) {
                            Toast.makeText(InfosActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void actualizarDisponibiliteAchat(String id) {
        Ion.with(this)
                .load(AsyncHttpPut.METHOD, "https://amateo.net/api/enginsDisponibleAchat/" + id)
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception exc, JsonObject jsonObject) {
                        try {
                            if (jsonObject.get("update").getAsString().equals("ok")) {
                                startActivity(new Intent(InfosActivity.this, DashboardActivity.class));
                                finish();
                            }
                            Toast.makeText(InfosActivity.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                        } catch (Exception unused) {
                            Toast.makeText(InfosActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void actualizarAchat(String id) {
        Ion.with(this)
                .load(AsyncHttpPut.METHOD, "https://amateo.net/api/enginsAchat/" + id)
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception exc, JsonObject result) {
                        try {
                            if (result.get("update").getAsString().equals("ok")) {
                                startActivity(new Intent(InfosActivity.this, DashboardActivity.class));
                                finish();
                            }
                            Toast.makeText(InfosActivity.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                        } catch (Exception unused) {
                            Toast.makeText(InfosActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
