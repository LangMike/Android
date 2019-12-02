package com.amindset.ve.amindset.Fragment.user.Counselorlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.CounselorProfileDetails;
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Model.Info;
import com.amindset.ve.amindset.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CounselorlistAdapter extends RecyclerView.Adapter<CounselorlistAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Info> counselorList;
    private Activity activity;
    private String
            title;
    private Counselorlisting iMethodCaller;

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_counselor_details:
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_counselor_details;
        public TextView counselor_name;
        public TextView tv_counselor_description;
        public ImageView profilepic;

        public MyViewHolder(View view) {
            super(view);
            rl_counselor_details = (RelativeLayout) view.findViewById(R.id.rl_counselor_details);
            counselor_name = (TextView) view.findViewById(R.id.counselor_name);
            tv_counselor_description = (TextView) view.findViewById(R.id.tv_counselor_description);
            profilepic = (ImageView) view.findViewById(R.id.profilepic);
        }
    }


    public CounselorlistAdapter(List<Info> counselorList, Activity activity , Counselorlisting iMethodCaller , String title) {
        this.counselorList = counselorList;
        this.activity=activity;
        this.iMethodCaller=iMethodCaller;
        this.title=title;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_specialist_couselor, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info info = counselorList.get(position);

        if(!TextUtils.isEmpty(info.getTherapName()))
            holder.counselor_name.setText(info.getTherapName());

        if(!TextUtils.isEmpty(info.getTherapName()))
            holder.tv_counselor_description.setText(info.getAbout());

        if(!TextUtils.isEmpty(info.getProfPic()))
        Picasso.get().load(info.getProfPic()).into(holder.profilepic);

        holder.rl_counselor_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new CounselorProfileDetails();
                Bundle args = new Bundle();
                args.putString("id", info.getTherapId());
                args.putString("title", title);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.child_fragment_container, myFragment).addToBackStack(null).commit();



            }
        });
    }
 
    @Override
    public int getItemCount() {
        return counselorList!=null?counselorList.size():0;
    }
}