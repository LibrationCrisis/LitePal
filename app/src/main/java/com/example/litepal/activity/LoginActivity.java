package com.example.litepal.activity;


import androidx.appcompat.app.AppCompatActivity;

import org.litepal.LitePal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.litepal.R;
import com.example.litepal.application.MyApplication;
import com.example.litepal.model.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // 全局变量
    private MyApplication app;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化控件并实例化
        initView();
    }

    /**
     * 初始化控件并实例化
     */
    private void initView() {
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        Button exit = findViewById(R.id.exit);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        exit.setOnClickListener(this);
        // 获取全局变量
        app = (MyApplication) getApplication();
    }

    @Override
    public void onClick(View v) {
        // 用户名
        EditText username_text = findViewById(R.id.username_text);
        String username_data = username_text.getText().toString();
        // 密码
        EditText password_text = findViewById(R.id.password_text);
        String password_data = password_text.getText().toString();

        switch (v.getId()) {
            case R.id.login:
                // 登录操作
                login(username_data, password_data);
                break;
            case R.id.register:
                // 注册操作
                register();
                break;
            case R.id.exit:
                // 退出操作
                exit();
            default:
                break;
        }
    }

    /**
     * 退出操作
     */
    private void exit() {
        // 设置对话提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("警告");
        builder.setMessage("你确定要退出吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 登录操作
     *
     * @param username_data 输入的用户名
     * @param password_data 输入的密码
     */
    private void login(String username_data, String password_data) {
        if ("".equals(username_data)) {
            // 判断用户名是否为空
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(password_data)) {
            // 判断密码是否为空
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else {
            // 查询数据库是否存在
            List<User> username = LitePal.select("username").where("username = ?", username_data).find(User.class);
            if (username.isEmpty()) {
                // 不存在用户名
                Toast.makeText(this, "用户名错误", Toast.LENGTH_SHORT).show();
            } else {
                // 存在,判断密码是否正确
                List<User> data = LitePal.where("username = ? AND password = ?", username_data, password_data).find(User.class);
                if (data.isEmpty()) {
                    // 密码错误
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    // 对象存入全局变量
                    app.setUser(data.get(0));
                    // 跳转页面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * 注册操作
     */
    private void register() {
        // 跳转页面
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
