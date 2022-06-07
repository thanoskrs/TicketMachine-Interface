package com.project.ticketmachine;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketsInfo extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, String> ticketInfo;

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

        expandableListView = findViewById(R.id.ticketsExpandableListView);
        //expandableListAdapter = new MyExpandapleListAdapter(this, groupList, ticketInfo);
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
    }


}
