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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static editMenuFragment newInstance(String param1, String param2) {
        editMenuFragment fragment = new editMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    EditText editDishName, editDishType, editDishDescription, editDishPrice, editDishAllergens, editDishImage;
    Button updateMenuButton, deleteMenuButton;

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