package com.amindset.ve.amindset.Fragment.providerservice.providerAppointements;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amindset.ve.amindset.Fragment.userservice.Movie;
import com.amindset.ve.amindset.R;

import java.util.List;

public class ProviderAppoinmentsAdapter extends RecyclerView.Adapter<ProviderAppoinmentsAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView navItemName;

        public MyViewHolder(View view) {
            super(view);
//            navItemName = (TextView) view.findViewById(R.id.navItemName);
        }
    }


    public ProviderAppoinmentsAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider_appoinments, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
//        holder.navItemName.setText(movie.getTitle());
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}