package com.example.litepal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.litepal.R;
import com.example.litepal.application.MyApplication;
import com.example.litepal.model.User;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 全局变量
    private MyApplication app;
    // 封装对象
    private User user;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件并实例化
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获取全局变量的数据
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("用户名:").append(user.getUsername()).append("\n");
        stringBuilder.append("密码:").append(user.getPassword()).append("\n");

        //赋值并显示
        show.setText(stringBuilder);
    }

    /**
     * 初始化控件并实例化
     */
    private void initView() {
        Button update = findViewById(R.id.update);
        Button delete = findViewById(R.id.delete);
        Button exit = findViewById(R.id.exit);
        show = findViewById(R.id.show);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        exit.setOnClickListener(this);


        app = (MyApplication) getApplication();
        user = app.getUser();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                //修改操作
                update();
                break;
            case R.id.delete:
                // 删除操作
                delete();
                break;
            case R.id.exit:
                // 退出操作
                exit();
                break;
            default:
                break;

        }
    }

    /**
     * 退出操作
     */
    private void exit() {
        // 关闭页面
        finish();
    }

    /**
     * 删除操作
     */
    private void delete() {
        // 设置对话提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("警告");
        builder.setMessage("你确定要注销账号吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LitePal.deleteAll(User.class, "username = ? AND password = ?", user.getUsername(), user.getPassword());
                user = new User();
                app.setUser(user);
                dialog.dismiss();
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

    /**
     * 修改操作
     */
    private void update() {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(intent);
    }
}
