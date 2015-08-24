package com.example.ddn.sitesofttest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DDN on 31.05.2015.
 */
public class CompanyFragment extends Fragment implements Clickable{
    DataBaseHelper dbHelper;
    ArrayList<Map<String, String>> groupData;
    ArrayList<Map<String, String>> childDataItem;
    ArrayList<ArrayList<Map<String, String>>> childData;
    Map<String, String> m;

    SimpleExpandableListAdapter adapter;
    public CompanyFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_company, container, false);
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

        Cursor cursor = dbHelper.tableBaseQuery("company");
        groupData = new ArrayList<Map<String, String>>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            m = new HashMap<String, String>();
            m.put("company", cursor.getString(cursor.getColumnIndex("name")));
            groupData.add(m);
            cursor.moveToNext();
        }
        String groupFrom[] = new String[] {"company"};

        int groupTo[] = new int[] {android.R.id.text1};

        childData = new ArrayList<ArrayList<Map<String, String>>>();

        int count = cursor.getCount();
        for(int i = 1; i < count+1;i++)
        {
            cursor = dbHelper.baseQuery("select * From department where company = ?", new String[]{String.valueOf(i)});
            cursor.moveToFirst();
            childDataItem = new ArrayList<Map<String, String>>();
            while(!cursor.isAfterLast()) {
                m = new HashMap<String, String>();
                m.put("department", cursor.getString(cursor.getColumnIndex("name")));
                childDataItem.add(m);
                cursor.moveToNext();
            }
            childData.add(childDataItem);
        }

        cursor.close();
        String childFrom[] = new String[] {"department"};
        int childTo[] = new int[] {android.R.id.text1};
        adapter = new SimpleExpandableListAdapter(
                getActivity().getApplicationContext(),
                groupData,
                R.layout.simple_expandable,
                groupFrom,
                groupTo,
                childData,
                R.layout.simple_list,
                childFrom,
                childTo);

        dbHelper.close();
        ExpandableListView lv = (ExpandableListView)view.findViewById(R.id.expandableListView);
        lv.setAdapter(adapter);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DescriptionActivity.class);
                try {
                    dbHelper.openDataBase();
                }catch(SQLException sqle){
                    try {
                        throw sqle;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                Cursor cursor = dbHelper.baseQuery("select description From department where company = ?", new String[]{String.valueOf(groupPosition+1)});
                cursor.moveToPosition(childPosition);
                intent.putExtra("text", cursor.getString(cursor.getColumnIndex("description")));
                Log.d("Log", cursor.getString(cursor.getColumnIndex("description")));
                cursor.close();
                dbHelper.close();
                startActivity(intent);
                return false;
            }
        });
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
