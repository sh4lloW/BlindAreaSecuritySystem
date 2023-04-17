package com.example.blindareasecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username, password, confirmPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 绑定
        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_password);
        confirmPassword = findViewById(R.id.et_register_confirm_password);
        register = findViewById(R.id.btn_register);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 获取文本框文本
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        // 文本合法性检测
        if (TextUtils.isEmpty(usernameText)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPasswordText)) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (!TextUtils.equals(passwordText, confirmPasswordText)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_LONG).show();
            return;
        }

        // 用户信息本地存储
        SharedPreferences sp = getSharedPreferences("UserMessage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", usernameText);
        editor.putString("password", passwordText);
        editor.apply();

        Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
        this.finish();
    }
}