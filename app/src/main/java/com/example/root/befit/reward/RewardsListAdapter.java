package com.example.root.befit.reward;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.befit.MainActivity;
import com.example.root.befit.R;
import com.example.root.befit.fragments.FragmentTileOne;

import java.util.List;

public class RewardsListAdapter extends ArrayAdapter<Reward> {

    Context context;
    Activity activity;
    public RewardsListAdapter(Activity activity, @LayoutRes int resource, @NonNull List<Reward> objects) {
        super(activity.getApplicationContext(), resource, objects);
        System.out.println("Init RewardsListAdapter");
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public View getView(int position, View view, ViewGroup parent) {
        System.out.println("Overiding getView");
        Reward reward = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.rewards_item, null);
        TextView rewardName = (TextView) view.findViewById(R.id.reward_name);
        //TextView rewardProgress = (TextView) view.findViewById(R.id.reward_progress);
        TextView rewardDesc = (TextView) view.findViewById(R.id.reward_desc);

        ImageView coupon = (ImageView) view.findViewById(R.id.coupon);
        coupon.setImageResource(context.getResources().getIdentifier(reward.getImage(),"drawable",context.getPackageName()));
        coupon.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Click Image",Toast.LENGTH_LONG).show();
                loadPhoto((ImageView) view);
            }
        });
        float percent = ((reward.getTargetPoints() - reward.getProgressPoints()) * 100.0f) / reward.getTargetPoints();
        System.out.println("Percent:: "+ percent);
        ImageView overlay = (ImageView) view.findViewById(R.id.overlay);
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) percent, context.getResources().getDisplayMetrics());
        overlay.getLayoutParams().height = dimensionInDp;
        rewardName.setText(reward.getName());
        //rewardProgress.setText(Integer.toString(reward.getProgressPoints()));
        rewardDesc.setText(reward.getDescription());
        return view;
    }
    private void loadPhoto(ImageView imageView) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        LayoutInflater inflater =
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.expand_image_dialog,null);
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageResource(R.drawable.act_running_man);
        imageDialog.setView(layout);
        imageDialog.show();
//        image.setImageDrawable(tempImageView.getDrawable());
        /*
        imageDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        imageDialog.create();
        imageDialog.show();*/
    }
}
