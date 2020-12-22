package com.example.lab4client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;


import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button submitButton;
    private Button cancleButton;

    private EditText uname1;
    private EditText teleno1;
    private EditText pw1;
    private EditText pw2;

    private String username;
    private String pass1;
    private String pass2;
    private String teleno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
    }

    void initview(){
        uname1=findViewById(R.id.uname1);
        teleno1=findViewById(R.id.teleno1);
        pw1=findViewById(R.id.pw1);
        pw2=findViewById(R.id.pw2);
        submitButton = findViewById(R.id.button3);
        cancleButton = findViewById(R.id.button4);
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Goback(View view){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Register(View view) {
        if(check()){
            toast("SignUp...");
            RegisterRequest(username,pass1,teleno);
        }
    }

    public void RegisterRequest(String username,String password,String teleno){
        String myurl = "http://123.57.252.151:8080/Lab-2/Signup";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
                .add("password",password)
                .add("teleno",teleno)
                .build();
        Request request = new Request.Builder()//调用post方法，将RequestBody对象传入
                .url(myurl)
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    //调用newCall方法来创建一个call对象，并用他的execute()方法来发送请求，并获取服务器返回的对象
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        parseGson(result);
                        Log.i("chenggong",result);

                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void parseGson(String jsonData){
        Gson gson =new Gson();
        Result result = gson.fromJson(jsonData,Result.class);
        if(result.getFlag()==1){
            toast(result.getMessage());
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            toast(result.getMessage());
        }
        Log.d("flag", String.valueOf(result.getFlag()));
        Log.d("zhuce", result.getMessage());
    }


    public boolean check(){//表单验证
        boolean isok = false;
        username  = uname1.getText().toString();
        if(username.isEmpty()){
            toast("用户名不能为空");
            return isok;
        }else{
            String mess1 = "";
            String pattern1 = "^[a-zA-Z][a-zA-Z\\d_]*";
            String pattern2 = ".*[A-Z].*";
            boolean match1 = Pattern.matches(pattern1, username);
            boolean match2 = Pattern.matches(pattern2, username);
            if (username.length() < 5 || username.length() > 10)
                mess1 += "用户名需要在5到10位";
            if (!match1) {
                mess1 += "用户名只允许包含英文字母、数字以及_，且必须以字母开头";
            }
            if (!match2) {
                mess1 += "用户名必须包含一个大写英文字母";
            }
            if (!mess1.isEmpty()) {
                toast(mess1);
                return isok;
            }

            teleno = teleno1.getText().toString();
            if(teleno.isEmpty()){
                toast("电话号码不能为空");
                return isok;
            }else{
                String pattern5 = "[1][3,4,5,7,8]\\d{9}";
                boolean match5 = Pattern.matches(pattern5, teleno);
                if (!match5) {
                    toast("请输入正确的电话号码");
                    return isok;
                }
            }

            pass1  = pw1.getText().toString();
            if(pass1.isEmpty()){
                toast("密码不能为空");
                return isok;
            }else{
                String mess = "";
                if (pass1.length() < 6  || pass1.length() > 12 )
                    mess += "密码必须是6到12位";
                String pattern6 = "[a-zA-Z\\d_]*";
                boolean match6 = Pattern.matches(pattern6, pass1);
                if (!match6) {
                    mess += "密码只允许包含英文字母、数字和_";
                }
                if (!mess.isEmpty()) {
                    toast(mess);
                    return isok;
                }

                pass2  = pw2.getText().toString();
                if(!pass1.equals(pass2)){
                    toast("两次输入的密码不一致");
                    return isok;
                }
                else{
                    isok = true;
                    return isok;
                }
            }

        }
    }

}
