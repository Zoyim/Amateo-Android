package com.location.amateolocationengin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.models.TypeEngin;
import com.location.amateolocationengin.ui.engins.EnginActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTypeAdapter extends ArrayAdapter<TypeEngin> {
    Context context;
    private final Filter typeFilter = new Filter() {
        public FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            ArrayList<TypeEngin> arrayList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                arrayList.addAll(typeListFull);
            } else {
                String trim = charSequence.toString().toLowerCase().trim();
                for (TypeEngin typeEngin : typeListFull) {
                    if (typeEngin.getNom().toLowerCase().contains(trim)) {
                        arrayList.add(typeEngin);
                    }
                }
            }
            filterResults.values = arrayList;
            filterResults.count = arrayList.size();
            return filterResults;
        }

        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            if (filterResults.values instanceof List<?>) {
                List<?> resultValues = (List<?>) filterResults.values;

                for (Object obj : resultValues) {
                    if (obj instanceof TypeEngin) {
                        add((TypeEngin) obj);
                    }
                }

                notifyDataSetChanged();
            }
        }

        public CharSequence convertResultToString(Object obj) {
            return ((TypeEngin) obj).getNom();
        }
    };

    public List<TypeEngin> typeListFull;

    public AutoCompleteTypeAdapter(Context context2, List<TypeEngin> list) {
        super(context2, 0, list);
        this.context = context2;
        this.typeListFull = list;
    }

    public Filter getFilter() {
        return typeFilter;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.search_type, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.text_view_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_flag);
        final TypeEngin typeEngin = (TypeEngin) getItem(i);
        if (typeEngin != null) {
            textView.setText(typeEngin.getNom());
            Picasso picasso = Picasso.get();
            picasso.load("https://amateo.net/" + typeEngin.getImageType()).into(imageView);
        }
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, EnginActivity.class);
                intent.putExtra("id", typeEngin.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
