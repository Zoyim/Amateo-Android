package com.location.amateolocationengin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.models.CatalogueModel;
import com.location.amateolocationengin.ui.typesEngines.TypesEnginesActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.MeuViewHolder> {
    Context ctx;
    List<CatalogueModel> liste;
    OnNoteListener mOnNoteListener;

    public class MeuViewHolder extends ViewHolder implements OnClickListener {
        ImageView imageCat;
        OnNoteListener onNoteListener;
        TextView txtNom;

        public MeuViewHolder(View view, OnNoteListener onNoteListener2) {
            super(view);
            txtNom = (TextView) view.findViewById(R.id.txtNom);
            imageCat = (ImageView) view.findViewById(R.id.imageCat);
            onNoteListener = onNoteListener2;
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            onNoteListener.onNoteListener(getBindingAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteListener(int i);
    }

    public CatalogueAdapter(Context context, List<CatalogueModel> list, OnNoteListener onNoteListener) {
        this.ctx = context;
        this.liste = list;
        this.mOnNoteListener = onNoteListener;
    }

    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MeuViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_catalogue, viewGroup, false), mOnNoteListener);
    }

    public void onBindViewHolder(MeuViewHolder meuViewHolder, int i) {
        final CatalogueModel catalogueModel = (CatalogueModel) liste.get(i);
        if (liste.get(i).getRead() == 0) {
            Picasso picasso = Picasso.get();
            picasso.load("https://amateo.net/" + liste.get(i).getImageCat()).into(meuViewHolder.imageCat);
            meuViewHolder.txtNom.setText(liste.get(i).getNom());
            meuViewHolder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, TypesEnginesActivity.class);
                    intent.putExtra("id", catalogueModel.getId());
                    ctx.startActivity(intent);
                }
            });
            return;
        }
        Picasso picasso2 = Picasso.get();
        picasso2.load("https://amateo.net/amateo/assets/images/amateo/imagesCat/" + liste.get(i).getImageCat()).into(meuViewHolder.imageCat);
        meuViewHolder.txtNom.setText(liste.get(i).getNom());
        meuViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ctx, TypesEnginesActivity.class);
                intent.putExtra("id", catalogueModel.getId());
                ctx.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return liste.size();
    }
}
