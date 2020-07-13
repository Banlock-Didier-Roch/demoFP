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


import com.szadst.szoemhost_and.Domaine.Parent;
import com.szadst.szoemhost_and.Edit_Parent_Activity;
import com.szadst.szoemhost_and.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parent_recycler_view_adapter extends RecyclerView.Adapter<Parent_recycler_view_adapter.myViewHolder> implements Filterable {


    private Context context;
    private List<Parent> Data = new ArrayList<>();
    private List<Parent> dataFull;

    public Parent_recycler_view_adapter(Context context, List<Parent> data) {
        this.context = context;
        Data = data;
        dataFull = new ArrayList<>(Data);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.item_parent,viewGroup,false);
        final myViewHolder vHolder = new myViewHolder(v);


        vHolder.item_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Edit_Parent_Activity.class);
                intent.putExtra("parentSelectione", (Serializable) Data.get(vHolder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(Data.get(i).getNom()+" "+Data.get(i).getPrenom());
        myViewHolder.tv_telephone.setText(Data.get(i).getTelephone());
        myViewHolder.tv_email.setText(Data.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public void setListDesProduits(List<Parent> list){
        this.Data = list;
        notifyDataSetChanged();
    }



    public static class myViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_product;
        private TextView tv_name;
        private TextView tv_email;
        private TextView tv_telephone;
        private ImageView img;

        public myViewHolder(View itemView){
            super(itemView);
            item_product = (LinearLayout)itemView.findViewById(R.id.parent_item_id);
            tv_name= (TextView)itemView.findViewById(R.id.nom_parent);
            tv_telephone= (TextView)itemView.findViewById(R.id.telephone_parent);
            tv_email= (TextView)itemView.findViewById(R.id.emailParent);
        }
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Parent> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(dataFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Parent produit : dataFull){
                    if(produit.getNom().toLowerCase().contains(filterPattern)){
                        filteredList.add(produit);
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
