package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.AdapterClass.Attandence_List_Adapter;
import com.rocketechit.officemanagementapp.FragmentClass.Admin.Employee_profile_view_by_admin;
import com.rocketechit.officemanagementapp.JavaClass.ConstantClass;
import com.rocketechit.officemanagementapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class My_Attendance_F extends Fragment {
    @BindView(R.id.spinner_month)
    Spinner spinner_month;
    @BindView(R.id.spinner_year)
    Spinner spinner_year;
    @BindView(R.id.my_attendanceTable_ID)
    RecyclerView attendanceTableID_My;


    List<String> keyList;
    List<String> entry_Time;
    List<String> exit_Time;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;


    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_attendance_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userID = getUserID();

        keyList = new ArrayList<>();
        entry_Time = new ArrayList<>();
        exit_Time = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();
        databaseReference3 = firebaseDatabase.getReference();


//get attendance value when load the fragment
        getAttendenceValue(spinner_month.getSelectedItem().toString()
                , spinner_year.getSelectedItem().toString(), new GetAttendanceList() {
                    @Override
                    public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time) {

                        callAdapter(date, entry_Time, exit_Time);

                    }
                });

        //set on selected item in spinner month
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAttendenceValue(spinner_month.getSelectedItem().toString()
                        , spinner_year.getSelectedItem().toString(), new GetAttendanceList() {
                            @Override
                            public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time) {


                                callAdapter(date, entry_Time, exit_Time);
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toasty.info(getContext(), "No Selected", Toast.LENGTH_SHORT, true).show();

            }
        });
        //set on selected item in spinner year
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAttendenceValue(spinner_month.getSelectedItem().toString(),
                        spinner_year.getSelectedItem().toString(), new GetAttendanceList() {
                            @Override
                            public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time) {

                                callAdapter(date, entry_Time, exit_Time);
                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toasty.info(getContext(), "No Selected", Toast.LENGTH_SHORT, true).show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        attendanceTableID_My.setLayoutManager(linearLayoutManager);


    }

    private void callAdapter(List<String> date, List<String> entry_time, List<String> exit_time) {
        Attandence_List_Adapter attandence_list_adapter = new Attandence_List_Adapter(getContext(), date,
                entry_time, exit_time);
        attendanceTableID_My.setAdapter(attandence_list_adapter);
        attandence_list_adapter.setOnItemClickListener(new Attandence_List_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    public interface GetAttendanceList {
        public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time);

    }

    private void getAttendenceValue(String month, String year, GetAttendanceList getAttendanceList) {
        String userID_Employee = userID;

        int i = 0;
        if (month.equals("January")) {
            i = 1;
        }
        if (month.equals("February")) {
            i = 2;
        }
        if (month.equals("March")) {
            i = 3;
        }
        if (month.equals("April")) {
            i = 4;
        }
        if (month.equals("May")) {
            i = 5;
        }
        if (month.equals("June")) {
            i = 6;
        }
        if (month.equals("July")) {
            i = 7;
        }
        if (month.equals("August")) {
            i = 8;
        }
        if (month.equals("September")) {
            i = 9;
        }
        if (month.equals("October")) {
            i = 10;
        }
        if (month.equals("November")) {
            i = 11;
        }
        if (month.equals("December")) {
            i = 12;
        }

        int monthNumber = i;
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyList.clear();

                for (DataSnapshot snapshot : dataSnapshot.child("Attendance")
                        .child(userID_Employee).child(year).child(String.valueOf(monthNumber)).getChildren()) {

                    entry_Time.clear();
                    exit_Time.clear();
                    String key = snapshot.getKey();
                    keyList.add(key);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String entryTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(key).child("Entry").child("entryTime").getValue(String.class);
                            String exitTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(key).child("Exit").child("exitTime").getValue(String.class);
                            entry_Time.add(entryTime);
                            exit_Time.add(exitTime);

                            getAttendanceList.get_Date(keyList, entry_Time, exit_Time);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });//second Data coming end
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//first Data
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
