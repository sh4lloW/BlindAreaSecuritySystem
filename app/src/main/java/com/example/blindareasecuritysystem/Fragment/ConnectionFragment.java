package com.example.blindareasecuritysystem.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.blindareasecuritysystem.Adapter.xialaAdapt;
import com.example.blindareasecuritysystem.R;

public class ConnectionFragment extends Fragment {

    private final xialaAdapt expert = new xialaAdapt();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_connection, container, false);
        Spinner spinner = view.findViewById(R.id.feature);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String feature = spinner.getSelectedItem().toString();
                String score = expert.getScore(feature);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }
}