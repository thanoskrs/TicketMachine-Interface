package com.project.ticketmachine;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketsInfo  {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> groupList;
    HashMap<String, List<String>> expandableDetailList;

    MaterialButton backBtn;
    ImageButton volumeBtn;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;


    public void createInfoTicketDialog(Context context, LayoutInflater layoutInflater, InitializeTextToSpeach initializeTextToSpeach, String kind) {
        dialogBuilder = new AlertDialog.Builder(context);
        final View infoPopUp = layoutInflater.inflate(R.layout.tickets_info, null);


        backBtn = (MaterialButton) infoPopUp.findViewById(R.id.info_back_button);
        volumeBtn = (ImageButton) infoPopUp.findViewById(R.id.volumeButton);

        groupList = new ArrayList<>();
        expandableDetailList = new HashMap<String, List<String>>();

        initExpandableDetailList(kind);

        expandableListView = (ExpandableListView) infoPopUp.findViewById(R.id.ticketsExpandableListView);
        expandableListAdapter = new MyExpandableListAdapter(context, groupList, expandableDetailList);
        expandableListView.setAdapter(expandableListAdapter);


        dialogBuilder.setView(infoPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {

                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = i;

                if (MainActivity.TTS) {
                    initializeTextToSpeach.speak(expandableDetailList.get(groupList.get(i)).get(0));
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                if (MainActivity.TTS) {
                    initializeTextToSpeach.stop();
                }
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
                if (MainActivity.TTS)
                    initializeTextToSpeach.stop();

                dialog.dismiss();
            }
        });

        if (MainActivity.TTS) {
            volumeBtn.setImageResource(R.drawable.volume_up);
        } else {
            volumeBtn.setImageResource(R.drawable.volume_off);
        }

        volumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.TTS = !MainActivity.TTS;
                if (MainActivity.TTS) {
                    volumeBtn.setImageResource(R.drawable.volume_up);
                } else {
                    initializeTextToSpeach.stop();
                    volumeBtn.setImageResource(R.drawable.volume_off);
                }
            }
        });

        final Handler handler = new Handler();

        if (MainActivity.TTS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak("Για να δείτε πληροφορίες για κάποιο εισιτήριο, πατήστε πάνω στον τίτλο εισιτηρίου που σας ενδιαφέρει.");
                }
            }, 100);
        }
    }

    private void initExpandableDetailList(String kind) {

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
}



