package com.darkpingouin.christmascountdown;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ChristmisApp extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    int j=0;
    static long days, hours, minutes, seconds;
    static  Date currentDate;
    static Date futureDate;
    int a = 1;
    ImageView snowgif;
    MediaPlayer SoundMediaPlayer;
    int snd = 1;
    private Handler handler;
    private Runnable runnable;
    int size = 0;
    ImageView gift;
    InterstitialAd mInterstitialAd;
    List<Long> Days = new ArrayList<Long>();
    List<Long> Hours = new ArrayList<Long>();
    List<Long> Minutes = new ArrayList<Long>();
    List<Long> Seconds = new ArrayList<Long>();
    DrawerLayout drawer;
    ImageView fabIconNew;
    //    ImageView animationgif;
    TranslateAnimation animation;
    ImageView digit_one, digit_two, digit_three, h_digit_one, h_digit_two, m_digit_one, m_digit_two, s_digit_one, s_digit_two;
    ImageView menu_buton;
    public static int count = 0;
    ImageView sond, wall;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.SET_WALLPAPER, Manifest.permission.SET_WALLPAPER_HINTS,
            Manifest.permission.CHANGE_CONFIGURATION,Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE};
//    final private ChristmisApp.UnityAdsListener unityAdsListener = new ChristmisApp.UnityAdsListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.drawer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                // Do stuff here
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" +  getPackageName()));
                startActivity(intent);
            }
        }
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
            Days.clear();
            Hours.clear();
            Minutes.clear();
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3444255945927869/8422810034");
            requestNewInterstitial();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    switch (j) {
                        case 0:
                            break;
                        case 1:
                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                            intent.putExtra("days",days);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(ChristmisApp.this, WallpaperActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            snowgif.setVisibility(View.VISIBLE);
                            break;
                    }
                    requestNewInterstitial();

                }
            });
            digit_one = (ImageView) findViewById(R.id.day1);
            digit_two = (ImageView) findViewById(R.id.day2);
            digit_three = (ImageView) findViewById(R.id.day3);
            h_digit_one = (ImageView) findViewById(R.id.hour1);
            h_digit_two = (ImageView) findViewById(R.id.hour2);
            m_digit_one = (ImageView) findViewById(R.id.minute1);
            m_digit_two = (ImageView) findViewById(R.id.minute2);
            s_digit_one = (ImageView) findViewById(R.id.second1);
            s_digit_two = (ImageView) findViewById(R.id.second2);
            gift = (ImageView) findViewById(R.id.gift_chatt);
            sond = (ImageView) findViewById(R.id.sound);
            snowgif = (ImageView) findViewById(R.id.snowgifile);
            wall = (ImageView) findViewById(R.id.theeme);
            menu_buton = (ImageView) findViewById(R.id.menu);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
            SoundMediaPlayer.start();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setItemIconTintList(null);
            navigationView.setNavigationItemSelectedListener(this);
//        animationgif = (ImageView) findViewById(R.id.animation);
            fabIconNew = new ImageView(this);

            FloatingActionButton.LayoutParams layoutParams = new FloatingActionButton.LayoutParams(150, 150);
            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings));
            final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                    .setTheme(FloatingActionButton.THEME_DARK)
                    .setLayoutParams(layoutParams)
                    .setPosition(FloatingActionButton.POSITION_BOTTOM_LEFT)
                    .setBackgroundDrawable(R.drawable.button_action_red)
                    .setContentView(fabIconNew)
                    .build();

            SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
            ImageView rlIcon1 = new ImageView(this);
            ImageView rlIcon2 = new ImageView(this);
            ImageView rlIcon3 = new ImageView(this);
            ImageView rlIcon4 = new ImageView(this);


            rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.rate));
            rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.more));
            rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.share));
            rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.exit));

            // Build the menu with default options: light theme, 90 degrees, 72dp radius.
            // Set 4 default SubActionButtons
            final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon1).setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_red)).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                    .setStartAngle(-0)
                    .setEndAngle(-90)
                    .attachTo(rightLowerButton)
                    .build();
            rlIcon1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + "com.utilitiesapps.faceflag.independance")));
                }
            });
            rlIcon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Free%20Apps%20Corner&hl=en"));
                    startActivity(i);
                }
            });
            rlIcon3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Christmas Wallpaper: Xmas Countdown, Santa Gifts");
                        String sAux = "\nLet me recommend you this application\n\n";
                        sAux = sAux + "com.utilitiesapps.faceflag.independance \n\n";
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(i, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }
                }
            });
            rlIcon4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            // Listen menu open and close events to animate the button content view
            rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                @Override
                public void onMenuOpened(FloatingActionMenu menu) {
                    // Rotate the icon of rightLowerButton 45 degrees clockwise
                    fabIconNew.setRotation(0);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                    animation.start();
                }

                

                @Override
                public void onMenuClosed(FloatingActionMenu menu) {
                    // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                    fabIconNew.setRotation(45);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                    animation.start();
                }
            });
            countDownStart();
            wall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    j=2;
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Intent intent = new Intent(ChristmisApp.this, WallpaperActivity.class);
                        startActivity(intent);
                    }
                }
            });
            sond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snd = snd + 1;
                    if (snd == 1) {
                        try {
                            SoundMediaPlayer.reset();
                            SoundMediaPlayer.prepare();
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.release();
                            SoundMediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
                        SoundMediaPlayer.start();
                    } else if (snd == 2) {
                        try {
                            SoundMediaPlayer.reset();
                            SoundMediaPlayer.prepare();
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.release();
                            SoundMediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_two);
                        SoundMediaPlayer.start();
                    } else if (snd == 3) {
                        try {
                            SoundMediaPlayer.reset();
                            SoundMediaPlayer.prepare();
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.release();
                            SoundMediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_three);
                        SoundMediaPlayer.start();
                    } else if (snd == 4) {
                        try {
                            SoundMediaPlayer.reset();
                            SoundMediaPlayer.prepare();
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.release();
                            SoundMediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (SoundMediaPlayer.isPlaying()) {
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.reset();
                        }
                    } else {
                        snd = 1;
                        try {
                            SoundMediaPlayer.reset();
                            SoundMediaPlayer.prepare();
                            SoundMediaPlayer.stop();
                            SoundMediaPlayer.release();
                            SoundMediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
                        SoundMediaPlayer.start();
                    }
                }
            });
            gift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    j=1;
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent);
                    }
                }
            });
        }


    private void requestNewInterstitial() {
        // TODO Auto-generated method stub
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    menu_buton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            drawer.openDrawer(Gravity.LEFT);
                        }
                    });


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    currentDate = new Date();
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    String xmas = year + "-12-25";
                    futureDate = dateFormat.parse(xmas);
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        seconds = diff / 1000;

                        while (days > 0) {
                            Days.add(days % 10);
                            days /= 10;
                        }
                        Collections.reverse(Days);

                        while (hours > 0) {
                            Hours.add(hours % 10);
                            hours /= 10;
                        }
                        Collections.reverse(Hours);

                        minutes = minutes % 100;
                        while (minutes > 0) {
                            Minutes.add(minutes % 10);
                            minutes /= 10;
                        }
                        Collections.reverse(Minutes);

                        while (seconds > 0) {
                            Seconds.add(seconds % 10);
                            seconds /= 10;
                        }
                        Collections.reverse(Seconds);


                        for (int i = Days.size() - 1; i >= 0; i--) {
                            size = Days.size();
                            if (size == 3) {
                                digit_three.setVisibility(View.VISIBLE);
                                digit_two.setVisibility(View.VISIBLE);
                                digit_one.setVisibility(View.VISIBLE);
                                if (i == 2) {
                                    if (Days.get(i) == 1) {
                                        digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_one.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 1) {
                                    if (Days.get(i) == 1) {
                                        digit_two.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_two.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_two.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_two.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_two.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_two.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_two.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_two.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_two.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_two.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 0) {
                                    if (Days.get(i) == 1) {
                                        digit_three.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_three.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_three.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_three.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_three.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_three.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_three.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_three.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_three.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_three.setImageResource(R.drawable.zero);
                                    }
                                    Days.clear();
                                    size = 0;
                                }
                            } else if (size == 2) {
                                digit_three.setVisibility(View.VISIBLE);
                                digit_one.setVisibility(View.VISIBLE);
                                digit_two.setVisibility(View.GONE);
                                if (i == 1) {
                                    if (Days.get(i) == 1) {
                                        digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_one.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 0) {
                                    if (Days.get(i) == 1) {
                                        digit_three.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_three.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_three.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_three.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_three.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_three.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_three.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_three.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_three.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_three.setImageResource(R.drawable.zero);
                                    }
                                    Days.clear();
                                    size = 0;
                                }
                            } else {
                                digit_one.setVisibility(View.GONE);
                                digit_three.setVisibility(View.GONE);
                                digit_two.setVisibility(View.VISIBLE);
                                if (i == 0) {
                                    if (Days.get(i) == 1) {
                                        digit_two.setImageResource(R.drawable.one);
                                    }
                                    if (Days.get(i) == 2) {
                                        digit_two.setImageResource(R.drawable.two);
                                    }
                                    if (Days.get(i) == 3) {
                                        digit_two.setImageResource(R.drawable.three);
                                    }
                                    if (Days.get(i) == 4) {
                                        digit_two.setImageResource(R.drawable.four);
                                    }
                                    if (Days.get(i) == 5) {
                                        digit_two.setImageResource(R.drawable.five);
                                    }
                                    if (Days.get(i) == 6) {
                                        digit_two.setImageResource(R.drawable.six);
                                    }
                                    if (Days.get(i) == 7) {
                                        digit_two.setImageResource(R.drawable.seven);
                                    }
                                    if (Days.get(i) == 8) {
                                        digit_two.setImageResource(R.drawable.eight);
                                    }
                                    if (Days.get(i) == 9) {
                                        digit_two.setImageResource(R.drawable.nine);
                                    }
                                    if (Days.get(i) == 0) {
                                        digit_two.setImageResource(R.drawable.zero);
                                    }
                                    Days.clear();
                                    size = 0;
                                }
                            }
                        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        for (int i = Hours.size() - 1; i >= 0; i--) {
                            size = Hours.size();
                            if (size == 2) {
                                h_digit_one.setVisibility(View.VISIBLE);
                                h_digit_two.setVisibility(View.VISIBLE);
                                if (i == 1) {
                                    if (Hours.get(i) == 1) {
                                        h_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Hours.get(i) == 2) {
                                        h_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Hours.get(i) == 3) {
                                        h_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Hours.get(i) == 4) {
                                        h_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Hours.get(i) == 5) {
                                        h_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Hours.get(i) == 6) {
                                        h_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Hours.get(i) == 7) {
                                        h_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Hours.get(i) == 8) {
                                        h_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Hours.get(i) == 9) {
                                        h_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Hours.get(i) == 0) {
                                        h_digit_one.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 0) {
                                    if (Hours.get(i) == 1) {
                                        h_digit_two.setImageResource(R.drawable.one);
                                    }
                                    if (Hours.get(i) == 2) {
                                        h_digit_two.setImageResource(R.drawable.two);
                                    }
                                    if (Hours.get(i) == 3) {
                                        h_digit_two.setImageResource(R.drawable.three);
                                    }
                                    if (Hours.get(i) == 4) {
                                        h_digit_two.setImageResource(R.drawable.four);
                                    }
                                    if (Hours.get(i) == 5) {
                                        h_digit_two.setImageResource(R.drawable.five);
                                    }
                                    if (Hours.get(i) == 6) {
                                        h_digit_two.setImageResource(R.drawable.six);
                                    }
                                    if (Hours.get(i) == 7) {
                                        h_digit_two.setImageResource(R.drawable.seven);
                                    }
                                    if (Hours.get(i) == 8) {
                                        h_digit_two.setImageResource(R.drawable.eight);
                                    }
                                    if (Hours.get(i) == 9) {
                                        h_digit_two.setImageResource(R.drawable.nine);
                                    }
                                    if (Hours.get(i) == 0) {
                                        h_digit_two.setImageResource(R.drawable.zero);
                                    }
                                    Hours.clear();
                                    size = 0;
                                }
                            }
                            if (size == 1) {
                                h_digit_two.setVisibility(View.INVISIBLE);
                                if (i == 0) {
                                    if (Hours.get(i) == 1) {
                                        h_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Hours.get(i) == 2) {
                                        h_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Hours.get(i) == 3) {
                                        h_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Hours.get(i) == 4) {
                                        h_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Hours.get(i) == 5) {
                                        h_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Hours.get(i) == 6) {
                                        h_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Hours.get(i) == 7) {
                                        h_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Hours.get(i) == 8) {
                                        h_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Hours.get(i) == 9) {
                                        h_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Hours.get(i) == 0) {
                                        h_digit_one.setImageResource(R.drawable.zero);
                                    }
                                    Hours.clear();
                                    size = 0;
                                }
                            }
                        }
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        for (int i = Minutes.size() - 1; i >= 0; i--) {
                            size = Minutes.size();
                            if (size == 2) {
                                m_digit_one.setVisibility(View.VISIBLE);
                                m_digit_two.setVisibility(View.VISIBLE);
                                if (i == 1) {
                                    if (Minutes.get(i) == 1) {
                                        m_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Minutes.get(i) == 2) {
                                        m_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Minutes.get(i) == 3) {
                                        m_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Minutes.get(i) == 4) {
                                        m_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Minutes.get(i) == 5) {
                                        m_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Minutes.get(i) == 6) {
                                        m_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Minutes.get(i) == 7) {
                                        m_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Minutes.get(i) == 8) {
                                        m_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Minutes.get(i) == 9) {
                                        m_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Minutes.get(i) == 0) {
                                        m_digit_one.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 0) {
                                    if (Minutes.get(i) == 1) {
                                        m_digit_two.setImageResource(R.drawable.one);
                                    }
                                    if (Minutes.get(i) == 2) {
                                        m_digit_two.setImageResource(R.drawable.two);
                                    }
                                    if (Minutes.get(i) == 3) {
                                        m_digit_two.setImageResource(R.drawable.three);
                                    }
                                    if (Minutes.get(i) == 4) {
                                        m_digit_two.setImageResource(R.drawable.four);
                                    }
                                    if (Minutes.get(i) == 5) {
                                        m_digit_two.setImageResource(R.drawable.five);
                                    }
                                    if (Minutes.get(i) == 6) {
                                        m_digit_two.setImageResource(R.drawable.six);
                                    }
                                    if (Minutes.get(i) == 7) {
                                        m_digit_two.setImageResource(R.drawable.seven);
                                    }
                                    if (Minutes.get(i) == 8) {
                                        m_digit_two.setImageResource(R.drawable.eight);
                                    }
                                    if (Minutes.get(i) == 9) {
                                        m_digit_two.setImageResource(R.drawable.nine);
                                    }
                                    if (Minutes.get(i) == 0) {
                                        m_digit_two.setImageResource(R.drawable.zero);
                                    }
                                    Minutes.clear();
                                    size = 0;
                                }
                            }
                            if (size == 1) {
                                m_digit_two.setVisibility(View.INVISIBLE);
                                if (i == 0) {
                                    if (Minutes.get(i) == 1) {
                                        m_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Minutes.get(i) == 2) {
                                        m_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Minutes.get(i) == 3) {
                                        m_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Minutes.get(i) == 4) {
                                        m_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Minutes.get(i) == 5) {
                                        m_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Minutes.get(i) == 6) {
                                        m_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Minutes.get(i) == 7) {
                                        m_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Minutes.get(i) == 8) {
                                        m_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Minutes.get(i) == 9) {
                                        m_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Minutes.get(i) == 0) {
                                        m_digit_one.setImageResource(R.drawable.zero);
                                    }
                                    Minutes.clear();
                                    size = 0;
                                }
                            }
                        }
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        for (int i = Seconds.size() - 1; i >= 0; i--) {
                            size = Seconds.size();
                            if (size == 2) {
                                s_digit_one.setVisibility(View.VISIBLE);
                                s_digit_two.setVisibility(View.VISIBLE);
                                if (i == 1) {
                                    if (Seconds.get(i) == 1) {
                                        s_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Seconds.get(i) == 2) {
                                        s_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Seconds.get(i) == 3) {
                                        s_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Seconds.get(i) == 4) {
                                        s_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Seconds.get(i) == 5) {
                                        s_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Seconds.get(i) == 6) {
                                        s_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Seconds.get(i) == 7) {
                                        s_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Seconds.get(i) == 8) {
                                        s_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Seconds.get(i) == 9) {
                                        s_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Seconds.get(i) == 0) {
                                        s_digit_one.setImageResource(R.drawable.zero);
                                    }
                                }
                                if (i == 0) {
                                    if (Seconds.get(i) == 1) {
                                        s_digit_two.setImageResource(R.drawable.one);
                                    }
                                    if (Seconds.get(i) == 2) {
                                        s_digit_two.setImageResource(R.drawable.two);
                                    }
                                    if (Seconds.get(i) == 3) {
                                        s_digit_two.setImageResource(R.drawable.three);
                                    }
                                    if (Seconds.get(i) == 4) {
                                        s_digit_two.setImageResource(R.drawable.four);
                                    }
                                    if (Seconds.get(i) == 5) {
                                        s_digit_two.setImageResource(R.drawable.five);
                                    }
                                    if (Seconds.get(i) == 6) {
                                        s_digit_two.setImageResource(R.drawable.six);
                                    }
                                    if (Seconds.get(i) == 7) {
                                        s_digit_two.setImageResource(R.drawable.seven);
                                    }
                                    if (Seconds.get(i) == 8) {
                                        s_digit_two.setImageResource(R.drawable.eight);
                                    }
                                    if (Seconds.get(i) == 9) {
                                        s_digit_two.setImageResource(R.drawable.nine);
                                    }
                                    if (Seconds.get(i) == 0) {
                                        s_digit_two.setImageResource(R.drawable.zero);
                                    }
                                    Seconds.clear();
                                    size = 0;
                                }
                            }
                            if (size == 1) {
                                s_digit_two.setVisibility(View.INVISIBLE);
                                if (i == 0) {
                                    if (Seconds.get(i) == 1) {
                                        s_digit_one.setImageResource(R.drawable.one);
                                    }
                                    if (Seconds.get(i) == 2) {
                                        s_digit_one.setImageResource(R.drawable.two);
                                    }
                                    if (Seconds.get(i) == 3) {
                                        s_digit_one.setImageResource(R.drawable.three);
                                    }
                                    if (Seconds.get(i) == 4) {
                                        s_digit_one.setImageResource(R.drawable.four);
                                    }
                                    if (Seconds.get(i) == 5) {
                                        s_digit_one.setImageResource(R.drawable.five);
                                    }
                                    if (Seconds.get(i) == 6) {
                                        s_digit_one.setImageResource(R.drawable.six);
                                    }
                                    if (Seconds.get(i) == 7) {
                                        s_digit_one.setImageResource(R.drawable.seven);
                                    }
                                    if (Seconds.get(i) == 8) {
                                        s_digit_one.setImageResource(R.drawable.eight);
                                    }
                                    if (Seconds.get(i) == 9) {
                                        s_digit_one.setImageResource(R.drawable.nine);
                                    }
                                    if (Seconds.get(i) == 0) {
                                        s_digit_one.setImageResource(R.drawable.zero);
                                    }
                                    Seconds.clear();
                                    size = 0;
                                }
                            }
                        }
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    @Override
    public void onBackPressed() {
//        if (UnityAds.isReady()) {
//            UnityAds.show(ChristmisApp.this, "video");
//
//        }
        AlertDialog.Builder ad1 = new AlertDialog.Builder(this);
        ad1.setMessage(R.string.dailog);


        ad1.setCancelable(false);
        ad1.setTitle("Thanks for using XMAS HD Photo Frames");

        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        ad1.setNeutralButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
                finish();
            }
        });

        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.utilitiesapps.faceflag.independance")));
            }
        });
        AlertDialog alert = ad1.create();
        alert.setIcon(R.drawable.icon);
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SoundMediaPlayer.isPlaying()) {
            SoundMediaPlayer.stop();
            SoundMediaPlayer.reset();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (SoundMediaPlayer.isPlaying()) {
            SoundMediaPlayer.stop();
            SoundMediaPlayer.reset();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.wallpaper) {
            j=2;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Intent intent = new Intent(ChristmisApp.this, WallpaperActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.sound) {
            snd = snd + 1;
            if (snd == 1) {
                try {
                    SoundMediaPlayer.reset();
                    SoundMediaPlayer.prepare();
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.release();
                    SoundMediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
                SoundMediaPlayer.start();
            } else if (snd == 2) {
                try {
                    SoundMediaPlayer.reset();
                    SoundMediaPlayer.prepare();
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.release();
                    SoundMediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_two);
                SoundMediaPlayer.start();
            } else if (snd == 3) {
                try {
                    SoundMediaPlayer.reset();
                    SoundMediaPlayer.prepare();
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.release();
                    SoundMediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_three);
                SoundMediaPlayer.start();
            } else if (snd == 4) {
                try {
                    SoundMediaPlayer.reset();
                    SoundMediaPlayer.prepare();
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.release();
                    SoundMediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (SoundMediaPlayer.isPlaying()) {
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.reset();
                }
            } else {
                snd = 1;
                try {
                    SoundMediaPlayer.reset();
                    SoundMediaPlayer.prepare();
                    SoundMediaPlayer.stop();
                    SoundMediaPlayer.release();
                    SoundMediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
                SoundMediaPlayer.start();
            }
        } else if (id == R.id.gift) {
            j=1;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        } else if (id == R.id.snow) {
            j=3;
            if (a == 1) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    snowgif.setVisibility(View.VISIBLE);
                }
                a = 0;
            } else {
                snowgif.setVisibility(View.GONE);
                a = 1;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
