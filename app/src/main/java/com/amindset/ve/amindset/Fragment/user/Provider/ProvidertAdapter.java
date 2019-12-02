package com.amindset.ve.amindset.Fragment.user.Provider;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Model.Info;
import com.amindset.ve.amindset.Fragment.userservice.Movie;
import com.amindset.ve.amindset.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProvidertAdapter extends RecyclerView.Adapter<ProvidertAdapter.MyViewHolder> {

    private List<Info> counselorList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView tv_description;
        public ImageView profilepic;
        public TextView tv_profiency;
        public TextView tv_proffesion_title;



        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            tv_profiency = (TextView) view.findViewById(R.id.tv_profiency);
            tv_proffesion_title = (TextView) view.findViewById(R.id.tv_proffesion_title);
            profilepic = (ImageView) view.findViewById(R.id.profilepic);
        }
    }


    public ProvidertAdapter(List<Info> counselorList) {
        this.counselorList = counselorList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Info info = counselorList.get(position);

        if(!TextUtils.isEmpty(info.getTherapName()))
            holder.name.setText(info.getTherapName());


        if(!TextUtils.isEmpty(info.getAbout()))
            holder.tv_description.setText(info.getAbout());

        if(!TextUtils.isEmpty(info.getProficiency()))
            holder.tv_description.setText("Provider: " + info.getProficiency());


        if(!TextUtils.isEmpty(info.getProficiency()))
            holder.tv_proffesion_title.setText("Professional title: " + info.getProfessionTitle());


        if(!TextUtils.isEmpty(info.getProfPic()))
        Picasso.get().load(info.getProfPic()).into(holder.profilepic);

    }
 
    @Override
    public int getItemCount() {
        return counselorList.size();
    }
}