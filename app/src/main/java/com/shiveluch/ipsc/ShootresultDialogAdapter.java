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

public class ShootresultDialogAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListInfo> objects;

    protected ListView mListView;
    public ShootresultDialogAdapter(Context context, ArrayList<ListInfo> stalkers, Activity activity) {
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
            view = lInflater.inflate(R.layout.shootresultdialogadapter, parent, false);
        }

        ListInfo p = getMainList(position);
        ((TextView) view.findViewById(R.id.dialogtarget)).setText(p.field1);
        ((TextView) view.findViewById(R.id.takes)).setText(p.field2);
        ((TextView) view.findViewById(R.id.medi)).setText(p.field3);


        return view;
    }

    ListInfo getMainList(int position) {
        return ((ListInfo) getItem(position));
    }

}
