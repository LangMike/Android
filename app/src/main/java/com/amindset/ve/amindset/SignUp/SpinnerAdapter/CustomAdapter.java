package com.amindset.ve.amindset.SignUp.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.SignUp.DataModel.Datum;
import com.amindset.ve.amindset.SignUp.DataModel.ModelProficiencyList;
import com.amindset.ve.amindset.SignUp.signup_activity;

/***** Adapter class extends with ArrayAdapter ******/
public class CustomAdapter extends ArrayAdapter<Datum>{
     
    private Activity activity;
    private  List<Datum> data;
    public Resources res;
    Datum tempValues=null;
    LayoutInflater inflater;
     
    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(
            signup_activity activitySpinner,
                          int textViewResourceId,
                          List<Datum> objects,
                          Resources resLocal
                         ) 
     {
        super(activitySpinner, textViewResourceId, objects);
         
        /********** Take passed values **********/
        activity = activitySpinner;
        data     = objects;
        res      = resLocal;
    
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
      }
 
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
 
        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
         
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (Datum) data.get(position);
         
        TextView label      = (TextView)row.findViewById(R.id.spinner_item);

//        if(position==0){
//
//            // Default selected Spinner item
//            label.setText("Please select company");
////            sub.setText("");
//        }
//        else
//        {
            // Set values for spinner each row 
            label.setText(tempValues.getProficiency());

//        }
 
        return row;
      }
 }