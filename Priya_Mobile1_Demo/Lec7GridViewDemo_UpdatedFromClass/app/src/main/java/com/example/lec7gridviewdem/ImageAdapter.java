package com.example.lec7gridviewdem;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<GalleryImage> imageList;

    public ImageAdapter(List<GalleryImage> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public GalleryImage getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return imageList.get(i).getImgId(); //returns the id of the ith gallery image object
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            ImageView imgViewItem = new ImageView(viewGroup.getContext()); //creates an image view object on the fly
           // imgViewItem.setLayoutParams(new ViewGroup.LayoutParams(120,120));

            imgViewItem.setLayoutParams(new ViewGroup.LayoutParams(GridView.AUTO_FIT,120));
            imgViewItem.setScaleType(ImageView.ScaleType.FIT_CENTER); //maintains the image proportion
            imgViewItem.setBackgroundColor(Color.LTGRAY);
            imgViewItem.setImageResource(imageList.get(i).getImgPic());
            view = imgViewItem; //sets the view to the imgViewItem object we have just created
        }

        //txtViewItem.setText()

        return view;
    }
}
