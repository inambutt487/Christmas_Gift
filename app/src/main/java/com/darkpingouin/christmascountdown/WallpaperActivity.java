package com.darkpingouin.christmascountdown;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class WallpaperActivity extends Activity {

    ImageView t1,t2,t3,t4,t5,t6;
    Intent i;
    int code=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        t1 = (ImageView) findViewById(R.id.theeme1);
        t2 = (ImageView) findViewById(R.id.theeme2);
        t3 = (ImageView) findViewById(R.id.theeme3);
        t4 = (ImageView) findViewById(R.id.theeme4);
        t5 = (ImageView) findViewById(R.id.theeme5);
        t6 = (ImageView) findViewById(R.id.theeme6);
        AdView adView1 = (AdView) findViewById(R.id.adView1);
        adView1.loadAd(new AdRequest.Builder().build());
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=1;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=2;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=3;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=4;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=5;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=6;
                i = new Intent(WallpaperActivity.this, FullScreenActivity.class);
                i.putExtra("theme", code);
                startActivity(i);
            }
        });
    }
}
