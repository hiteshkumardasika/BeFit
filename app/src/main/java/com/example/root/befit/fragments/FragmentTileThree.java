package com.example.root.befit.fragments;

/**
 * Created by root on 3/30/17.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.befit.CustomExpandableListAdapter;
import com.example.root.befit.R;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.root.befit.R.id.textView;


public class FragmentTileThree extends Fragment {

    CircleLayout circleLayout;
    Button button;
    TextView textView;
    ImageView imageView;
    ExpandableListView expandableListView;
    HashMap<String, List<String>> expandableListDetail;
    List<String> timingsList = new ArrayList<>();
    ExpandableListAdapter expandableListAdapter;
    ArrayList<String> listTitles = new ArrayList<>();

    public FragmentTileThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tile_three, container, false);
        circleLayout = (CircleLayout) view.findViewById(R.id.circle_layout);
        button = (Button) view.findViewById(R.id.connectButton);
        button.setVisibility(view.INVISIBLE);
        circleLayout.setRotating(false);
        circleLayout.setOnItemClickListener(new CircleLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                if (view instanceof CircleImageView) {
                    String name = ((CircleImageView) view).getName();
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                    View dialogView = layoutInflater.inflate(R.layout.custom_dialog, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setTitle(name + "'s details");
                    textView = (TextView) dialogView.findViewById(R.id.text);
                    imageView = (ImageView) dialogView.findViewById(R.id.image);
                    expandableListView = (ExpandableListView) dialogView.findViewById(R.id.expandList);
                    listTitles.add("Free Times");
                    expandableListDetail = new HashMap<String, List<String>>();
                    switch (view.getId()) {
                        case R.id.calendar:
                            textView.setText("Interests:\nCricket FootBall BasketBall");
                            imageView.setImageResource(R.drawable.calendar);
                            timingsList.add("Thursday 5.00PM - 6.00PM");
                            timingsList.add("Friday 5.00PM - 6.00PM");
                            expandableListDetail.put("Free Times", timingsList);
                            expandableListAdapter = new CustomExpandableListAdapter(getContext(), listTitles, expandableListDetail);
                            break;
                        case R.id.jeffrey:
                            textView.setText("Interests:\nBaseBall FootBall BasketBall");
                            imageView.setImageResource(R.drawable.jeff_round);
                            timingsList.add("Thursady 3.00PM - 6.00PM");
                            timingsList.add("Friday 3.00PM - 6.00PM");
                            expandableListDetail.put("Free Times", timingsList);
                            expandableListAdapter = new CustomExpandableListAdapter(getContext(), listTitles, expandableListDetail);
                            break;
                        case R.id.shakti:
                            textView.setText("Interests:\nSoftball FootBall Cricket");
                            imageView.setImageResource(R.drawable.shakti_round);
                            timingsList.add("Saturday 5.00PM - 6.00PM");
                            timingsList.add("Sunday 5.00PM - 6.00PM");
                            expandableListDetail.put("Free Times", timingsList);
                            expandableListAdapter = new CustomExpandableListAdapter(getContext(), listTitles, expandableListDetail);
                            break;
                        case R.id.jay:
                            textView.setText("Interests:\nChess Squash Billiards");
                            imageView.setImageResource(R.drawable.jay_round);
                            timingsList.add("Saturday 2.00PM - 6.00PM");
                            timingsList.add("Sunday 2.00PM - 6.00PM");
                            expandableListDetail.put("Free Times", timingsList);
                            expandableListAdapter = new CustomExpandableListAdapter(getContext(), listTitles, expandableListDetail);
                            break;
                    }
                    dialogBuilder.setPositiveButton("Yay!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Congrats!! You guys are playing together", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialogBuilder.setNegativeButton("Nay!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Cool!!...Look for other people", Toast.LENGTH_SHORT).show();
                        }
                    });
                    expandableListView.setAdapter(expandableListAdapter);
                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int i) {
                        }
                    });
                    dialogBuilder.show();
/*
                    builder.setTitle(name+"'s Details");
                    builder.setMessage("Interests: Cricket, Football....Wanna Meetup and Play Together");
                    builder.setPositiveButton("Yeah!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Great!!...You are Meeting", Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }
                    });

                    builder.setNegativeButton("Nay!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Okay!!..Look for other people", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
*/

                }
            }
        });

        return view;
    }
}