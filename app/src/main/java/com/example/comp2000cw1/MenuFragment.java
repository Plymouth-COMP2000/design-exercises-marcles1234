package com.example.comp2000cw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView dishNameText, dishDescriptionText, dishPriceText, dishAllergensText, dishImage;

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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        RecyclerView recyclerView = root.findViewById(R.id.dishScroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> dishes = Arrays.asList("Pepperoni", "Meat Feast", "Margherita", "Hawaiian", "Anchovy");
        DishView adapter = new DishView(dishes);
        recyclerView.setAdapter(adapter);

        dishNameText=root.findViewById(R.id.dishNameText);
        dishDescriptionText=root.findViewById(R.id.dishDescriptionText);
        dishPriceText=root.findViewById(R.id.dishPriceText);
        dishAllergensText=root.findViewById(R.id.dishAllergensText);
        dishImage=root.findViewById(R.id.dishImage);

        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        DataModel data = databaseHelper.getDishByName("Spaghetti");
        dishNameText.setText(data.getDishName());
        dishDescriptionText.setText(data.getDishDescription());
        dishPriceText.setText(data.getDishPrice());
        dishAllergensText.setText(data.getDishAllergens());
        dishImage.setText(data.getDishImage());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button additemBtn = view.findViewById(R.id.additem);

        additemBtn.setOnClickListener(v -> {
            AddMenuFragment newFragment = new AddMenuFragment();

            ((MainActivity) requireActivity()).replaceFragment(newFragment);
        });
    }
}

