package com.darkpingouin.christmascountdown;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class GiftTuneActivity extends AppCompatActivity {

    AlertDialog.Builder alertDialogBuilder;
    ImageView imageView, like, wall;
    String extStorageDirectory;
    File file;
    File f = null;
    File g = null;
    Boolean i = true;
    OutputStream out;
    MediaPlayer SoundMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_tune);
        imageView = (ImageView) findViewById(R.id.img3);
        imageView.setBackgroundResource(R.drawable.pause);
        wall = (ImageView) findViewById(R.id.wall3);
        like = (ImageView) findViewById(R.id.share_button3);
        AdView adView2 = (AdView) findViewById(R.id.adView5);
        adView2.loadAd(new AdRequest.Builder().build());
        Bundle bundle = getIntent().getExtras();
        int data = bundle.getInt("data");

        if (data == 20) {
            file = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "TUNES");
            if (!file.exists()) {
                file.mkdirs();

            }
            SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
            SoundMediaPlayer.start();
            SoundMediaPlayer.setLooping(true);
            File dest = Environment.getExternalStorageDirectory();
            InputStream in = this.getResources().openRawResource(R.raw.sound_one);

            f = new File(file.getAbsolutePath() + dest.separator + "tune1" + ".mp3");
            try {
                out = new FileOutputStream(f);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
            }

            wall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogBuilder = new AlertDialog.Builder(GiftTuneActivity.this);
                    alertDialogBuilder.setMessage("You wanted to set this Ringtune!");
                    alertDialogBuilder.setIcon(R.drawable.icon);
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
                                    values.put(MediaStore.MediaColumns.TITLE, "Ringtune1");
                                    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
                                    values.put(MediaStore.MediaColumns.SIZE, f.length());
                                    values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
                                    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                                    values.put(MediaStore.Audio.Media.IS_ALARM, true);
                                    values.put(MediaStore.Audio.Media.IS_MUSIC, false);

                                    Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
                                            .getAbsolutePath());
                                    getContentResolver().delete(
                                            uri,
                                            MediaStore.MediaColumns.DATA + "=\""
                                                    + f.getAbsolutePath() + "\"", null);
                                    Uri newUri = getContentResolver().insert(uri, values);

                                    try {
                                        RingtoneManager.setActualDefaultRingtoneUri(
                                                GiftTuneActivity.this, RingtoneManager.TYPE_RINGTONE,
                                                newUri);
                                    } catch (Throwable t) {
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
                    Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.setType("audio/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(share, "Share audio File"));

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i) {
                        try {
                            SoundMediaPlayer.setLooping(false);
                            SoundMediaPlayer.stop();
                            imageView.setBackgroundResource(R.drawable.play);
                            i = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        i = true;
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_one);
                        SoundMediaPlayer.start();
                        SoundMediaPlayer.setLooping(true);
                        imageView.setBackgroundResource(R.drawable.pause);
                    }

                }
            });

        }
        if (data == 22) {
            file = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "TUNES");
            if (!file.exists()) {
                file.mkdirs();

            }
            File dest = Environment.getExternalStorageDirectory();
            InputStream in = this.getResources().openRawResource(R.raw.sound_two);
            SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_two);
            SoundMediaPlayer.start();
            SoundMediaPlayer.setLooping(true);
            f = new File(file.getAbsolutePath() + dest.separator + "tune2" + ".mp3");
            try {
                out = new FileOutputStream(f);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
            }

            wall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
                    values.put(MediaStore.MediaColumns.TITLE, "Ringtune2");
                    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
                    values.put(MediaStore.MediaColumns.SIZE, f.length());
                    values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
                    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                    values.put(MediaStore.Audio.Media.IS_ALARM, true);
                    values.put(MediaStore.Audio.Media.IS_MUSIC, false);

                    Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
                            .getAbsolutePath());
                    getContentResolver().delete(
                            uri,
                            MediaStore.MediaColumns.DATA + "=\""
                                    + f.getAbsolutePath() + "\"", null);
                    Uri newUri = getContentResolver().insert(uri, values);

                    try {
                        RingtoneManager.setActualDefaultRingtoneUri(
                                GiftTuneActivity.this, RingtoneManager.TYPE_RINGTONE,
                                newUri);
                    } catch (Throwable t) {
                    }

                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.setType("audio/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(share, "Share audio File"));

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i) {
                        try {
                            SoundMediaPlayer.setLooping(false);
                            SoundMediaPlayer.stop();
                            imageView.setBackgroundResource(R.drawable.play);
                            i = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        i = true;
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_two);
                        SoundMediaPlayer.start();
                        SoundMediaPlayer.setLooping(true);
                        imageView.setBackgroundResource(R.drawable.pause);
                    }

                }
            });

        }
        if (data == 24) {
            file = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "TUNES");
            if (!file.exists()) {
                file.mkdirs();

            }
            File dest = Environment.getExternalStorageDirectory();
            InputStream in = this.getResources().openRawResource(R.raw.sound_three);
            SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_three);
            SoundMediaPlayer.start();
            SoundMediaPlayer.setLooping(true);
            f = new File(file.getAbsolutePath() + dest.separator + "tune3" + ".mp3");
            try {
                out = new FileOutputStream(f);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
            }

            wall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
                    values.put(MediaStore.MediaColumns.TITLE, "Ringtune3");
                    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
                    values.put(MediaStore.MediaColumns.SIZE, f.length());
                    values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
                    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                    values.put(MediaStore.Audio.Media.IS_ALARM, true);
                    values.put(MediaStore.Audio.Media.IS_MUSIC, false);

                    Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
                            .getAbsolutePath());
                    getContentResolver().delete(
                            uri,
                            MediaStore.MediaColumns.DATA + "=\""
                                    + f.getAbsolutePath() + "\"", null);
                    Uri newUri = getContentResolver().insert(uri, values);

                    try {
                        RingtoneManager.setActualDefaultRingtoneUri(
                                GiftTuneActivity.this, RingtoneManager.TYPE_RINGTONE,
                                newUri);
                    } catch (Throwable t) {
                    }

                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.setType("audio/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(share, "Share audio File"));

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i) {
                        try {
                            SoundMediaPlayer.setLooping(false);
                            SoundMediaPlayer.stop();
                            imageView.setBackgroundResource(R.drawable.play);
                            i = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        i = true;
                        SoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_three);
                        SoundMediaPlayer.start();
                        SoundMediaPlayer.setLooping(true);
                        imageView.setBackgroundResource(R.drawable.pause);
                    }

                }
            });

        }


    }
}
