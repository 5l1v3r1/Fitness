package com.puzzlegames.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends AppCompatActivity {

    @BindView(R.id.videoViewId) VideoView videoView;
    @BindView(R.id.imgDescId) ImageView imgDesc;
    @BindView(R.id.textTitleId) TextView textTitle;
    @BindView(R.id.textDescId) TextView textDesc;

    private int catId = 0;
    private String dbTitle, dbDesc, dbIcon, dbVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        catId = getIntent().getExtras().getInt("id");
        init();

    }

    private void init(){

        setContentData();

    }

    private void setContentData(){

        File databaseFile = getDatabasePath("/data/data/"+getPackageName()+"/mydb/data3110");
        SQLiteDatabase database = null;
        database = SQLiteDatabase.
                openOrCreateDatabase(databaseFile, "", null);

        String sqlCatNameQuery = "SELECT * FROM exercices WHERE id=" + catId;
        Cursor cursor = database.rawQuery(sqlCatNameQuery, null);
        while (cursor.moveToNext()){
            dbTitle = cursor.getString(2);
            dbDesc = cursor.getString(3);
            dbIcon = cursor.getString(4);
            dbVideo = cursor.getString(6);


        }

        cursor.close();

        int rawId = getResources().getIdentifier("ex" + dbVideo,  "raw", getPackageName());
        String path = "android.resource://" + getPackageName() + "/" + rawId;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        setTitle(dbTitle);
        textTitle.setText(dbTitle);
        textDesc.setText(dbDesc);
        imgDesc.setImageBitmap(getBitmapFromAsset(getApplicationContext(), "icon" + dbIcon));

    }

    @OnClick(R.id.imgDescId)
    void showContentImage(){
        ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800);
        imagePopup.setWindowWidth(800);
        imagePopup.setFullScreen(false);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        imagePopup.initiatePopup(imgDesc.getDrawable());
        imagePopup.viewPopup();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentData();
    }

    private Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open("images/" + filePath + ".png");
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }
}
