package edu.sjsu.android.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.textBookingId.setText("Booking ID: " + booking.getBookingId());
        holder.textLocation.setText(booking.getLocation());
        holder.textDate.setText(booking.getBookingDate());
        holder.textPrice.setText("Price: $" + booking.getBookingPrice());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView textBookingId;
        TextView textLocation;
        TextView textDate;
        TextView textPrice;

        public BookingViewHolder(View itemView) {
            super(itemView);

            textBookingId = itemView.findViewById(R.id.text_booking_id);
            textLocation = itemView.findViewById(R.id.text_location);
            textDate = itemView.findViewById(R.id.text_date);
            textPrice = itemView.findViewById(R.id.text_price);
        }
    }
}

