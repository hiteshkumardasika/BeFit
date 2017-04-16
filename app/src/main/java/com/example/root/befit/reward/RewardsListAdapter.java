package com.example.root.befit.reward;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.root.befit.R;

import java.util.List;

public class RewardsListAdapter extends ArrayAdapter<Reward> {

    Context context;
    public RewardsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Reward> objects) {
        super(context, resource, objects);
        System.out.println("Init RewardsListAdapter");
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        System.out.println("Overiding getView");
        Reward reward = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.rewards_item,null);
        TextView rewardName = (TextView) view.findViewById(R.id.reward_name);
        TextView rewardProgress = (TextView) view.findViewById(R.id.reward_progress);
        rewardName.setText(reward.getName());
        rewardProgress.setText(Integer.toString(reward.getProgressPoints()));
        return view;
    }
}
