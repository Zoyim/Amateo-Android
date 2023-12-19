package com.location.amateolocationengin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.models.EnginModel;
import com.location.amateolocationengin.ui.infos.InfosActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ListeEnginsAdapter extends RecyclerView.Adapter<ListeEnginsAdapter.MeuViewHolder> {
    Context ctx;
    List<EnginModel> liste;

    public class MeuViewHolder extends ViewHolder {
        Button btn_louer;
        PhotoView imageEngin;
        TextView lieu;
        TextView txtNomEngin;
        TextView txtPrix;
        TextView txtPuissance;
        TextView txtTitle;
        TextView txtenergie;
        TextView txtpoidsTotal;
        TextView txtpoidsVide;
        TextView txtstatut;
        TextView txtstatutpublication;

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
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtstatutpublication = (TextView) view.findViewById(R.id.txtstatutpublication);
            this.imageEngin = (PhotoView) view.findViewById(R.id.imageEngin);
            this.btn_louer = (Button) view.findViewById(R.id.btn_louer);
            // Ajoutez un écouteur de clic à votre PhotoView
            this.imageEngin.setOnClickListener(new View.OnClickListener() {
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

    public ListeEnginsAdapter(Context context, List<EnginModel> list) {
        this.ctx = context;
        this.liste = list;
    }

    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MeuViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_liste, viewGroup, false));
    }

    public void onBindViewHolder(MeuViewHolder meuViewHolder, int i) {
        final EnginModel enginModel = liste.get(i);
        if (liste.get(i).getTitle().equals("Louer")) {
            Picasso picasso = Picasso.get();
            picasso.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
            meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
            meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrix()));
            meuViewHolder.lieu.setText(liste.get(i).getNomVille());
            meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
            meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
            meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
            meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
            meuViewHolder.txtTitle.setText("À Louer");
            meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
            meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button);
            meuViewHolder.btn_louer.setText("Louer au client");
            meuViewHolder.btn_louer.setBackgroundResource(R.drawable.rounded_button);
            if (liste.get(i).getStatut() == 1) {
                meuViewHolder.txtstatut.setText("Disponible");
                meuViewHolder.txtstatut.setTextColor(Color.parseColor("#008000"));
                meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx, InfosActivity.class);
                        intent.putExtra("id", enginModel.getId());
                        ctx.startActivity(intent);
                    }
                });
            } else if (liste.get(i).getStatut() == 0) {
                meuViewHolder.txtstatut.setText("En location...");
                meuViewHolder.txtstatut.setTextColor(Color.parseColor("#FF0000"));
                meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx, InfosActivity.class);
                        intent.putExtra("id", enginModel.getId());
                        ctx.startActivity(intent);
                    }
                });
            }
            if (liste.get(i).getPublication() == 1) {
                meuViewHolder.txtstatutpublication.setText("Publié");
                meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#008000"));
                meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx, InfosActivity.class);
                        intent.putExtra("id", enginModel.getId());
                        ctx.startActivity(intent);
                    }
                });
            } else if (liste.get(i).getPublication() == 0) {
                meuViewHolder.txtstatutpublication.setText("Pas Publié");
                meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#FF0000"));
                meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx, InfosActivity.class);
                        intent.putExtra("id", enginModel.getId());
                        ctx.startActivity(intent);
                    }
                });
            }
        } else if (!liste.get(i).getTitle().equals("Vendre")) {
        } else {
            if (liste.get(i).getVendu() == 0) {
                Picasso picasso2 = Picasso.get();
                picasso2.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
                meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
                meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrix()));
                meuViewHolder.lieu.setText(liste.get(i).getNomVille());
                meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
                meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
                meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
                meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
                meuViewHolder.txtTitle.setText("À Vendre");
                meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button_achat);
                meuViewHolder.btn_louer.setText("Vendre au client");
                meuViewHolder.btn_louer.setBackgroundResource(R.drawable.rounded_button_achat);
                if (liste.get(i).getStatut() == 1) {
                    meuViewHolder.txtstatut.setText("Disponible");
                    meuViewHolder.txtstatut.setTextColor(Color.parseColor("#008000"));
                    meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(ctx, InfosActivity.class);
                            intent.putExtra("id", enginModel.getId());
                            ctx.startActivity(intent);
                        }
                    });
                }
                if (liste.get(i).getPublication() == 1) {
                    meuViewHolder.txtstatutpublication.setText("Publié");
                    meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#008000"));
                    meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(ctx, InfosActivity.class);
                            intent.putExtra("id", enginModel.getId());
                            ctx.startActivity(intent);
                        }
                    });
                } else if (liste.get(i).getPublication() == 0) {
                    meuViewHolder.txtstatutpublication.setText("Pas Publié");
                    meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#FF0000"));
                    meuViewHolder.btn_louer.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(ctx, InfosActivity.class);
                            intent.putExtra("id", enginModel.getId());
                            ctx.startActivity(intent);
                        }
                    });
                }
            } else if (liste.get(i).getVendu() == 1) {
                Picasso picasso3 = Picasso.get();
                picasso3.load("https://amateo.net/amateo/assets/images/amateo/imagesEngin/" + liste.get(i).getImageEngin()).into(meuViewHolder.imageEngin);
                meuViewHolder.txtNomEngin.setText(liste.get(i).getNom());
                meuViewHolder.txtPrix.setText(String.valueOf(liste.get(i).getPrix()));
                meuViewHolder.lieu.setText(liste.get(i).getNomVille());
                meuViewHolder.txtenergie.setText(liste.get(i).getEnergie());
                meuViewHolder.txtpoidsVide.setText(liste.get(i).getPoidsVide());
                meuViewHolder.txtpoidsTotal.setText(liste.get(i).getPoidsTotal());
                meuViewHolder.txtPuissance.setText(liste.get(i).getPuissance());
                meuViewHolder.txtTitle.setText("À Vendre");
                meuViewHolder.txtTitle.setTextColor(Color.parseColor("#FFFFFF"));
                meuViewHolder.txtTitle.setBackgroundResource(R.drawable.rounded_button_achat);
                meuViewHolder.btn_louer.setText("Vendre au client");
                meuViewHolder.btn_louer.setBackgroundResource(R.drawable.rounded_button_achat);
                meuViewHolder.txtstatut.setText("Vendu");
                meuViewHolder.txtstatut.setTextColor(Color.parseColor("#008000"));
                if (liste.get(i).getStatut() == 0) {
                    meuViewHolder.btn_louer.setVisibility(View.INVISIBLE);
                }
                if (liste.get(i).getPublication() == 1) {
                    meuViewHolder.txtstatutpublication.setText("Publié");
                    meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#008000"));
                } else if (liste.get(i).getPublication() == 0) {
                    meuViewHolder.txtstatutpublication.setText("Pas Publié");
                    meuViewHolder.txtstatutpublication.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }
    }

    public int getItemCount() {
        return liste.size();
    }
}
