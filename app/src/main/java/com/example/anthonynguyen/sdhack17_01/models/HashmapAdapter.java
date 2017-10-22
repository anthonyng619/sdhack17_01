package com.example.anthonynguyen.sdhack17_01.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anthonynguyen.sdhack17_01.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by anthonynguyen on 10/22/17.
 */

public class HashmapAdapter extends BaseAdapter {
    private final ArrayList mData;

    public HashmapAdapter(Map<String, Object> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Object> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.hashmap, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Object> item = getItem(position);

        ((TextView) result.findViewById(android.R.id.text1)).setText(revertString(item.getKey()));
        return result;
    }

    public String revertString(String text) {
        return text.replace(',', '.');
    }
}
