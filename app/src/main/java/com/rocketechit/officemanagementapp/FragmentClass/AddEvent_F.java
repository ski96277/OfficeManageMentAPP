package com.rocketechit.officemanagementapp.FragmentClass;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rocketechit.officemanagementapp.JavaClass.EventClass;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class AddEvent_F extends Fragment {

    MCalendarView mCalendarView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String userID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarView = view.findViewById(R.id.calenderView_ID);
//        mCalendarView.markDate(2019, 3, 10);
//mark as a list
       /* ArrayList<DateData> dates=new ArrayList<>();
        dates.add(new DateData(2018,04,26));
        dates.add(new DateData(2018,04,27));

        for(int i=0;i<dates.size();i++) {
            mCalendarView.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());//mark multiple dates with this code.
        }*/



        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

                String year= String.valueOf(date.getYear());
                String month= String.valueOf(date.getMonth());
                String day= String.valueOf(date.getDay());

                String datew = String.format("%d, %d, %d", date.getYear(), date.getMonth(), date.getDay());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(R.layout.add_event_custom_alert_dialog, null);
                alertDialogBuilder.setView(view);
                EditText titleET = view.findViewById(R.id.event_titleID);
                EditText descriptionET = view.findViewById(R.id.event_details_ID);
                String title = titleET.getText().toString();
                String description = descriptionET.getText().toString();


                alertDialogBuilder.setTitle("Event Details")
                        .setPositiveButton("Submit", (dialog, which) -> {
                            String userID=getUserID();
                            EventClass eventClass=new EventClass(title,description,datew,year,month,day,userID);

                        }).setNegativeButton("Cancel", (dialog, which) -> alertDialogBuilder.setCancelable(true)).show();
            }
        });

    }

    //get Current Admin User ID
    private String getUserID() {

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        return userID;
    }
}
