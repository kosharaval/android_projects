package com.example.briskdelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    List<HashMap<String, String>> list;

    // Constructor
    public HistoryAdapter( List<HashMap<String, String>> list) {
        this.list = list;
    }
    public int getCount() {
        return list.size();
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
            view = layoutInflater.inflate(R.layout.listview_history_layout,
                    viewGroup,false);
        }
        TextView hisprice = view.findViewById(R.id.hisprice);
        TextView hisitem = view.findViewById(R.id.hisitem);

        TextView hisdate = view.findViewById(R.id.hisdate);


        hisitem.setText(list.get(i).get("id") );
        hisdate.setText(list.get(i).get("date") );
        hisprice.setText("$"+list.get(i).get("price") );

        return view;
    }
}
