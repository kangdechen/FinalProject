package com.kangde.myapplication.Activitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kangde.myapplication.Bean.Recommendation;
import com.kangde.myapplication.Bean.UserPreference;
import com.kangde.myapplication.R;
import com.kangde.myapplication.Util.L;
import com.kangde.myapplication.Util.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecDetailActivity extends AppCompatActivity {

    private TextView uploader;
    private TextView comment;
    private TextView movieName;

    private Recommendation rec;
    private ImageView img;
    private Context context;
    private Button like;
    private Button dislike;
    private SharedPreferencesUtil sp;
    private MaterialRatingBar rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_detail);

        this.context = context;
        sp= SharedPreferencesUtil.getInstance(getApplicationContext());

        uploader = (TextView) findViewById(R.id.textView_Uploader);
        comment = (TextView) findViewById(R.id.TextView_comment);
        movieName = (TextView) findViewById(R.id.TextView_movie);
        rb = (MaterialRatingBar) findViewById(R.id.SRatingBar);
        like = (Button) findViewById(R.id.button_like);
        dislike = (Button) findViewById(R.id.button_dislike);

        rec = (Recommendation) getIntent().getSerializableExtra("rec");
        img = (ImageView) findViewById(R.id.image_view);


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeOrDislike(1);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeOrDislike(-1);
            }
        });
        viewDetails();

    }

    public void viewDetails() {
        uploader.setText(rec.getUsername());
        comment.setText(rec.getComment());
        movieName.setText(rec.getMovieName());
        rb.setProgress((int) rec.getRating());

        String pic = rec.getPic();
        //.with(context).load(url).into(img);
        //LoadImage.Download();
        Download(pic);
    }

    private void Download(String picUrl) {


        OkHttpClient okHttpClient = new OkHttpClient();


        Request.Builder builder = new Request.Builder();
        String pic = picUrl;
        Request request = builder.url("http://192.168.1.11:9999/MovieAppServer/DownLoadServlet?filename=" + pic)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                L.e("onFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                //将输入流数据转化为Bitmap位图数据
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                final File file = new File("/mnt/sdcard/picture.jpg");
                file.createNewFile();
                //创建文件输出流对象用来向文件中写入数据
                FileOutputStream out = new FileOutputStream(file);
                //将bitmap存储为jpg格式的图片
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                //刷新文件流
                out.flush();
                out.close();
                final Message msg = Message.obtain();
                msg.obj = bitmap;

                // Glide.with(this).load(url).into(mImageView);
                // img.setImageBitmap(file.);
                runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);


                    }
                }));


            }
        });

    }

    private void likeOrDislike(int rate) {

        final UserPreference up = new UserPreference();
        up.setRate(rate);
        up.setMovieName(rec.getMovieName());
        up.setUploader(rec.getUsername());
        up.setUserName(sp.get("userName"));
        if (isConnectingToInternet()) {
             {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Gson gson = new Gson();
                        String json = gson.toJson(up);
                        String address = "http://192.168.1.11:9999/MovieAppServer/UserPreferenceServlet";


                        //HttpConnection.sendOkHttpRequest(address, user, new okhttp3.Callback(){

                        OkHttpClient client = new OkHttpClient();
//

                        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                                , json);
                        Request request = new Request.Builder()
                                .url(address)
                                .post(requestBody)
                                .build();

                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(MainActivity.TAG, "connectionfaill");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                boolean responseData = Boolean.valueOf(response.body().string());
                                L.e("响应信息： " + responseData);
                                L.e("res code" + response.code());
                                //  String cookie = response.headers().get("Set-Cookie");

                                // Log.d(TAG, cookie);
                                L.e("res data" + responseData);
                                if (responseData) {

                                    Looper.prepare();
                                    Toast.makeText(RecDetailActivity.this, "succeed", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
//                                 else
//                                 {
//                                     startActivity(new Intent(RegActivity.this,MainActivity.class));
//                                 }


                            }
                        });

                    }
                }).start();
            }
        } else {
            System.out.println("网络未连接");
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