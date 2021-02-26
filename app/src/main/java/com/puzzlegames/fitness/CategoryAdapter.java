package com.puzzlegames.fitness;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> catName, catImage;

    @BindView(R.id.catImageId) ImageView categoryImageView;
    @BindView(R.id.catTextId) TextView categoryTextView;
    private AssetManager mngr;

    CategoryAdapter(Context context, ArrayList<String> catName, ArrayList<String> catImage) {
        super(context,0, catName);
        this.context = context;
        this.catName = catName;
        this.catImage = catImage;
        inflater = LayoutInflater.from(context);
        mngr = context.getAssets();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cat_grid_view, null);
            ButterKnife.bind(this, convertView);

            categoryImageView.setImageBitmap(getBitmapFromAsset(context, catImage.get(position).toLowerCase()));

            categoryTextView.setText(catName.get(position));
        }

        return convertView;
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