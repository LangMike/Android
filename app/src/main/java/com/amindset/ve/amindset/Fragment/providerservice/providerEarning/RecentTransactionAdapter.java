package com.amindset.ve.amindset.Fragment.providerservice.providerEarning;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation.Datum;
import com.amindset.ve.amindset.Fragment.userservice.Movie;
import com.amindset.ve.amindset.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.MyViewHolder> {

    private List<Datum> transList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_total_amount, tv_total_duration,tv_total_time;
        public CircleImageView profilepic;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_total_amount = (TextView) view.findViewById(R.id.tv_total_amount);
            tv_total_duration = (TextView) view.findViewById(R.id.tv_total_duration);
            tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
        }
    }


    public RecentTransactionAdapter(List<Datum> transList , Activity activity) {
        this.transList = transList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider_transactions, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Datum data = transList.get(position);

        if(!TextUtils.isEmpty(data.getPFname()))
        holder.tv_name.setText(data.getPFname());

        if(!TextUtils.isEmpty(data.getTotalEarn()))
            holder.tv_total_amount.setText("$ "+data.getTotalEarn());

        if(!TextUtils.isEmpty(data.getCallDuration()))
        holder.tv_total_duration.setText(data.getCallDuration());

        if(!TextUtils.isEmpty(data.getTransTime()))
        holder.tv_total_time.setText(data.getTransTime());

        if(!TextUtils.isEmpty(data.getPProfPic()))
            Picasso.get().load(data.getPProfPic()).into(holder.profilepic);



    }
 
    @Override
    public int getItemCount() {
        return transList!=null?transList.size():0;
    }
}