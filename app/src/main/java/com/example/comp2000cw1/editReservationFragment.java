package com.example.comp2000cw1;

import static com.example.comp2000cw1.ReservationsFragment.selectedReservation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editReservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static editReservationFragment newInstance(String param1, String param2) {
        editReservationFragment fragment = new editReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_reservation, container, false);
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        String text = "";
        TextView reservationDisplay = root.findViewById(R.id.reservationTable);
        Toast.makeText(requireContext(), "ID = " + selectedReservation, Toast.LENGTH_SHORT).show();
        DatabaseHelper DatabaseHelper = new DatabaseHelper(requireContext());
        List<DataModelReservations> reservations = Collections.singletonList(DatabaseHelper.getReservationByID(selectedReservation));
        for (DataModelReservations reservation : reservations) {
            text += reservation.getReservationName() + "    " + reservation.getReservationDate() + "   " + reservation.getReservationTime() + "  " + reservation.getReservationGuests();
        }
        reservationDisplay.setText(text);
        return root;
    }
}