package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class AddEvent_F extends Fragment {

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
        View view = inflater.inflate(R.layout.add_event_f, null);
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
                for (DataSnapshot snapshot : dataSnapshot.child("Event_List").child(getUserID()).getChildren()) {
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

                boolean hasTheDate = dataSnapshot.child("Event_List").child(getUserID()).hasChild(datew);
                if (hasTheDate) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String title = dataSnapshot.child("Event_List").child(getUserID())
                                    .child(datew).child("eventTitle").getValue(String.class);
                            String details = dataSnapshot.child("Event_List").child(getUserID())
                                    .child(datew).child("eventDetails").getValue(String.class);
//this date taken only for use when i update the event
                            String date = dataSnapshot.child("Event_List").child(getUserID())
                                    .child(datew).child("date").getValue(String.class);
                            dateTV.setText(date);

                            titleTV.setText(title);
                            detailsTV.setText(details);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });//end the addValueEventListener action

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    View view = layoutInflater.inflate(R.layout.add_event_custom_alert_dialog, null);
                    alertDialogBuilder.setView(view);
                    EditText titleET = view.findViewById(R.id.event_titleID);
                    EditText descriptionET = view.findViewById(R.id.event_details_ID);
                    alertDialogBuilder
                            .setPositiveButton("Submit", (dialog, which) -> {
                                String userID = getUserID();

                                String title = titleET.getText().toString();
                                String description = descriptionET.getText().toString();
                                EventClass eventClass = new EventClass(title, description, datew, year, month, day);
                                databaseReference.child("Event_List").child(userID).child(datew).setValue(eventClass);
                                Toasty.success(getContext(), "Event Added!", Toast.LENGTH_SHORT, true).show();


                            }).setNegativeButton("Cancel", (dialog, which) -> alertDialogBuilder.setCancelable(true)).show();
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

    //onclick method in Textview

    @OnClick({R.id.title_TV, R.id.details_TV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_TV:

                callTheEditMethod();
                break;
            case R.id.details_TV:
                callTheEditMethod();
                break;
        }
    }

    private void callTheEditMethod() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.add_event_custom_alert_dialog, null);
        alertDialogBuilder.setView(view);
        EditText titleET = view.findViewById(R.id.event_titleID);
        EditText descriptionET = view.findViewById(R.id.event_details_ID);

//get the Text from TextView and set on alert Edit Text
        titleET.setText(titleTV.getText().toString());
        descriptionET.setText(detailsTV.getText().toString());

        String date = dateTV.getText().toString();

        alertDialogBuilder
                .setPositiveButton("Submit", (dialog, which) -> {
                    String userID = getUserID();

                    String title = titleET.getText().toString();
                    String description = descriptionET.getText().toString();

                    databaseReference.child("Event_List").child(userID)
                            .child(date).child("eventTitle").setValue(title);
                    databaseReference.child("Event_List").child(userID)
                            .child(date).child("eventDetails").setValue(description);
                    Toast.makeText(getContext(), "Event updated", Toast.LENGTH_SHORT).show();

                }).setNegativeButton("Cancel", (dialog, which) -> alertDialogBuilder.setCancelable(true)).show();

    }
}
