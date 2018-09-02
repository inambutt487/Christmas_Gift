package com.darkpingouin.christmascountdown;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tab5Fragment extends Fragment {
    ImageView gift_show;
    LinearLayout first, second;
    TextView style_text1,style_text2,style_text3,text4;
    ImageView app1, app2, app3, app4, app5, app6, app7, app8, app9;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab5_fragment, container, false);
        AdView adView2 = (AdView) view.findViewById(R.id.adView1);
        adView2.loadAd(new AdRequest.Builder().build());
        gift_show = (ImageView) view.findViewById(R.id.giftshow);
        first = (LinearLayout) view.findViewById(R.id.first6);
        second = (LinearLayout) view.findViewById(R.id.second6);
        text4 = (TextView) view.findViewById(R.id.text4);

        style_text1 = (TextView) view.findViewById(R.id.text1);
        style_text2 = (TextView) view.findViewById(R.id.text2);
        style_text3 = (TextView) view.findViewById(R.id.text3);
        app1 = (ImageView) view.findViewById(R.id.app1);
        app2 = (ImageView) view.findViewById(R.id.app2);
        app3 = (ImageView) view.findViewById(R.id.app3);
        app4 = (ImageView) view.findViewById(R.id.app4);
        app5 = (ImageView) view.findViewById(R.id.app5);
        app6 = (ImageView) view.findViewById(R.id.app6);
        app7 = (ImageView) view.findViewById(R.id.app7);
        app8 = (ImageView) view.findViewById(R.id.app8);
        app9 = (ImageView) view.findViewById(R.id.app9);

        Typeface blockFonts1 = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/SEGOESCB.TTF");
        style_text1.setTypeface(blockFonts1);
        Typeface blockFonts2 = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/VIVALDII.TTF");
        style_text2.setTypeface(blockFonts2);
        style_text3.setTypeface(blockFonts2);
        text4.setTypeface(blockFonts2);
        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        Date todayMonth = new Date();
        String thisMonth = currentMonth.format(todayMonth);
        SimpleDateFormat currentDay = new SimpleDateFormat("dd");
        Date todayDay = new Date();
        String thisDay = currentDay.format(todayDay);
        if(thisMonth.matches("12")){
            if(thisDay.matches("22")){
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.GONE);
                gift_show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext(),GiftTuneActivity.class);
                        intent.putExtra("data",22);
                        startActivity(intent);
                    }
                });
            } else {
                first.setVisibility(View.GONE);
                second.setVisibility(View.VISIBLE);
            }
        } else {
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
        }
        app1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.jalsawasiapps.super.private.vpn")));
            }
        });
        app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.utilitiesapp.independence.dpmaker.profilephoto")));
            }
        });

        app3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.dk.xmas.themes.holiday.tree.santa.gifts.countdown")));
            }
        });

        app4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.freeappscorner.flashalerts.ringingflashlight.flashoncallsms")));
            }
        });

        app5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.fac.file.manager.trasfer.share.cleanup.organizer.explore")));
            }
        });

        app6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.jalsawasiapps.find.mylostphone.mobile.tracker")));
            }
        });

        app7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.jalsawasiapps.music.player.musicbox")));
            }
        });
        app8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.jalsawasiapps.gps.driving.route.tracking.live.map.navigation")));
            }
        });
        app9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + "com.jalsawasi.nightmodeenabler.bluelightfilter")));
            }
        });


        return view;
    }



}
