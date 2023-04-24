package com.example.blindareasecuritysystem.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.blindareasecuritysystem.Adapter.xialaAdapt;
import com.example.blindareasecuritysystem.R;

import java.util.Objects;

public class ConnectionFragment extends Fragment {

    private final xialaAdapt expert = new xialaAdapt();

    private TextView lightningText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_connection, container, false);

        lightningText = view.findViewById(R.id.text_view_lightning);
        changeStyle();

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

    private void changeStyle() {
        // 放大显示电量的数值
        String lightning = lightningText.getText().toString();
        SpannableString spannableString1 = new SpannableString(lightning);
        RelativeSizeSpan bigSizeSpan = new RelativeSizeSpan(1.6f);
        spannableString1.setSpan(bigSizeSpan, 2, spannableString1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        lightningText.setText(spannableString1);
    }
}