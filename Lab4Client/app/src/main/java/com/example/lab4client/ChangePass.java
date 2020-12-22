package com.example.lab4client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePass extends AppCompatActivity {

    private String username;
    private TextView usname;
    private String oldpass;
    private EditText edoldpass;
    private String newpass;
    private EditText ednewpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        initview();
    }
    void initview(){
        Intent intent = getIntent();//获取首页传过来的用户名
        username = intent.getStringExtra("username");
        usname=findViewById(R.id.cp_name);
        usname.setText("用户名："+username);
        edoldpass=findViewById(R.id.oldpass);
        ednewpass=findViewById(R.id.newpass);
        oldpass=edoldpass.getText().toString();
        newpass=ednewpass.getText().toString();
    }

    public void Goback(View view){
        Intent intent = new Intent(ChangePass.this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void Change(View view){
        //通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePass.this);
        //    设置Content来显示一个信息
        builder.setMessage("确定修改吗？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oldpass=edoldpass.getText().toString();
                newpass=ednewpass.getText().toString();
                if(oldpass.isEmpty()){
                    toast("旧密码不能为空");
                }else{
                    if(newpass.isEmpty()){
                        toast("新密码不能为空");
                    }else{
                        ChangeRequest(username,oldpass,newpass);
                    }
                }
            }
        });
        //设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(ChangePass.this, "取消修改 ", Toast.LENGTH_SHORT).show();
            }
        });
        //显示出该对话框
        builder.show();
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePass.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ChangeRequest(String username,String oldpass,String newpass){
        String myurl = "http://123.57.252.151:8080/Lab-2/ChangePass";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
                .add("oldpass",oldpass)
                .add("newpass",newpass)
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
                        Log.i("修改密码成功",result);

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
            Intent intent = new Intent(ChangePass.this, MainActivity.class);
            startActivity(intent);
        }else{
            toast(result.getMessage());
        }
    }


}
