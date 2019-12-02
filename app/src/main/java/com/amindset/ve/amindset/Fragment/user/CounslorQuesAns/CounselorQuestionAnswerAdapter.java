package com.amindset.ve.amindset.Fragment.user.CounslorQuesAns;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amindset.ve.amindset.Dashboard.Dashboard;
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.Info;
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.ModelQuesAns.ModelSaveAnswer;
import com.amindset.ve.amindset.Fragment.userservice.MainService;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.Utils.NetworkUtils;
import com.amindset.ve.amindset.Web.ApiClient;
import com.amindset.ve.amindset.Web.WebService;
import com.amindset.ve.amindset.data.AppPreferencesHelper;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;

public class CounselorQuestionAnswerAdapter extends RecyclerView.Adapter<CounselorQuestionAnswerAdapter.MyViewHolder> {

    private List<Info> questionList;
    private Activity activity;
    int pos;
    private AppPreferencesHelper appPreferencesHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question,saveAnswer;
        public EditText et_answer;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            saveAnswer = (TextView) view.findViewById(R.id.saveAnswer);
            et_answer = (EditText) view.findViewById(R.id.et_answer);
        }
    }


    public CounselorQuestionAnswerAdapter(List<Info> questionList, Activity activity) {
        this.questionList = questionList;
        appPreferencesHelper = new AppPreferencesHelper(activity);
        this.activity=activity;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_answer, parent, false);
 
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        Info info = questionList.get(position);
        if(!TextUtils.isEmpty(info.getQuestion())) {
            pos = position + 1;
            holder.question.setText("" + pos + ". " + info.getQuestion());
        if(info.getQuestion().contains("N/A"))
        {
            String s =info.getQuestion().replace("N/A","\nN/A");
            holder.question.setText("" + pos + ". "+s );

        }
        }


        if(!TextUtils.isEmpty(info.getAnswer())) {
            holder.et_answer.setText(info.getAnswer());
        }



        holder.saveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NetworkUtils.isNetworkConnected(activity)) {
                    ((Dashboard)activity).showLoading();
                    ((Dashboard)activity).hideKeyboard();

                    SaveAnswerApi(info.getQueId(), holder.et_answer.getText().toString());
                }
                else
                {
                    ((Dashboard)activity).showMessage(R.string.msg_check_internet);
                }
            }
        });
    }

    private void SaveAnswerApi(String quesId, String ans) {


        WebService apiService =
                ApiClient.getClient().create(WebService.class);
        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("Ans", ans);
        paramObject.put("quesId", quesId);

        Call<ModelSaveAnswer> call = apiService.postAnswer(paramObject , "Bearer " + appPreferencesHelper.getUserBToken() );

        call.enqueue(new retrofit2.Callback<ModelSaveAnswer>() {
            @Override
            public void onResponse(Call<ModelSaveAnswer> call, Response<ModelSaveAnswer> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ((Dashboard)activity).hideLoading();
                        ((Dashboard)activity).showSnackBar(response.body().getMessage());

                    }

                    else
                    {
                        ((Dashboard)activity).hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelSaveAnswer> call, Throwable t) {
                // Log error here since request failed
                ((Dashboard)activity).hideLoading();
                ((Dashboard)activity).showSnackBar(activity.getString(R.string.error_some_problem_occur));

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList!=null?questionList.size():0;
    }
}