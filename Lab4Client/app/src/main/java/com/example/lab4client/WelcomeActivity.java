package com.example.lab4client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WelcomeActivity extends AppCompatActivity {
    private  DrawerLayout myDL;
    private  String username="";
    private  String teleno;
    private List<PersonResult.Persons> personlist ;
    private Handler handler;

    private TextView TextViewusname;

    //定义UPDATE_TEXT这个整型常量，用于表示从子线程接收数据
    public static final int UPDATE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();//获取登录时传过来的参数
        username = intent.getStringExtra("username");
        teleno = intent.getStringExtra("teleno");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //抽屉菜单
        myDL=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        //获取头部布局,设置头部的用户名和电话号码
        View headerView = nav_view.getHeaderView(0);
        TextView textview1 = (TextView)headerView.findViewById(R.id.nav_username);
        textview1.setText(""+username);
        TextView textview2 = (TextView)headerView.findViewById(R.id.nav_teleno);
        textview2.setText("Tel:"+teleno);
        //设置bar上的图标
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_username);
        }
        nav_view.setCheckedItem(R.id.nav_return);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_return:
                        myDL.closeDrawers();
                        break;
                    case R.id.nav_changepass:
                        Intent intent = new Intent(WelcomeActivity.this, ChangePass.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    case R.id.nav_overlogin:
                        Intent intent1 = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        Log.d("测试","我从oncreate进的");
        //从服务端获取personlist
        GetPersonList();

        //获取子线程中的数据
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT:
                        //在这里可以进行UI操作
                        //对msg.obj进行String强制转换
                        Log.d("测试","回到了UI线程中。");
                        String result=(String)msg.obj;
                        parseGson(result);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        //设置list中的数据
        //  initPersons();

    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WelcomeActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tb_addperson://此处写点击增加之后的操作
                //GetPersonList();
                Intent intent = new Intent(WelcomeActivity.this,AddPerson.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case android.R.id.home:
                myDL.openDrawer(GravityCompat.START);
                break;
            case R.id.tb_shuaxin:
                Log.d("测试","我从shuaxin进的");
                //从服务端获取personlist
                GetPersonList();
                break;
            default:
        }
        return true;
    }

    public void GetPersonList(){
        Log.d("测试","进入到了getpersonlist");
        String myurl = "http://123.57.252.151:8080/Lab-2/ShowPersons";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
                .build();
        Request request = new Request.Builder()//调用post方法，将RequestBody对象传入
                .url(myurl)
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("测试","进入到了新线程中");
                Response response = null;
                try {
                    //回调
                    //调用newCall方法来创建一个call对象，并用他的execute()方法来发送请求，并获取服务器返回的对象
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        message.obj=result;
                        handler.sendMessage(message);
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
        PersonResult result = gson.fromJson(jsonData,PersonResult.class);
        if(result.getFlag()==1){
            personlist=result.getPersons();
            PersonAdapter adapter = new PersonAdapter(WelcomeActivity.this,R.layout.person_item,personlist);
            ListView listView =(ListView) findViewById(R.id.list_view);
            if(personlist.size()>0)
                listView.setAdapter(adapter);
            else
                Log.d("结果","perlist为空");
            for(int i=0;i<personlist.size();i++){
                Log.d("name=",personlist.get(i).getName());
                String temp="";
                if(personlist.get(i).getAge()!=null){
                    temp=personlist.get(i).getAge().toString();
                }
                Log.d("age",""+temp);
                Log.d("teleno",personlist.get(i).getTeleno());
            }

        }else{
            toast(result.getMessage());
        }
    }

 /*  public void initPersons(){

    } */
}
