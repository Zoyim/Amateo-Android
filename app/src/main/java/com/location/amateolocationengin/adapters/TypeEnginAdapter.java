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
import com.location.amateolocationengin.models.TypeEngin;
import com.location.amateolocationengin.ui.engins.EnginActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TypeEnginAdapter extends RecyclerView.Adapter<TypeEnginAdapter.MeuViewHolder> {
    Context ctx;
    List<TypeEngin> liste;
    OnNoteListener mOnNoteListener;

    public class MeuViewHolder extends ViewHolder implements OnClickListener {
        ImageView imageType;
        OnNoteListener onNoteListener;
        TextView txtNomType;
        TextView txtPrixBas;
        TextView txtidType;

        public MeuViewHolder(View view, OnNoteListener onNoteListener2) {
            super(view);
            this.txtNomType = (TextView) view.findViewById(R.id.txtNomType);
            this.txtPrixBas = (TextView) view.findViewById(R.id.txtPrixBas);
            this.txtidType = (TextView) view.findViewById(R.id.txtidType);
            this.imageType = (ImageView) view.findViewById(R.id.imageType);
            this.onNoteListener = onNoteListener2;
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            this.onNoteListener.onNoteListener(getAbsoluteAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteListener(int i);
    }

    public TypeEnginAdapter(Context context, List<TypeEngin> list, OnNoteListener onNoteListener) {
        this.ctx = context;
        this.liste = list;
        this.mOnNoteListener = onNoteListener;
    }

    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MeuViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_typesengins, viewGroup, false), this.mOnNoteListener);
    }

    public void onBindViewHolder(MeuViewHolder meuViewHolder, int i) {
        final TypeEngin typeEngin = liste.get(i);
        if (liste.get(i).getRead() == 0) {
            meuViewHolder.txtNomType.setText(liste.get(i).getNom());
            meuViewHolder.txtPrixBas.setText(liste.get(i).getPrixBas());
            Picasso picasso = Picasso.get();
            picasso.load("https://amateo.net/" + liste.get(i).getImageType()).into(meuViewHolder.imageType);
            meuViewHolder.txtidType.setText(String.valueOf(liste.get(i).getId()));
            meuViewHolder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, EnginActivity.class);
                    intent.putExtra("id", typeEngin.getId());
                    ctx.startActivity(intent);
                }
            });
            return;
        }
        meuViewHolder.txtNomType.setText(liste.get(i).getNom());
        meuViewHolder.txtPrixBas.setText(liste.get(i).getPrixBas());
        Picasso picasso2 = Picasso.get();
        picasso2.load("https://amateo.net/amateo/assets/images/amateo/imagesType/" + liste.get(i).getImageType()).into(meuViewHolder.imageType);
        meuViewHolder.txtidType.setText(String.valueOf(liste.get(i).getId()));
        meuViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ctx, EnginActivity.class);
                intent.putExtra("id", typeEngin.getId());
                ctx.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return liste.size();
    }
}
