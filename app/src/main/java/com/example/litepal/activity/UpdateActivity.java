package com.example.litepal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.litepal.R;
import com.example.litepal.application.MyApplication;
import com.example.litepal.model.User;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username_text;
    private EditText password_text;
    private MyApplication app;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        Button update = findViewById(R.id.update);
        Button exit = findViewById(R.id.exit);

        update.setOnClickListener(this);
        exit.setOnClickListener(this);

        // 用户名
        username_text = findViewById(R.id.username_text);
        // 密码
        password_text = findViewById(R.id.password_text);

        // 获取全局变量的数据
        app = (MyApplication) getApplication();
        user = app.getUser();

        // 显示数据
        username_text.setText(user.getUsername());
        password_text.setText(user.getPassword());


    }

    @Override
    public void onClick(View v) {
        // 用户名
        String username_data = username_text.getText().toString();
        // 密码
        String password_data = password_text.getText().toString();

        switch (v.getId()) {
            case R.id.update:
                // 修改操作
                update(username_data, password_data);
                break;
            case R.id.exit:
                // 退出操作
                exit();
                break;
            default:
                break;
        }
    }

    private void update(final String username_data, final String password_data) {
        if ("".equals(username_data)) {
            // 判断用户名是否为空
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(password_data)) {
            // 判断密码是否为空
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else {
            // 设置对话提示框
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
            builder.setTitle("警告");
            builder.setMessage("你确定要修改账号吗");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 存储数据~
                    user.setUsername(username_data);
                    user.setPassword(password_data);
                    user.updateAll();
                    app.setUser(user);
                    // 返回登录
                    exit();
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
    }

    /**
     * 退出操作
     */
    private void exit() {
        finish();
    }
}
