package com.amindset.ve.amindset.Fragment.providerservice.providerRecentList;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.ModelRecent.Info;
import com.amindset.ve.amindset.Fragment.userservice.Movie;
import com.amindset.ve.amindset.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class ProviderRecentAdapter extends RecyclerView.Adapter<ProviderRecentAdapter.MyViewHolder> {

    private List<Info> infoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_calltime;
        public TextView tv_name;
        public TextView tv_total_duration;
        public TextView tv_total_cost;
        public CircleImageView profilepic;
        public ImageView iv_call_type;

        public MyViewHolder(View view) {
            super(view);
            tv_calltime = (TextView) view.findViewById(R.id.tv_calltime);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_total_duration = (TextView) view.findViewById(R.id.tv_total_duration);
            tv_total_cost = (TextView) view.findViewById(R.id.tv_total_cost);
            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
            iv_call_type = (ImageView) view.findViewById(R.id.iv_call_type);
        }
    }


    public ProviderRecentAdapter(List<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider_recent, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info info = infoList.get(position);


        if (!TextUtils.isEmpty(info.getCallTime()))
            holder.tv_calltime.setText(info.getCallTime());



        if (!TextUtils.isEmpty(info.getPFname()))
            holder.tv_name.setText(info.getPFname());

        if (!TextUtils.isEmpty(info.getCallDuration()))
            holder.tv_total_duration.setText(info.getCallDuration());


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

        if (!TextUtils.isEmpty(info.getCost()))
            holder.tv_total_cost.setText(info.getCost());
        else
            holder.tv_total_cost.setText("$ 0.0");



        if (!TextUtils.isEmpty(info.getPProfPic()))
            Picasso.get().load(info.getPProfPic()).into(holder.profilepic);

    }

    @Override
    public int getItemCount() {
        return infoList != null ? infoList.size() : 0;
    }
}