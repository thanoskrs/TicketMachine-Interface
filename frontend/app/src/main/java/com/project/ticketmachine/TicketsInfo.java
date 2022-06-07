package com.project.ticketmachine;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketsInfo extends AppCompatActivity {

    List<String> groupList;
    List<String> child;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupList = new ArrayList<>();
        createGroupList();
    }

    private void createGroupList() {

    }


}
