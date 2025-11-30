package com.example.comp2000cw1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMenuFragment extends Fragment {
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddMenuFragment() {
        // Required empty public constructor
    }
    
    public static AddMenuFragment newInstance(String param1, String param2) {
        AddMenuFragment fragment = new AddMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText editDishName, editDishType, editDishDescription, editDishPrice, editDishAllergens, editDishImage;
    Button addButton;


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
        View root = inflater.inflate(R.layout.fragment_add_menu, container, false);

        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        editDishName=root.findViewById(R.id.dishName);
        editDishType=root.findViewById(R.id.dishType);
        editDishDescription=root.findViewById(R.id.dishDescription);
        editDishPrice=root.findViewById(R.id.dishPrice);
        editDishAllergens=root.findViewById(R.id.dishAllergens);
        editDishImage=root.findViewById(R.id.dishImage);
        addButton=root.findViewById(R.id.addMenuButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel data = new DataModel(
                        editDishName.getText().toString(),
                        editDishType.getText().toString(),
                        editDishDescription.getText().toString(),
                        editDishPrice.getText().toString(),
                        editDishAllergens.getText().toString(),
                        editDishImage.getText().toString());
                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                boolean success = databaseHelper.addDish(data);
                if(success) {
                    Toast.makeText(requireContext(), "Dish added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error adding dish", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return root;
    }
}