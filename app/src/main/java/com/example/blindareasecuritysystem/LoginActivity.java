package com.example.blindareasecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blindareasecuritysystem.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;

    private DatabaseHelper dbHelper;

    // 数据库中查询出来的密码
    String dbPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 绑定
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.btn_login);

        // 实例化数据库变量
        dbHelper = new DatabaseHelper(LoginActivity.this, "user.db", null, 1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取文本框文本
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                // 先查找用户名在数据库中是否存在
                Cursor cursor = dbHelper.getReadableDatabase().query("user", null, "username = ?", new String[]{usernameText}, null, null, null);
                // 查出来的放队列里，用于检测是否有该用户
                List<Map<String, String>> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    // 查出来的放进Hash
                    Map<String, String> map = new HashMap<>();
                    // 第二、第三列分别对应用户名和密码
                    map.put("username", cursor.getString(1));
                    map.put("password", cursor.getString(2));
                    // 取出密码
                    dbPassword = map.get("password");
                    // 查到了存进list中
                    list.add(map);
                }
                // 先判断用户是否存在
                if (!list.isEmpty()) {
                    // 存在的话判断密码是否匹配
                    if (dbPassword.equals(passwordText)) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                        //TODO 跳转主页面
//                        Intent intent = new Intent(LoginActivity.this, Bottomnav.class);
//                        startActivity(intent);

                        LoginActivity.this.finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "该用户未注册", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // 跳转注册页面，绑定的是那个注册的Textview
    public void toRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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