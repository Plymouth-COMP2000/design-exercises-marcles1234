package com.example.comp2000cw1;

import static com.example.comp2000cw1.ReservationsFragment.selectedReservation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMenuFragment extends Fragment {

    EditText editDishName, editDishDescription, editDishPrice, editDishAllergens, editDishImage;
    Spinner editDishType;
    Button addButton;
    boolean dishTypeValidMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_menu, container, false);

        //BACK BUTTON
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });


        //TYPE SPINNER
        editDishType = root.findViewById(R.id.dishType);
        String[] categories = {"Starter", "Pasta", "Pizza", "Sides", "Dessert"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                categories);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDishType.setAdapter(timeAdapter);

        editDishName=root.findViewById(R.id.dishName);
        editDishDescription=root.findViewById(R.id.dishDescription);
        editDishPrice=root.findViewById(R.id.dishPrice);
        editDishAllergens=root.findViewById(R.id.dishAllergens);
        editDishImage=root.findViewById(R.id.dishImage);
        addButton=root.findViewById(R.id.addMenuButton);

        //ADD DISH BUTTON
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishTypeValidMarker = false;
                DataModel data = new DataModel(
                        editDishName.getText().toString(),
                        editDishType.getSelectedItem().toString(),
                        editDishDescription.getText().toString(),
                        editDishPrice.getText().toString(),
                        editDishAllergens.getText().toString(),
                        editDishImage.getText().toString());


                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                DataModel existingDish = databaseHelper.getDishByName(data.getDishName());
                if (existingDish != null) {
                    Toast.makeText(requireContext(), editDishName.getText().toString() + " already exists", Toast.LENGTH_SHORT).show();
                } else {
                    boolean success = databaseHelper.addDish(data);
                    if (success) {
                        Toast.makeText(requireContext(), "Dish added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Error adding dish", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return root;
    }
}