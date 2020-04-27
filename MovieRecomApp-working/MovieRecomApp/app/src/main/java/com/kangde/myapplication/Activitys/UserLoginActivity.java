package com.kangde.myapplication.Activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kangde.myapplication.Bean.User;
import com.kangde.myapplication.R;
import com.kangde.myapplication.Util.L;
import com.kangde.myapplication.Util.SharedPreferencesUtil;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserLoginActivity extends AppCompatActivity {
    private EditText editText_id, editText_password;
    private Button Login, Register;
    public static final String TAG="UserLoginActivity";
    private SharedPreferencesUtil sp;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_login);

        editText_id = (EditText) findViewById(R.id.editText_Uid);
        editText_password = (EditText) findViewById(R.id.editText_Upassword);
        Login = (Button) findViewById(R.id.button_login);
        Register = (Button) findViewById(R.id.button_registerUsers);
        sp= SharedPreferencesUtil.getInstance(getApplicationContext());
        initView();
    }

    public void initView() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    public void Login() {
        if (isConnectingToInternet()) {
            if (editText_id.toString().isEmpty() || editText_password.toString().isEmpty()) {
                System.out.println("password cant be empty");
            } else {
                //开启访问数据库线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        User user = new User();
                        user.setUsername(editText_id.getText().toString());
                        user.setPassword(editText_password.getText().toString());

                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        String address = "http://192.168.1.11:9999/MovieAppServer//LoginServlet";

                        OkHttpClient client = new OkHttpClient();


                        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                                , json);
                        Request request = new Request.Builder().url(address).post(body).build();
                        try {
                            Response response = client.newCall(request).execute();
//                            String responseData = response.body().toString();


                            boolean responseData = Boolean.valueOf(response.body().string());
                            L.e("response ="+responseData);
                            String cookie = response.headers().get("Set-Cookie");
                            if (responseData) {
                                sp.setLogin(true);
                                sp.put("userName", user.getUsername());
//                                sp.put("password", user.getPassword());

                                // SharedPreferences.Editor editor =sp
                                startActivity(new Intent(UserLoginActivity.this,TestActivity.class));
                                Looper.prepare();
                                Toast.makeText(UserLoginActivity.this, "succeed", Toast.LENGTH_SHORT).show();
                                String usertest = sp.get("userName");
                                L.e(usertest);
                                Looper.loop();


                            } else {
                                L.e("status="+responseData);
                                Looper.prepare();
                                Toast.makeText(UserLoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                            Log.d(TAG, cookie);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//

                    }
                }).start();
            }
        } else {
            System.out.println("internet not connect");
        }

    }

    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

}
