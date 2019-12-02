package com.amindset.ve.amindset.Dashboard;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.Constant;
import com.amindset.ve.amindset.Fragment.user.FavList.FavListFragment;
import com.amindset.ve.amindset.Fragment.user.FavList.ModelRemoveFavList.ModelUserFavListRemove;
import com.amindset.ve.amindset.Fragment.userservice.ModelFavList.Datum;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.Web.ApiClient;
import com.amindset.ve.amindset.Web.WebService;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder> {

    private List<Datum> favList;
    private String userBtoken;
    Activity activity;
    FavListFragment favListFragment;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_aboutYou;
        public TextView fav_provider_name;
        public CircleImageView profilepic;
        public ImageView iv_like_dislike;
        public MyViewHolder(View view) {
            super(view);
            tv_aboutYou = (TextView) view.findViewById(R.id.tv_aboutYou);
            fav_provider_name = (TextView) view.findViewById(R.id.fav_provider_name);
            profilepic = (CircleImageView) view.findViewById(R.id.profilepic);
            iv_like_dislike = (ImageView) view.findViewById(R.id.iv_like_dislike);

        }
    }


    public NavAdapter(List<Datum> favList , String userBtoken, Activity activity , FavListFragment favListFragment) {
        this.favList = favList;
        this.userBtoken=userBtoken;
        this.activity=activity;
        this.favListFragment=favListFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nav, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Datum datum = favList.get(position);

        if(!TextUtils.isEmpty(datum.getTFname()))
            holder.fav_provider_name.setText(datum.getTFname());

        if(!TextUtils.isEmpty(datum.getTAbout()))
            holder.tv_aboutYou.setText(datum.getTAbout());

        if(!TextUtils.isEmpty(datum.getTProfPic()))
            Picasso.get().load(datum.getTProfPic()).into(holder.profilepic);

        holder.iv_like_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiForDislikeProvider(datum.getFavUserid(),position);
            }
        });

    }

    private void ApiForDislikeProvider(String favId,int pos) {


        ((Dashboard)activity).showLoading();

        WebService apiService =
                ApiClient.getClient().create(WebService.class);
        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("usertype", "1");
        paramObject.put("fav_userid", favId);

        Call<ModelUserFavListRemove> call = apiService.postForProviderDislike(paramObject , "Bearer " + userBtoken);

        call.enqueue(new retrofit2.Callback<ModelUserFavListRemove>() {
            @Override
            public void onResponse(Call<ModelUserFavListRemove> call, Response<ModelUserFavListRemove> response) {
                if (response != null) {
                    if (response.isSuccessful()) {

                        if(response.body().getStatus().equals( Constant.RESPONSE_SUCCESSFULLY))
                        {
                            ((Dashboard)activity).hideLoading();
                            ((Dashboard)activity).showSnackBar(response.body().getMessage());
                             favList.remove(pos);
                             notifyDataSetChanged();
//                             ((FavListFragment)favListFragment).APItoGetUserFavList();
                        }

                        else
                        {
                            ((Dashboard)activity).hideLoading();
                            ((Dashboard)activity).showSnackBar(response.body().getMessage());

                            ((FavListFragment)favListFragment).APItoGetUserFavList();

                        }
                    }

                    else
                    {
                        ((Dashboard)activity).hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserFavListRemove> call, Throwable t) {
                // Log error here since request failed
                ((Dashboard)activity).hideLoading();

                ((Dashboard)activity).showMessage(R.string.error_some_problem_occur);

            }
        });
    }

    @Override
    public int getItemCount() {
        return favList!=null?favList.size():0;
    }
}