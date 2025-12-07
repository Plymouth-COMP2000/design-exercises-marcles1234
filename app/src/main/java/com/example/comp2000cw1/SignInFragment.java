package com.example.comp2000cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class SignInFragment extends Fragment {
    EditText username, password;
    TextView textViewResult;
    Button signInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_in, container, false);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        signInButton = root.findViewById(R.id.signInButton);
        textViewResult = root.findViewById(R.id.textViewResult);

        signInButton.setOnClickListener(v -> getUser(username.getText().toString(), password.getText().toString()));
        return root;
    }

    private void getUser(String username, String password) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String url = "http://10.240.72.69/comp2000/coursework/read_user/{10861908}/" + username;
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject user = jsonObject.getJSONObject("user");
                        String storedPassword = user.getString("password");
                        if (storedPassword.equals(password)) {
                            editor.putBoolean("Signed In", true);
                            editor.apply();
                            HomeFragment newFragment = new HomeFragment();
                            ((MainActivity) requireActivity()).replaceFragment(newFragment);
                        }
                    } catch (Exception e) {
                        textViewResult.setText("Error: " + e.getMessage());
                    }
                },
                error -> textViewResult.setText("Error: " + error.toString())
        );
        queue.add(request);
    }
}