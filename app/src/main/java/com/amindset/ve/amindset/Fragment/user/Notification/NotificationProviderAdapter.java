package com.amindset.ve.amindset.Fragment.user.Notification;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi.InfoProvider;
import com.amindset.ve.amindset.Fragment.user.Notification.ModelUserNotifica.Info;
import com.amindset.ve.amindset.R;

import java.util.List;

public class NotificationProviderAdapter extends RecyclerView.Adapter<NotificationProviderAdapter.MyViewHolder> {

    private List<InfoProvider> infoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,tv_notification_details,tv_daystatus;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            tv_notification_details = (TextView) view.findViewById(R.id.tv_notification_details);
            tv_daystatus = (TextView) view.findViewById(R.id.tv_daystatus);
        }
    }


    public NotificationProviderAdapter(List<InfoProvider> infoList) {
        this.infoList = infoList;
    }


 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notofication, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InfoProvider movie = infoList.get(position);
//        if(!TextUtils.isEmpty(movie.getNotificationText())) {
//            holder.tv_notification_details.setText(movie.getNotificationText());
//        }

        if(!TextUtils.isEmpty(movie.getName())) {
            holder.title.setText(movie.getName());

        }
        if(!TextUtils.isEmpty(movie.getApptDate())) {
            holder.tv_daystatus.setText(movie.getApptDate());

        }
    }
 
    @Override
    public int getItemCount() {
        return infoList==null?0:infoList.size();
    }
}