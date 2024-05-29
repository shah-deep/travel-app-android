package edu.sjsu.android.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private ArrayList<HomeLocations> itemsAll;

    private ArrayList<HomeLocations> items;
    public interface RecyclerViewInterface {
        void onItemClick(int position);
    }

    private Context context;
    private RecyclerViewInterface recyclerViewInterface;
    DecimalFormat formatter;

    public PopularAdapter(ArrayList<HomeLocations> items) {
        this.itemsAll = items;
        this.items = new ArrayList<>(itemsAll);
        formatter = new DecimalFormat("###,###,###,###");
    }


    public PopularAdapter(Context packageContext, ArrayList<HomeLocations> items) {
        this.itemsAll = items;
        this.items = new ArrayList<>(itemsAll);
        formatter = new DecimalFormat("###,###,###,###");
        this.context = packageContext;
    }

    public void filter(String text) {
        items.clear();
        if (text.isEmpty()) {
            // itemsAll.stream().limit(4).collect(Collectors.toList());
            items.addAll(itemsAll);
        } else {
            text = text.toLowerCase();
            for (HomeLocations item : itemsAll) {
                if (item.getTitle().toLowerCase().contains(text) || item.getLocation().toLowerCase().contains(text)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_locations, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
//        System.out.println(position);
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.locationTxt.setText(items.get(position).getLocation());
        holder.scoreTxt.setText("" + items.get(position).getRating());
//        System.out.println(items.get(position).getPic());
        int drawableResId = holder.itemView.getResources().getIdentifier(items.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(items.get(position).getPic())
                .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewInterface.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTxt, locationTxt, scoreTxt;

        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            locationTxt = itemView.findViewById(R.id.locationTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.locImg);
        }
    }

    public ArrayList<HomeLocations> getItems() {
        return items;
    }

    public void setItems(ArrayList<HomeLocations> items) {
        this.items = items;
    }

    public RecyclerViewInterface getRecyclerViewInterface() {
        return recyclerViewInterface;
    }

    public void setRecyclerViewInterface(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }
}
