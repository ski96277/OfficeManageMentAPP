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

        //high lilted the event Date
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("Event_List").child("1bDbWQodBxXVX0mnKdERRa8vYuH3")
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



        calenderViewID.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

                String year = String.valueOf(date.getYear());
                String month = String.valueOf(date.getMonth());
                String day = String.valueOf(date.getDay());
                String datew = String.format("%d, %d, %d", date.getYear(), date.getMonth(), date.getDay());
                checkTheDateIsTaken(datew, year, month, day);
            }
        });

    }

    //check the click date is available on the Event list
    private void checkTheDateIsTaken(String datew, String year, String month, String day) {
//high lilted the event Date
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean hasTheDate = dataSnapshot.child("Event_List").child("1bDbWQodBxXVX0mnKdERRa8vYuH3")
                        .hasChild(datew);
                if (hasTheDate) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String title = dataSnapshot.child("Event_List").child("1bDbWQodBxXVX0mnKdERRa8vYuH3")
                                    .child(datew).child("eventTitle").getValue(String.class);
                            String details = dataSnapshot.child("Event_List").child("1bDbWQodBxXVX0mnKdERRa8vYuH3")
                                    .child(datew).child("eventDetails").getValue(String.class);
//this date taken only for use when i update the event
                            String date = dataSnapshot.child("Event_List").child("1bDbWQodBxXVX0mnKdERRa8vYuH3")
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


   /* //get Current Admin User ID
    private String getUserID() {

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        return userID;
    }*/

}
