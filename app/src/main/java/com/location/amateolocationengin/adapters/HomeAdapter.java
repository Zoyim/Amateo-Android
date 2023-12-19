package com.location.amateolocationengin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.models.TypeEngin;
import com.location.amateolocationengin.ui.engins.EnginActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MeuViewHolder> {
    Context ctx;
    List<TypeEngin> liste;
    OnNoteListener mOnNoteListener;

    public interface OnNoteListener {
        void onNoteListener(int i);
    }

    public class MeuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageType;
        OnNoteListener onNoteListener;

        public MeuViewHolder(View view, OnNoteListener onNoteListener2) {
            super(view);
            this.imageType = (ImageView) view.findViewById(R.id.ivPlace);
            this.onNoteListener = onNoteListener2;
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            this.onNoteListener.onNoteListener(getAbsoluteAdapterPosition());
        }
    }

    public HomeAdapter(Context context, List<TypeEngin> list, OnNoteListener onNoteListener) {
        this.ctx = context;
        this.liste = list;
        this.mOnNoteListener = onNoteListener;
    }

    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MeuViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_custom_layout, viewGroup, false), this.mOnNoteListener);
    }

    public void onBindViewHolder(MeuViewHolder meuViewHolder, int i) {
        final TypeEngin typeEngin = this.liste.get(i);
        Picasso picasso = Picasso.get();
        picasso.load("https://amateo.net/" + this.liste.get(i).getImageType()).into(meuViewHolder.imageType);
        meuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdapter.this.ctx, EnginActivity.class);
                intent.putExtra("id", typeEngin.getId());
                HomeAdapter.this.ctx.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.liste.size();
    }
}
