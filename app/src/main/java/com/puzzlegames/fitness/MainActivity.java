package com.puzzlegames.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> imageCatList = new ArrayList<>();
    private ArrayList<String> nameCatList = new ArrayList<>();
    private ArrayList<String> idCatList = new ArrayList<>();

    private CategoryAdapter categoryAdapter;
    @BindView(R.id.catGridId) GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        imageCatList.clear();
        nameCatList.clear();
        idCatList.clear();

        /*

        String simpleJsonString = "{\"id\":1,\"name\":\"A green door\",\"price\":12.5,\"tags\":[\"home\",\"green\"]}";

        JSON json = new JSON(simpleJsonString);

        //access data
        String firstTag = json.key("tags").index(0).stringValue();
        Double price = json.key("price").doubleValue();
        
         */

        File databaseFile = getDatabasePath("/data/data/"+getPackageName()+"/mydb/data3110");
        SQLiteDatabase database = null;
        database = SQLiteDatabase.
                openOrCreateDatabase(databaseFile, "", null);

        String sqlCatNameQuery = "SELECT * FROM exercices_types WHERE lang='" + Locale.getDefault().getLanguage()+"'";
        Cursor cursor = database.rawQuery(sqlCatNameQuery, null);
        while (cursor.moveToNext()){
            nameCatList.add(cursor.getString(1));
        }

        String sqlCatIdQuery = "SELECT * FROM exercices_types WHERE lang='" + Locale.getDefault().getLanguage()+"'";
        cursor = database.rawQuery(sqlCatIdQuery, null);
        while (cursor.moveToNext()){
            idCatList.add(cursor.getString(4));
        }


        cursor.close();
        imageCatList.add("abs");
        imageCatList.add("back");
        imageCatList.add("biceps");
        imageCatList.add("calf");
        imageCatList.add("chest");
        imageCatList.add("forearms");
        imageCatList.add("leg");
        imageCatList.add("shoulder");
        imageCatList.add("triceps");

        prepareAdapter();
    }

    private void prepareAdapter(){

        categoryAdapter = new CategoryAdapter(getApplicationContext(), nameCatList, imageCatList);
        catGrid.setAdapter(categoryAdapter);
        catGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getApplicationContext(), SecondActivity.class);
                mIntent.putExtra("id", Integer.parseInt(idCatList.get(position)));
                startActivity(mIntent);
            }
        });

    }
}
