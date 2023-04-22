package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blindareasecuritysystem.Helper.DatabaseHelper;
import com.example.blindareasecuritysystem.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username, password, confirmPassword;
    private Button register;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 绑定
        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_password);
        confirmPassword = findViewById(R.id.et_register_confirm_password);
        register = findViewById(R.id.btn_register);

        // 实例化数据库变量
        dbHelper = new DatabaseHelper(RegisterActivity.this, "user.db", null, 1);

        register.setOnClickListener(RegisterActivity.this);
    }

    @Override
    public void onClick(View view) {
        // 获取文本框文本
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        // 文本合法性检测
        if (TextUtils.isEmpty(usernameText)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPasswordText)) {
            Toast.makeText(RegisterActivity.this, "确认密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (!TextUtils.equals(passwordText, confirmPasswordText)) {
            Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_LONG).show();
            return;
        }

        // 写入用户信息
        insertUserData(dbHelper.getReadableDatabase(), usernameText, passwordText);

        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
        RegisterActivity.this.finish();
    }

    // 插入用户数据
    private void insertUserData(SQLiteDatabase database, String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        database.insert("user", null, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}