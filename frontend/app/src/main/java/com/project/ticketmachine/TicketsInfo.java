package com.project.ticketmachine;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsInfo extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, String> ticketCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_info);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        groupList = new ArrayList<>(getIntent().getStringArrayListExtra("Products"));
        ticketCollection = new HashMap<String, String>();
        childList = new ArrayList<String>();

        for (int i =0; i < groupList.size(); i++) {
            childList.add("asddsadsa");
        }


        expandableListView = findViewById(R.id.ticketsExpandableListView);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, ticketCollection);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition)
                    expandableListView.collapseGroup(lastExpandedPosition);

                lastExpandedPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                return false;
            }
        });
    }


}
