package edu.sjsu.android.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TravelPackageAdapter extends RecyclerView.Adapter<TravelPackageAdapter.MyViewHolder> {
    RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<TravelPackage> travelPackagesList;
    TravelPackageAdapter(Context context, ArrayList<TravelPackage> travelPackagesList, RecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.travelPackagesList = travelPackagesList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_layout, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //assign values to the views we created in the recycler view row layout file
        //based on position of recycler view
        holder.name.setText(travelPackagesList.get(position).name);
        holder.price.setText("$."+travelPackagesList.get(position).price);
    }

    @Override
    public int getItemCount() {
        //number of items you want to be displayed
        return travelPackagesList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
