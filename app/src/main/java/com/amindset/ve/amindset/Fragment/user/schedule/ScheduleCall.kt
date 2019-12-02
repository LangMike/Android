//package com.amindset.ve.amindset.Fragment.user.schedule
//
//import android.app.Dialog
//import android.arch.lifecycle.ViewModelProviders
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.Window
//import android.widget.TextView
//import com.amindset.ve.amindset.BaseActivity.BaseFragment
//import com.amindset.ve.amindset.R
//import com.applandeo.materialcalendarview.EventDay
//import java.util.*
//import kotlin.collections.ArrayList
//
//
//class ScheduleCall : BaseFragment() , View.OnClickListener{
//
//     var  arr : ArrayList<EventDay?> = ArrayList()
//
//    lateinit var calendarView : com.applandeo.materialcalendarview.CalendarView
//
//    override fun onClick(p0: View?) {
//        when(p0!!.id)
//        {
//
//
//            R.id.selectDate->
//            {
//
//               calenderSetting();
//
//
//            }
//        }
//    }
//
//    private fun calenderSetting() {
//        var dialog : Dialog =  Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_date);
//        calendarView = dialog.findViewById(R.id.calendarView) as com.applandeo.materialcalendarview.CalendarView
//        val events = ArrayList<EventDay>()
//        val calendar = Calendar.getInstance()
//        events.add(EventDay(calendar))
//        calendarView.setEvents(events)
//        val calendars = ArrayList<Calendar>()
//        calendar.add(Calendar.DATE, -1);
//        calendars.add(calendar)
//        calendarView.setDisabledDays(calendars)
//
//        dialog.show()
//
//    }
//
//    //Declare varaibles
//
//    lateinit var schedule_Call : TextView
//
//    lateinit var selectDate : TextView
//
//    companion object {
//    }
//
//    private lateinit var viewModel: ScheduleCallViewModel
//    private lateinit var binding:ScheduleCall
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//
//        return inflater.inflate(R.layout.schedule_call_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ScheduleCallViewModel::class.java)
//        // TODO: Use the ViewModel
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        schedule_Call = view.findViewById(R.id.schedule) as TextView
//        schedule_Call.setOnClickListener(this)
//
//
//        selectDate = view.findViewById(R.id.selectDate) as TextView
//        selectDate.setOnClickListener(this)
//
//
//
//
//    }
//
//
//
//
//}
