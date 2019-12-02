package com.amindset.ve.amindset.VdoCall;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.VdoCall.ProviderQuestionAndAnswer.Info;
import com.amindset.ve.amindset.data.AppPreferencesHelper;

import java.util.List;

public class ReportQuestionAnswerAdapter extends RecyclerView.Adapter<ReportQuestionAnswerAdapter.MyViewHolder> {

    private List<Info> questionList;
    private Activity activity;
    int pos;
    private AppPreferencesHelper appPreferencesHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question,saveAnswer;
        public TextView tv_answer;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            saveAnswer = (TextView) view.findViewById(R.id.saveAnswer);
            tv_answer = (TextView) view.findViewById(R.id.tv_answer);
        }
    }

    public ReportQuestionAnswerAdapter(List<Info> questionList, Activity activity) {
        this.questionList = questionList;
        appPreferencesHelper = new AppPreferencesHelper(activity);
        this.activity=activity;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider_question_answer, parent, false);
 
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        Info info = questionList.get(position);
        if(!TextUtils.isEmpty(info.getQuestion())) {
            pos = position + 1;
            holder.question.setText("" + pos + ". " + info.getQuestion());
        }


        if(!TextUtils.isEmpty(info.getAnswer())) {
            holder.tv_answer.setText("Ans: "+info.getAnswer());
        }



    }


    @Override
    public int getItemCount() {
        return questionList!=null?questionList.size():0;
    }
}