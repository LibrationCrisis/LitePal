package com.example.litepal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.litepal.R;
import com.example.litepal.model.User;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 初始化控件并实例化
        initView();
    }

    private void initView() {
        Button register = findViewById(R.id.register);
        Button exit = findViewById(R.id.exit);

        register.setOnClickListener(this);
        exit.setOnClickListener(this);
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
            case R.id.register:
                // 注册操作
                register(username_data, password_data);
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
     * 注册操作
     *
     * @param username_data 输入的用户名
     * @param password_data 输入的密码
     */
    private void register(String username_data, String password_data) {
        if ("".equals(username_data)) {
            // 判断用户名是否为空
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(password_data)) {
            // 判断密码是否为空
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else {
            // 查询数据库是否同名
            List<User> username = LitePal.select("username").where("username = ?", username_data).find(User.class);
            if (username.isEmpty()) {
                // 不存在,存储数据
                User user = new User();
                user.setUsername(username_data);
                user.setPassword(password_data);
                try {
                    // 存储失败则报错
                    user.saveThrows();
                } catch (DataSupportException e) {
                    e.printStackTrace();
                }
                // 返回登录
                exit();
            } else {
                // 已存在用户名
                Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 退出操作
     */
    private void exit() {
        // 关闭页面
        finish();
    }
}
