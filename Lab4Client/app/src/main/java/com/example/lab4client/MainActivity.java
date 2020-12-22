package com.example.lab4client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private String username;
    private String password;
    private  String teleno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    public void initview(){
        usernameEditText = findViewById(R.id.et1);
        passwordEditText = findViewById(R.id.et2);
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Login(View view){
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(MainActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        } else if(password.isEmpty()){
            Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        } else {
            toast("Login...");
            LoginRequest(username,password);
        }

    }

    public void SignUp(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Forget(View view){
        Intent intent = new Intent(MainActivity.this, ForgetActivity.class);
        startActivity(intent);
    }

    public void LoginRequest(String username, String password){
        String myurl = "http://123.57.252.151:8080/Lab-2/Login";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
                .add("password",password)
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
                        Log.i("chenggong",result);
                        parseGson(result);
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
            teleno=result.getData().getTeleno();
            Log.d("teleno:", teleno+"");
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("teleno",teleno);
            startActivity(intent);
        }else{
            toast(result.getMessage());
        }
    }
}