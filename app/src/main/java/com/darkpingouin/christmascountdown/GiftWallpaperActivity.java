package com.darkpingouin.christmascountdown;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class GiftWallpaperActivity extends AppCompatActivity {

    int ctime;
    ImageView imageView, more, like, wall;
    AlertDialog.Builder alertDialogBuilder;
    File file;
    File f = null;
    String extStorageDirectory;
    String img_url = null;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_wallpaper);
        imageView = (ImageView) findViewById(R.id.img2);
        wall = (ImageView) findViewById(R.id.wall2);
        more = (ImageView) findViewById(R.id.save_button2);
        like = (ImageView) findViewById(R.id.share_button2);
        AdView adView2 = (AdView) findViewById(R.id.adView4);
        adView2.loadAd(new AdRequest.Builder().build());
        Bundle bundle = getIntent().getExtras();
        int data = bundle.getInt("data");
        if (isNetworkAvailable()) {
            if (data == 18) {
                img_url = "https://jalsawasi-apps.000webhostapp.com/hdwallpaper/02.png";
            }
            if (data == 19) {
                img_url = "https://jalsawasi-apps.000webhostapp.com/hdwallpaper/03.png";
            }
            if (data == 21) {
                img_url = "https://jalsawasi-apps.000webhostapp.com/hdwallpaper/04.png";
            }
            if (data == 23) {
                img_url = "https://jalsawasi-apps.000webhostapp.com/hdwallpaper/05.png";
            }
            if (data == 25) {
                img_url = "https://jalsawasi-apps.000webhostapp.com/hdwallpaper/06.png";
            }
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            dialog.show();
            loadImage(getApplicationContext(), img_url);
        } else

        {
            android.support.v7.app.AlertDialog.Builder ad1 = new android.support.v7.app.AlertDialog.Builder(this);
            ad1.setMessage("Check Your Internet Connection");
            ad1.setCancelable(false);
            ad1.setTitle("Alert!!!");

            ad1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog alert = ad1.create();
            alert.show();
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Target mTarget;

    void loadImage(final Context context, String url) {
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bmp, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bmp);
                dialog.dismiss();
                wall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogBuilder = new AlertDialog.Builder(GiftWallpaperActivity.this);
                        alertDialogBuilder.setMessage("You wanted to set wallpaper!");
                        alertDialogBuilder.setIcon(R.drawable.icon);
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                        DisplayMetrics metrics = new DisplayMetrics();
                                        getWindowManager().getDefaultDisplay()
                                                .getMetrics(metrics);

                                        int height = metrics.heightPixels;
                                        int width = metrics.widthPixels;
                                        Bitmap bitmapResized = Bitmap.createScaledBitmap(bmp, width,
                                                height, true);
                                        myWallpaperManager.suggestDesiredDimensions(
                                                bitmapResized.getWidth(), bitmapResized.getHeight());

                                        try {
                                            myWallpaperManager.setBitmap(bitmapResized);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                });
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogBuilder = new AlertDialog.Builder(GiftWallpaperActivity.this);
                        alertDialogBuilder.setMessage("You wanted to Save wallpaper!");
                        alertDialogBuilder.setIcon(R.drawable.icon);
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        ctime = Calendar.getInstance().get(Calendar.MILLISECOND);
                                        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                                        OutputStream outStream = null;
                                        file = new File(
                                                android.os.Environment.getExternalStorageDirectory(),
                                                "HD Wallpapers");
                                        if (!file.exists()) {
                                            file.mkdirs();

                                        }
                                        f = new File(file.getAbsolutePath() + file.separator + "frm"
                                                + ctime + ".jpg");

                                        try {
                                            outStream = new FileOutputStream(f);
                                            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                                            outStream.flush();
                                            outStream.close();
                                            Toast.makeText(context, "Image is Saved", Toast.LENGTH_SHORT).show();

                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                });

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/LatestShare.jpg";
                        OutputStream out = null;
                        File file = new File(path);
                        try {
                            out = new FileOutputStream(file);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        path = file.getPath();
                        Uri bmpUri = Uri.parse("file://" + path);
                        Intent shareIntent;
                        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/jpg");
                        startActivity(Intent.createChooser(shareIntent, "Share with"));
                    }
                });

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(getApplicationContext())
                .load(url)
                .into(mTarget);
    }
}