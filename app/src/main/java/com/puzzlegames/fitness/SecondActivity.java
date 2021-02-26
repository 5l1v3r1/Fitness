package com.puzzlegames.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    private ArrayList<String> imageCatList = new ArrayList<>();
    private ArrayList<String> nameCatList = new ArrayList<>();
    private ArrayList<String> idCatList = new ArrayList<>();

    private CategoryAdapter categoryAdapter;
    @BindView(R.id.catSecondGridId) GridView catGrid;

    private int catId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SQLiteDatabase.loadLibs(this);
        ButterKnife.bind(this);
        catId = getIntent().getExtras().getInt("id");

        init();
    }

    private void init(){
        imageCatList.clear();
        nameCatList.clear();
        idCatList.clear();

        File databaseFile = getDatabasePath("/data/data/"+getPackageName()+"/mydb/data3110");
        SQLiteDatabase database = null;
        database = SQLiteDatabase.
                openOrCreateDatabase(databaseFile, "", null);

        String sqlCatNameQuery = "SELECT * FROM exercices WHERE id_type=" + catId + " AND lang='" + Locale.getDefault().getLanguage()+"'";
        Cursor cursor = database.rawQuery(sqlCatNameQuery, null);
        while (cursor.moveToNext()){
            nameCatList.add(cursor.getString(2));
            imageCatList.add(cursor.getString(6)+"-min");
            idCatList.add(cursor.getString(0));
        }

        cursor.close();

        prepareAdapter();
    }

    private void prepareAdapter(){

        categoryAdapter = new CategoryAdapter(getApplicationContext(), nameCatList, imageCatList);
        catGrid.setAdapter(categoryAdapter);
        catGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mIntent = new Intent(getApplicationContext(), ContentActivity.class);
                mIntent.putExtra("id", Integer.parseInt(idCatList.get(position)));
                startActivity(mIntent);

            }
        });

    }
}
