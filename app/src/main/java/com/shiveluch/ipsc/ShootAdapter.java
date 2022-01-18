package com.shiveluch.ipsc;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShootAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListInfo> objects;

    protected ListView mListView;
    public ShootAdapter(Context context, ArrayList<ListInfo> stalkers, Activity activity) {
        super();
        ctx = context;
        objects = stalkers;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // ???-?? ?????????
    @Override
    public int getCount() {
        return objects.size();
    }

    // ??????? ?? ???????
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.shootresulttime, parent, false);
        }

       ListInfo p = getMainList(position);
        ((TextView) view.findViewById(R.id.shoottime)).setText(p.field1);
        if (p.field2.equals("1"))
            ((TextView) view.findViewById(R.id.shoottime)).setTextSize(16);

        return view;
    }

    ListInfo getMainList(int position) {
        return ((ListInfo) getItem(position));
    }

}
