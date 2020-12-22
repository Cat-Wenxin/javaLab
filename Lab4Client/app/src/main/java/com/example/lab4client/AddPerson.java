package com.example.lab4client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AddPerson extends AppCompatActivity {
    private String username;
    private TextView usname;

    private EditText epname;
    private EditText epage;
    private EditText epteleno;

    private String pname;
    private int page;
    private String pteleno;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
        Intent intent = getIntent();//获取登录时传过来的参数
        username = intent.getStringExtra("username");
        usname=findViewById(R.id.ap_uname);
        usname.setText("用户名："+username);
        initview();
    }

    void initview(){
        epname=findViewById(R.id.pname);
        epage=findViewById(R.id.page);
        epteleno=findViewById(R.id.pteleno);
        submitButton = findViewById(R.id.button3);
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddPerson.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Goback(View view){
        Intent intent = new Intent(AddPerson.this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void Add(View view){
        if(check()){
            AddRequest(username,pname,page,pteleno);
        }else{
            toast("添加失败");
        }
    }

    public void AddRequest(String username,String pname,int page,String pteleno){
        String myurl = "http://123.57.252.151:8080/Lab-2/ResponseAddPerson";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
                .add("pname",pname)
                .add("page", String.valueOf(page))
                .add("pteleno",pteleno)
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
            toast(result.getMessage()+"，请刷新");
            Intent intent = new Intent(AddPerson.this, WelcomeActivity.class);
            startActivity(intent);
        }else{
            toast(result.getMessage());
        }
        Log.d("flag", String.valueOf(result.getFlag()));
        Log.d("zhuce", result.getMessage());
    }

    public boolean check() {
        boolean isok = false;
        pname= epname.getText().toString();
        if(pname.isEmpty()){
            toast("联系人姓名不能为空");
            return isok;
        }else{

            if(!epage.getText().toString().isEmpty()){//若填写了年龄
                page= Integer.valueOf(epage.getText().toString());
                String pattern = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
                boolean match = Pattern.matches(pattern,String.valueOf(page));
                if (!match) {
                    toast("年龄填写不规范，只能输入1到120");
                    return isok;
                }
            }

            pteleno = epteleno.getText().toString();
            if(pteleno.isEmpty()){
                toast("电话号码不能为空");
                return isok;
            }else{
                String pattern2 ="[1][3,4,5,7,8]\\d{9}";
                boolean match2 = Pattern.matches(pattern2, pteleno);
                if (!match2) {
                    toast("请输入正确的电话号码");
                    return isok;
                }else{
                    isok = true;
                    return  isok;
                }
            }
        }

    }
}

