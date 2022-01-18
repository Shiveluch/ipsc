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

public class MainListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<TargetStat> objects;

    protected ListView mListView;
    public MainListAdapter(Context context, ArrayList<TargetStat> stalkers, Activity activity) {
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
            view = lInflater.inflate(R.layout.mainadapter, parent, false);
        }

        TargetStat p = getMainList(position);
        ((TextView) view.findViewById(R.id.post1)).setText(p.target);
        ((TextView) view.findViewById(R.id.post2)).setText(p.status);
        ((TextView) view.findViewById(R.id.post3)).setText(p.add);
        if (p.add.equals("0"))
            ((TextView) view.findViewById(R.id.post2)).setTextColor(Color.parseColor("#ff0000"));
        if (p.add.equals("1"))
            ((TextView) view.findViewById(R.id.post2)).setTextColor(Color.parseColor("#00ff00"));




        return view;
    }

    TargetStat getMainList(int position) {
        return ((TargetStat) getItem(position));
    }

}
