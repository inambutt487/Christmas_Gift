package com.darkpingouin.christmascountdown;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.InvalidMarkException;
import java.util.Calendar;

public class FullScreenActivity extends Activity {

    int status;
    int ctime;
    ImageView imageview, more, like, wall;
    AlertDialog.Builder alertDialogBuilder;

    Bitmap bmp;
    File file;
    File f = null;
    int[] pic = {R.drawable.theme_one, R.drawable.theme_two, R.drawable.theme_three, R.drawable.theme_four, R.drawable.theme_five, R.drawable.theme_six};
    String extStorageDirectory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_screen);
        Bundle bundle = getIntent().getExtras();
        status = bundle.getInt("theme");
        status = status - 1;
        imageview = (ImageView) findViewById(R.id.img);
        wall = (ImageView) findViewById(R.id.wall);
        more = (ImageView) findViewById(R.id.save_button);
        like = (ImageView) findViewById(R.id.share_button);
        AdView adView2 = (AdView) findViewById(R.id.adView2);
        adView2.loadAd(new AdRequest.Builder().build());
        BitmapDrawable drawable = (BitmapDrawable) getApplicationContext().getResources().getDrawable(pic[status]);
        bmp = drawable.getBitmap();
        imageview.setImageBitmap(bmp);
        wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder = new AlertDialog.Builder(FullScreenActivity.this);
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
                ctime = Calendar.getInstance().get(Calendar.MILLISECOND);
                extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                Bitmap bm = BitmapFactory.decodeResource( getResources(), pic[status]);
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
                    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Toast.makeText(FullScreenActivity.this, "Saved", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),pic[status]);
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/LatestShare.jpg";
                OutputStream out = null;
                File file=new File(path);
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                path=file.getPath();
                Uri bmpUri = Uri.parse("file://"+path);
                Intent shareIntent = new Intent();
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/jpg");
                startActivity(Intent.createChooser(shareIntent,"Share with"));
            }
        });
    }
}
