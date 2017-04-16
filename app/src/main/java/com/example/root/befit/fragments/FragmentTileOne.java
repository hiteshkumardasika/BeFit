package com.example.root.befit.fragments;

/**
 * Created by root on 3/30/17.
 */

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.befit.MainActivity;
import com.example.root.befit.R;
import com.example.root.befit.reward.Reward;
import com.example.root.befit.reward.RewardsListAdapter;

import java.util.ArrayList;


public class FragmentTileOne extends Fragment {

    ListView rewardsLst;
    String[] name ={"Android","ios","Blackberry"};
    ArrayList<Reward> rewards = new ArrayList<Reward>();

    public FragmentTileOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tile_one, container, false);
        try {
            rewardsLst = (ListView) view.findViewById(R.id.Rewards);
            System.out.println("got the view");

            /*rewardsLst.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, name));
            rewardsLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    Toast.makeText(getActivity(), name[arg2], Toast.LENGTH_SHORT).show();
                }
            });*/
            loadStaticData();
            RewardsListAdapter adapter = new RewardsListAdapter(getActivity().getApplicationContext(),R.layout.rewards_item,rewards);
            rewardsLst.setAdapter(adapter);
            rewardsLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
                    Toast.makeText(getActivity(), rewards.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return view;
    }
    private void loadStaticData() {
        System.out.println("Loading Static Data");
        Reward r1 = new Reward("Coupon1",100,500,"Test1");
        Reward r2 = new Reward("Coupon2",100,5000,"Test1");
        Reward r3 = new Reward("Coupon3",100,5,"Test1");
        Reward r4 = new Reward("Coupon4",100,50000,"Test1");
        rewards.add(r1);
        rewards.add(r2);
        rewards.add(r3);
        rewards.add(r4);
        System.out.println("Data is loaded");
    }


}