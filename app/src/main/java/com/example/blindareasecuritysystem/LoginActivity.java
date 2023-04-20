package com.example.blindareasecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.blindareasecuritysystem.Helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 绑定
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.btn_login);

        // 实例化数据库变量
        dbHelper = new DatabaseHelper(this, "user.db", null, 1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取文本框文本
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                //TODO 在数据库中验证
            }
        });
    }

    // 跳转注册页面，绑定的是那个注册的Textview
    public void toRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}