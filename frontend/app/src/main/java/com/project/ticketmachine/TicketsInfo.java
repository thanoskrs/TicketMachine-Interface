package com.project.ticketmachine;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketsInfo extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> groupList;
    HashMap<String, List<String>> expandableDetailList;
    String kind;

    MaterialButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_info);

        backBtn = (MaterialButton) findViewById(R.id.info_back_button);

        groupList = new ArrayList<>();
        kind = getIntent().getStringExtra("kind");
        expandableDetailList = new HashMap<String, List<String>>();

        initExpandableDetailList();

        expandableListView = (ExpandableListView) findViewById(R.id.ticketsExpandableListView);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, expandableDetailList);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initExpandableDetailList() {

        for (Document document : ProductScreen.list) {
            if (!((String) document.get("Kind")).equals(kind))
                continue;

            String name = (String) document.get("Name");
            String description = document.get("Description").toString();

            ArrayList childList = new ArrayList<String>();
            childList.add(description);

            groupList.add(name);
            expandableDetailList.put(name, childList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



