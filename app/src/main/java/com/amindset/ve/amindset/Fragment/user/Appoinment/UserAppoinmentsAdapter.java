package com.amindset.ve.amindset.Fragment.user.Appoinment;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.user.Appoinment.Model.Info;
import com.amindset.ve.amindset.Fragment.userservice.Movie;
import com.amindset.ve.amindset.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class UserAppoinmentsAdapter extends RecyclerView.Adapter<UserAppoinmentsAdapter.MyViewHolder> {

    private List<Info> infoList;
    private Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_calltime,tv_details,name,tv_calldate;
        public CircleImageView profile_image;
        public ImageView iv_call_type;

        public MyViewHolder(View view) {
            super(view);
            tv_calldate = (TextView) view.findViewById(R.id.tv_calldate);
            tv_calltime = (TextView) view.findViewById(R.id.tv_calltime);
            tv_details = (TextView) view.findViewById(R.id.tv_details);
            name = (TextView) view.findViewById(R.id.name);
            profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
            iv_call_type=(ImageView)view.findViewById(R.id.iv_call_type);
        }
    }

    public UserAppoinmentsAdapter(List<Info> infoList , Activity  activity) {
        this.infoList = infoList;
        this.activity=activity;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_appoinments, parent, false);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info info = infoList.get(position);

        if(!TextUtils.isEmpty(info.getCallTime()))
        holder.tv_calltime.setText(info.getCallTime());

        if(!TextUtils.isEmpty(info.getTAbout()))
            holder.tv_details.setText(info.getTAbout());

        if(!TextUtils.isEmpty(info.getCallTime()))
            holder.name.setText(info.getTFname());

        if(!TextUtils.isEmpty(info.getCreatedon()))
            holder.tv_calldate.setText(info.getCreatedon());

        if(!TextUtils.isEmpty(info.getTProfPic()))
            Picasso.get().load(info.getTProfPic()).into(holder.profile_image);

        if (!TextUtils.isEmpty(info.getCalltypes()))
        {
            if(info.getCalltypes().equalsIgnoreCase("1"))
                holder.iv_call_type.setBackgroundResource(R.mipmap.ic_phone_profile);

            else  if(info.getCalltypes().equalsIgnoreCase("2"))
                holder.iv_call_type.setBackgroundResource(R.mipmap.ic_video_call_provider_detail_view);

            else
            if(info.getCalltypes().equalsIgnoreCase("3"))
                holder.iv_call_type.setBackgroundResource(R.mipmap.ic_chat_provider_detail_view);

        }


    }
 
    @Override
    public int getItemCount() {
        return infoList!=null?infoList.size():0;
    }
}