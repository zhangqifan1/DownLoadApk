package com.example.anadministrator.downloadapk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    /**
     * 下载Apk
     */
    private Button mButDownLoad;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mButDownLoad = (Button) findViewById(R.id.butDownLoad);
        mButDownLoad.setOnClickListener(this);
        mProgress = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butDownLoad:
                mProgress.setVisibility(View.VISIBLE);
                DownLoad();
                break;
        }
    }

    private void DownLoad() {
        OkHttpUtils
                .get()
                .url("http://p.gdown.baidu.com/e96035c05cfbd104bc614338d32e4998cf247dcb6b1d2f55352c28c485a21dc66ba8b52319123d749562b790fc47aaaee5da5908708263ce0b1692be82a460934eed49f570b9c59411293bb6bbc5eaa6b5d33a12fa8942809ab5006d401340245313c56321cf811b955d8594032edf172207325d3d4fe11fd2a043066ab78e7def5069cf551eefddc1250aa4f4faf971cddb65081a3004655f51e0adfa56339673d241fd87d214181c62654fcf4ec14746c495a66bbafe057a887ec1a8741de6fec48d8ed0e75ab7e22a9538a70aeb0d590439066f50f5505137d99b62a87fa5")
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getPath(),"zqf.apk") {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                       mProgress.setProgress((int)(100*progress));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        String s = response.toString();
                        System.out.println("不知道有啥:"+s);
                        if (mProgress.getProgress()==mProgress.getMax()){
                            Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(response), "application/vnd.android.package-archive");
                            startActivity(intent);

                        }

                    }
                });
    }
}
