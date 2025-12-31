package com.example.comp2000cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private List<DataModelReservations> reservations;

    //CONSTRUCT ADAPTER
    public ReservationAdapter(List<DataModelReservations> reservations, OnItemClickListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    //UPDATE IF NEEDED
    public void setReservations(List<DataModelReservations> newReservations) {
        this.reservations = newReservations;
        notifyDataSetChanged();
    }

    //INFLATE RESERVATION
    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    //SET DATA TO RESPECTIVE VIEWS
    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        DataModelReservations reservation = reservations.get(position);
        holder.idTextView.setText(String.valueOf(reservation.getReservationId()));
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        //DECREASE TEXT SIZE AND DISPLAY NAME IF STAFF
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            holder.nameTextView.setText(reservation.getReservationName());
            holder.nameTextView.setTextSize(12);
            holder.dateTextView.setTextSize(12);
            holder.timeTextView.setTextSize(12);
            holder.guestsTextView.setTextSize(12);
        } else {
            holder.nameTextView.setText("");
        }
        holder.dateTextView.setText(reservation.getReservationDate());
        holder.timeTextView.setText(reservation.getReservationTime());
        holder.guestsTextView.setText(reservation.getReservationGuests());

        holder.selectButton.setOnClickListener(v -> {
            listener.onItemClick(reservation);
        });
    }

    //SIZE
    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, idTextView, dateTextView, timeTextView, guestsTextView;
        public Button selectButton;

        //FIND VIEWS IN HOLDER
        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.reservationId);
            nameTextView = itemView.findViewById(R.id.reservationName);
            dateTextView = itemView.findViewById(R.id.reservationDate);
            timeTextView = itemView.findViewById(R.id.reservationTime);
            guestsTextView = itemView.findViewById(R.id.reservationGuests);
            selectButton = itemView.findViewById(R.id.reservationButton);
        }
    }

    //RESPECTIVE BUTTON SELECTION LISTENER
    public interface OnItemClickListener {
        void onItemClick(DataModelReservations reservation);
    }

    private OnItemClickListener listener;

}
