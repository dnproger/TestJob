package com.example.ddn.sitesofttest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DDN on 31.05.2015.
 */
public class ContactsFragment extends Fragment implements Clickable{
    DataBaseHelper dbHelper;
    Cursor cursor;
    ListView listView;
    private ArrayList<HashMap<String, Object>> arrayList;

    public ContactsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        dbHelper = new DataBaseHelper(getActivity().getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        }catch(SQLException sqle){
            try {
                throw sqle;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        cursor = dbHelper.tableBaseQuery("contacts");
        cursor.moveToFirst();
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap;

        while(!cursor.isAfterLast()) {
            hashMap = new HashMap<>();
            hashMap.put("Name", cursor.getString(cursor.getColumnIndex("name")));
            hashMap.put("Phone", cursor.getString(cursor.getColumnIndex("phone")));
            arrayList.add(hashMap);
            cursor.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), arrayList, R.layout.list_item, new String[]{"Name", "Phone"}, new int[]{R.id.textView1, R.id.textView2});
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClickButton(View view) {
        switch (view.getId()){

        }
    }

}
