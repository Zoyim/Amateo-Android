package com.location.amateolocationengin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.models.EnginModel;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class EnginAdapter extends RecyclerView.Adapter<EnginAdapter.MeuViewHolder> {
    private final OnClickListener btnListener;
    Context ctx;
    List<EnginModel> liste;

    public class MeuViewHolder extends ViewHolder {
        Button btn_Engin;
        PhotoView imageEngin;
        TextView lieu;
        LinearLayout linearEngin;
        TextView txtLibelle;
        TextView txtLibelleJour;
        TextView txtNomEngin;
        TextView txtPrix;
        TextView txtPuissance;
        TextView txtTitle;
        TextView txtenergie;
        TextView txtidEngin;
        TextView txtpoidsTotal;
        TextView txtpoidsVide;
        TextView txtstatut;

        public MeuViewHolder(View view) {
            super(view);
            this.txtNomEngin = (TextView) view.findViewById(R.id.txtNomEngin);
            this.txtPrix = (TextView) view.findViewById(R.id.txtPrix);
            this.txtstatut = (TextView) view.findViewById(R.id.txtstatut);
            this.lieu = (TextView) view.findViewById(R.id.lieu);
            this.txtenergie = (TextView) view.findViewById(R.id.txtenergie);
            this.txtpoidsVide = (TextView) view.findViewById(R.id.txtpoidsVide);
            this.txtpoidsTotal = (TextView) view.findViewById(R.id.txtpoidsTotal);
            this.txtPuissance = (TextView) view.findViewById(R.id.txtPuissance);
            this.txtidEngin = (TextView) view.findViewById(R.id.txtidEngin);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtLibelle = (TextView) view.findViewById(R.id.txtLibelle);
            this.txtLibelleJour = (TextView) view.findViewById(R.id.txtLibelleJour);
            this.imageEngin = (PhotoView) view.findViewById(R.id.imageEngin);
            this.linearEngin = (LinearLayout) view.findViewById(R.id.linearEngin);
            this.btn_Engin = (Button) view.findViewById(R.id.btn_Engin);
            // Ajoutez un écouteur de clic à votre PhotoView
            this.imageEngin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Créer une boîte de dialogue
                    Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.dialog_image); // Créez un fichier de mise en page XML pour votre boîte de dialogue

                    // Trouver PhotoView dans le dialogue
                    PhotoView photoViewDialog = dialog.findViewById(R.id.imageEnginDialog);

                    // Définir l'image dans PhotoView du dialogue
                    photoViewDialog.setImageDrawable(imageEngin.getDrawable());

                    // Montrer la boîte de dialogue
                    dialog.show();
                }
            });
        }
    }

    public EnginAdapter(Context context, List<EnginModel> list, OnClickListener onClickListener) {
        this.ctx = context;
        this.liste = list;
        this.btnListener = onClickListener;
    }

    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MeuViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_engins, viewGroup, false));
    }

    public void onBindViewHolder(MeuViewHolder meuViewHolder, int i) {
        EnginModel enginModel = liste.get(i);
        if (liste.get(i).getPublication() == 1 && liste.get(i).getStatut() == 1) {
            if (liste.get(i).getTitle().equals("Louer")) {
                Picasso picasso = Picasso.get();
                picasso.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
                meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
                meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrixLocation()));
                meuViewHolder.lieu.setText(liste.get(i).getNomVille());
                meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
                meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
                meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
                meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
                meuViewHolder.txtidEngin.setText(String.valueOf(liste.get(i).getId()));
                meuViewHolder.txtTitle.setText("À Louer");
                meuViewHolder.txtLibelle.setText("Prix Location");
                meuViewHolder.txtLibelleJour.setText("XAF/Jour");
                meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button);
                meuViewHolder.btn_Engin.setText("Appeler pour reserver");
                meuViewHolder.btn_Engin.setBackgroundResource(R.drawable.rounded_button);
                meuViewHolder.txtstatut.setText("Disponible");
                meuViewHolder.txtstatut.setTextColor(Color.parseColor("#008000"));
                meuViewHolder.btn_Engin.setOnClickListener(this.btnListener);
            } else if (liste.get(i).getTitle().equals("Vendre") && liste.get(i).getVendu() == 0) {
                Picasso picasso2 = Picasso.get();
                picasso2.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
                meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
                meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrixLocation()));
                meuViewHolder.lieu.setText(liste.get(i).getNomVille());
                meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
                meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
                meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
                meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
                meuViewHolder.txtidEngin.setText(String.valueOf(liste.get(i).getId()));
                meuViewHolder.txtTitle.setText("À Vendre");
                meuViewHolder.txtLibelle.setText("Prix De Vente");
                meuViewHolder.txtLibelleJour.setText("XAF");
                meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button_achat);
                meuViewHolder.btn_Engin.setText("Appeler pour acheter");
                meuViewHolder.btn_Engin.setBackgroundResource(R.drawable.rounded_button_achat);
                meuViewHolder.txtstatut.setText("Disponible");
                meuViewHolder.txtstatut.setTextColor(Color.parseColor("#008000"));
                meuViewHolder.btn_Engin.setOnClickListener(this.btnListener);
            }
        } else if (liste.get(i).getPublication() == 1 && liste.get(i).getStatut() == 0 && liste.get(i).getTitle().equals("Louer")) {
            Picasso picasso3 = Picasso.get();
            picasso3.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
            meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
            meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrixLocation()));
            meuViewHolder.lieu.setText(liste.get(i).getNomVille());
            meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
            meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
            meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
            meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
            meuViewHolder.txtidEngin.setText(String.valueOf(liste.get(i).getId()));
            meuViewHolder.txtTitle.setText("À Louer");
            meuViewHolder.txtLibelle.setText("Prix Location");
            meuViewHolder.txtLibelleJour.setText("XAF/Jour");
            meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
            meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button);
            meuViewHolder.btn_Engin.setText("Appeler pour reserver");
            meuViewHolder.btn_Engin.setBackgroundResource(R.drawable.rounded_button);
            meuViewHolder.txtstatut.setText("En location...");
            meuViewHolder.txtstatut.setTextColor(Color.parseColor("#FF0000"));
            meuViewHolder.btn_Engin.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Désolé cet engin est déjà occupé.\n Veuillez nous contacter au 677 22 08 14!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showImageDialog(Context context, Drawable drawable) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Utilisez un ImageView pour afficher l'image agrandie
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);

        // Ajoutez l'ImageView à la boîte de dialogue
        builder.setView(imageView);

        // Ajoutez un bouton "Fermer" à la boîte de dialogue
        builder.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fermez la boîte de dialogue lorsque le bouton est cliqué
            }
        });

        // Affichez la boîte de dialogue
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public int getItemCount() {
        return liste.size();
    }
}
