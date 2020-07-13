package com.szadst.szoemhost_and.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szadst.szoemhost_and.Domaine.Eleves;
import com.szadst.szoemhost_and.Editer_eleve_Activity;
import com.szadst.szoemhost_and.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Eleves_recycler_view_adapter extends RecyclerView.Adapter<Eleves_recycler_view_adapter.myViewHolder> implements Filterable {


    private Context context;
    private List<Eleves> Data = new ArrayList<>();
    private List<Eleves> dataFull;

    public Eleves_recycler_view_adapter(Context context, List<Eleves> data) {
        this.context = context;
        Data = data;
        dataFull = new ArrayList<>(Data);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.item_eleves,viewGroup,false);
        final myViewHolder vHolder = new myViewHolder(v);

        //Déclaration de l'événnement sur les éléments de la liste
        vHolder.item_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Editer_eleve_Activity.class);
                intent.putExtra("produitSelectione", (Serializable) Data.get(vHolder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(Data.get(i).getNom()+" "+Data.get(i).getPrenom());
        myViewHolder.tv_classe.setText(Data.get(i).getClasse());
        myViewHolder.tv_matricule.setText(Data.get(i).getMatricule());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public void setListDesProduits(List<Eleves> list){
        this.Data = list;
        notifyDataSetChanged();
    }



    public static class myViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_product;
        private TextView tv_name;
        private TextView tv_classe;
        private TextView tv_matricule;
        private ImageView img;

        public myViewHolder(View itemView){
            super(itemView);
            item_product = (LinearLayout)itemView.findViewById(R.id.eleve_item_id);
            tv_name= (TextView)itemView.findViewById(R.id.nom_eleve);
            tv_classe= (TextView)itemView.findViewById(R.id.classe);
            tv_matricule= (TextView)itemView.findViewById(R.id.matricule);
            img= (ImageView)itemView.findViewById(R.id.image_eleve);



        }
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Eleves> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(dataFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Eleves eleve : dataFull){
                    if(eleve.getNom().toLowerCase().contains(filterPattern)){
                        filteredList.add(eleve);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Data.clear();
            Data.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
