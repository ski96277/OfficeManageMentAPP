package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.JavaClass.EventClass;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class ViewCalender_F extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @BindView(R.id.calenderView_ID)
    MCalendarView calenderViewID;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.details_TV)
    TextView detailsTV;
    @BindView(R.id.date_TV)
    TextView dateTV;

    static String userID_Company;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_calender_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
//get Company User ID
        getCompanyUserName(new MyCallback() {
            @Override
            public void onCallback(String value) {
                userID_Company = value;
                highlightedTheDate(userID_Company);
            }
        });

        calenderViewID.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

                String year = String.valueOf(date.getYear());
                String month = String.valueOf(date.getMonth());
                String day = String.valueOf(date.getDay());
                String datew = String.format("%d, %d, %d", date.getYear(), date.getMonth(), date.getDay());
                getCompanyUserName(value -> {
                    userID_Company=value;
                    checkTheDateIsTaken(datew, year, month, day,userID_Company);

                });
            }
        });

    }
//highlighted The Date  Method is here
private void highlightedTheDate(String userID_company) {
        //high lilted the event Date
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("Event_List").child(userID_company)
                        .getChildren()) {
                    EventClass eventClass = snapshot.getValue(EventClass.class);
                    int year = Integer.parseInt(eventClass.getYear());
                    int month = Integer.parseInt(eventClass.getMonth());
                    int day = Integer.parseInt(eventClass.getDay());
                    calenderViewID.markDate(year, month, day);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//get Company User ID Method

    private String getCompanyUserName(MyCallback myCallback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userID_Company = dataSnapshot.child("Employee_List")
                        .child(getUserID()).child("userID_company").getValue(String.class);
                myCallback.onCallback(userID_Company);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userID_Company;
    }

    //interface class for return the value from snapshot and ge the value in oncreate view
    public interface MyCallback {
        void onCallback(String value);
    }


    //check the click date is available on the Event list
    private void checkTheDateIsTaken(String datew, String year, String month, String day,String userID_Company) {
//high lilted the event Date
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean hasTheDate = dataSnapshot.child("Event_List").child(userID_Company)
                        .hasChild(datew);
                if (hasTheDate) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String title = dataSnapshot.child("Event_List").child(userID_Company)
                                    .child(datew).child("eventTitle").getValue(String.class);
                            String details = dataSnapshot.child("Event_List").child(userID_Company)
                                    .child(datew).child("eventDetails").getValue(String.class);
//this date taken only for use when i update the event
                            String date = dataSnapshot.child("Event_List").child(userID_Company)
                                    .child(datew).child("date").getValue(String.class);
                            dateTV.setText(date);

                            titleTV.setText(title);
                            detailsTV.setText(details);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });//end the addValueEventListener action

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
