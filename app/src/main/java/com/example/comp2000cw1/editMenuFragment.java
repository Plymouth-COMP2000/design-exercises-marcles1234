package com.example.comp2000cw1;

import static com.example.comp2000cw1.ReservationsFragment.selectedReservation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class editMenuFragment extends Fragment {


    public editMenuFragment() {
        // Required empty public constructor
    }

    EditText editDishName, editDishDescription, editDishPrice, editDishAllergens, editDishImage;
    Spinner editDishType;
    Button updateMenuButton, deleteMenuButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        //BACK BUTTON
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
        editDishDescription.setText(itemData.getDishDescription());
        editDishPrice.setText(itemData.getDishPrice());
        editDishAllergens.setText(itemData.getDishAllergens());
        editDishImage.setText(itemData.getDishImage());


        //TYPE SPINNER
        editDishType = view.findViewById(R.id.dishType);
        String currentTime = (itemData).getDishType();
        String[] categories = {"Starter", "Pasta", "Pizza", "Soup", "Sides"};
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(currentTime)) {
                int temp = i;
                for (int j = 0; j < i; j++) {
                    categories[temp] = categories[temp-1];
                    temp--;
                }
            }
        }
        categories[0] = currentTime;
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                categories
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDishType.setAdapter(timeAdapter);

        //UPDATE MENU BUTTON
        updateMenuButton.setOnClickListener(v -> {
            //CREATE DATAMODEL
            DataModel data = new DataModel(
                    editDishName.getText().toString(),
                    editDishType.getSelectedItem().toString(),
                    editDishDescription.getText().toString(),
                    editDishPrice.getText().toString(),
                    editDishAllergens.getText().toString(),
                    editDishImage.getText().toString());

            DataModel existingDish = databaseHelper.getDishByName(data.getDishName());
            if (existingDish != null && !existingDish.getDishName().equals(dishName)) {
                Toast.makeText(requireContext(), editDishName.getText().toString() + " already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            databaseHelper.updateDish(dishName, data);
            Toast.makeText(requireContext(), "Dish updated", Toast.LENGTH_SHORT).show();
        });

        //DELETE MENU ITEM BUTTON
        deleteMenuButton.setOnClickListener(v -> {
            databaseHelper.deleteDish(dishName);
            Toast.makeText(requireContext(), "Dish deleted successfully", Toast.LENGTH_SHORT).show();
        });
    }
}