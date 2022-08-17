package com.example.briskdelivery;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuAdapter extends BaseAdapter {

    List<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();

    // Constructor
    public MenuAdapter( List<HashMap<String, String>> hmList) {
        this.hmList = hmList;
    }
    public int getCount() {
        return hmList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater
                    = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.listview_menu,
                    viewGroup,false);
        }
        TextView txtItem = view.findViewById(R.id.txtItem);
        TextView txtSubItem1 = view.findViewById(R.id.txtSubItem1);

        TextView txtSubItem2 = view.findViewById(R.id.txtSubItem2);


        System.out.println(hmList.size());


        txtItem.setText(hmList.get(i).get("title"));
         txtSubItem1.setText(hmList.get(i).get("desc"));

         txtSubItem2.setText(hmList.get(i).get("price"));

        txtItem.setGravity(Gravity.CENTER); //centers the text in the TextView
        txtSubItem1.setGravity(Gravity.CENTER); //centers the text in the TextView

        txtSubItem2.setGravity(Gravity.CENTER); //centers the text in the TextView


        return view;
    }

}
