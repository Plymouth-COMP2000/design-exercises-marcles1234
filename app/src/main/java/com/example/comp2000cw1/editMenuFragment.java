package com.example.comp2000cw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editMenuFragment extends Fragment {


    public editMenuFragment() {
        // Required empty public constructor
    }
    public static editMenuFragment newInstance(String param1, String param2) {
        editMenuFragment fragment = new editMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    EditText editDishName, editDishType, editDishDescription, editDishPrice, editDishAllergens, editDishImage;
    Button updateMenuButton, deleteMenuButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editDishName = view.findViewById(R.id.dishName);
        editDishType = view.findViewById(R.id.dishType);
        editDishDescription = view.findViewById(R.id.dishDescription);
        editDishPrice = view.findViewById(R.id.dishPrice);
        editDishAllergens = view.findViewById(R.id.dishAllergens);
        editDishImage = view.findViewById(R.id.dishImage);
        updateMenuButton = view.findViewById(R.id.updateMenuButton);
        deleteMenuButton = view.findViewById(R.id.deleteMenuButton);

        String dishName = getArguments().getString("dishName");
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        DataModel itemData = databaseHelper.getDishByName(dishName);

        editDishName.setText(itemData.getDishName());
        editDishType.setText(itemData.getDishType());
        editDishDescription.setText(itemData.getDishDescription());
        editDishPrice.setText(itemData.getDishPrice());
        editDishAllergens.setText(itemData.getDishAllergens());
        editDishImage.setText(itemData.getDishImage());


        updateMenuButton.setOnClickListener(v -> {
            DataModel data = new DataModel(
                    editDishName.getText().toString(),
                    editDishType.getText().toString(),
                    editDishDescription.getText().toString(),
                    editDishPrice.getText().toString(),
                    editDishAllergens.getText().toString(),
                    editDishImage.getText().toString());
            databaseHelper.updateDish(dishName, data);
        });

        deleteMenuButton.setOnClickListener(v -> {
            databaseHelper.deleteDish(dishName);
            Toast.makeText(requireContext(), "Dish deleted successfully", Toast.LENGTH_SHORT).show();
        });
    }
}