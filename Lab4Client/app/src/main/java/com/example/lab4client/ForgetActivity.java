package com.example.lab4client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mob.MobSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_submit;
    private Button btn_backtohome;
    private Button btn_check;
    private EditText et_phonenum;
    private EditText et_verifycode;
    private EditText et_uname;

    private TimerTask tt;
    private Timer tm;
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s,我们前端会有差异的建议设置90，100或者120
    public String country="86";//这是中国区号，如果需要其他国家列表，可以使用getSupportedCountries();获得国家区号

    private static final int CODE_REPEAT = 1; //重新发送

    private String username;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);
        initview();
        MobSDK.init(this,"31ccf10f093ac","7812649e56443b523066bfd0bd1c2469");//注册自己的
        SMSSDK.registerEventHandler(eh); //注册短信回调（记得销毁，避免泄露内存）

    }

    public void BackHome(View view){
        Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
        startActivity(intent);
    }


    public void initview(){
        btn_submit = findViewById(R.id.button7);
        btn_backtohome = findViewById(R.id.button8);
        btn_check = findViewById(R.id.check);
        et_phonenum = findViewById(R.id.phonenum);
        et_verifycode = findViewById(R.id.verifycode);
        et_uname=findViewById(R.id.uname1);
        btn_check.setOnClickListener((View.OnClickListener) this);
        btn_submit.setOnClickListener((View.OnClickListener) this);

    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ForgetPasswordRequest(final String username, final String teleno){
        //请求地址
        String myurl ="http://123.57.252.151:8080/Lab-2/ForgetPass";
        FakeX509TrustManager.allowAllSSL();//解决证书不信任问题
        OkHttpClient client = new OkHttpClient();//创建一个OkHttpClient实例
        RequestBody requestBody =new FormBody.Builder()//构建一个RequestBody 来存放待提交的参数
                .add("username",username)
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
                        Log.d("chenggong",response.toString());
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
            Looper.prepare();
            //通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ForgetActivity.this);
            //    设置Content来显示一个信息
            builder.setMessage("提交成功，已修改密码为888888，请重新登录。");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ForgetActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
            Looper.loop();
        }else{
            toast(result.getMessage());
        }
    }


    private Handler hd = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                btn_check.setEnabled(true);
                btn_submit.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                btn_check.setText("再次发送");
            }else {
                btn_check.setText(TIME + "s");
            }
            return true;
        }
    });

    //回调
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                   // toast("验证成功");
                    ForgetPasswordRequest(username,phone);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                  //  toast("获取验证码成功");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                ((Throwable)data).printStackTrace();
                String str = data.toString();
                //toast(str);
                toast("验证码错误!");
            }
        }
    };
    //弹窗确认下发
    private void alterWarning() {
        //构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
      //  builder.setTitle("Hint"); //设置标题
        builder.setMessage("确定给"+phone + "发送验证码？"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                //通过sdk发送短信验证（请求获取短信验证码，在监听（eh）中返回）
                SMSSDK.getVerificationCode(country, phone);
                //做倒计时操作
                Toast.makeText(ForgetActivity.this, "已发送", Toast.LENGTH_SHORT).show();
                btn_check.setEnabled(false);
                btn_submit.setEnabled(true);
                tm = new Timer();
                tt = new TimerTask() {
                    @Override
                    public void run() {
                        hd.sendEmptyMessage(TIME--);
                    }
                };
                tm.schedule(tt,0,1000);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ForgetActivity.this, "取消发送" , Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check:
                username =et_uname.getText().toString().trim();
                phone = et_phonenum.getText().toString().trim().replaceAll("/s","");
                if(!TextUtils.isEmpty(username)){//用户名不为空
                    if (!TextUtils.isEmpty(phone)) {//电话号码不为空
                        //定义需要匹配的正则表达式的规则
                        String REGEX_MOBILE_SIMPLE =  "[1][34578]\\d{9}";
                        //把正则表达式的规则编译成模板
                        Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                        //把需要匹配的字符给模板匹配，获得匹配器
                        Matcher matcher = pattern.matcher(phone);
                        // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                        if (matcher.find()) {//匹配手机号是否存在
                            alterWarning();

                        } else {
                            toast("电话号码格式不正确");
                        }
                    } else {
                        toast("电话号码不能为空");
                    }
                }else{
                    toast("用户名不能为空");
                }
                break;
            case R.id.button7:
                //获得用户输入的验证码
                String code = et_verifycode.getText().toString().replaceAll("/s","");
                if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
                    //验证
                    SMSSDK.submitVerificationCode( country,  phone,  code);
                }else{//如果用户输入的内容为空，提醒用户
                    toast("请输入验证码");
                }
                break;
        }
    }

    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);
    }

}
