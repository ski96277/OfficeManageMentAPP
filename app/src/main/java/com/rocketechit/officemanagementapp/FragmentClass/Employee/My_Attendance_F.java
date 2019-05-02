package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
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

    List<String> date_List;
    List<String> entry_Time;
    List<String> exit_Time;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;


    String userID;
    Attandence_List_Adapter attandence_list_adapter;

    String month[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"};
    String year[] = {"2018", "2019", "2020", "2021", "2022", "2023"};


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
        getActivity().setTitle("My Attendance");

        date_List = new ArrayList<>();
        entry_Time = new ArrayList<>();
        exit_Time = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();
        databaseReference3 = firebaseDatabase.getReference();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        attendanceTableID_My.setLayoutManager(linearLayoutManager);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter month_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, month);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_month.setAdapter(month_adapter);
//SET current month
        Calendar cal = Calendar.getInstance();
        String current_month = month[cal.get(Calendar.MONTH)];
        int selectionPosition = month_adapter.getPosition(current_month);
        spinner_month.setSelection(selectionPosition);
//SET current month End


        ArrayAdapter year_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, year);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_year.setAdapter(year_adapter);
//SET current year
        Calendar cal2 = Calendar.getInstance();
        int year_int = cal2.get(Calendar.YEAR);
        int i;
        String current_year = null;
        if (year_int == 2018) {
            i = 0;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
        if (year_int == 2019) {
            i = 1;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
        if (year_int == 2020) {
            i = 2;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
        if (year_int == 2021) {
            i = 3;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
        if (year_int == 2022) {
            i = 3;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
        if (year_int == 2023) {
            i = 3;
            current_year = year[i];
            int selectionPosition_year = year_adapter.getPosition(current_year);
            spinner_year.setSelection(selectionPosition_year);
        }
//SET current year End
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = spinner_year.getSelectedItem().toString();
                String month = spinner_month.getSelectedItem().toString();
                getAttendenceValue(month, year);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String year = spinner_year.getSelectedItem().toString();
                    String month = spinner_month.getSelectedItem().toString();
                    getAttendenceValue(month, year);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getAttendenceValue(String month, String year) {

        date_List.clear();
        entry_Time.clear();
        exit_Time.clear();
        Log.e("TAG", "getAttendenceValue: "+date_List.size() );
        Log.e("TAG", "getAttendenceValue: "+entry_Time.size() );
        Log.e("TAG", "getAttendenceValue: "+exit_Time.size() );

        String userID_Employee = getUserID();
        int monthNumber;
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

        monthNumber = i;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();
//first data (date) start here
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//check the user ID has in the attendance list
                if (!dataSnapshot.child("Attendance").hasChild(userID_Employee)) {
                    attendanceTableID_My.setVisibility(View.GONE);

                }
//check the user  has attendance in this year
                if (!dataSnapshot.child("Attendance").child(userID_Employee).hasChild(year)) {
                    attendanceTableID_My.setVisibility(View.GONE);
                }
//check the user  has attendance in this month
                if (!dataSnapshot.child("Attendance").child(userID_Employee).child(year).hasChild(month)) {
                    attendanceTableID_My.setVisibility(View.GONE);

                }
                //clear the date_list for reduce the data replete
                date_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("Attendance")
                        .child(userID_Employee).child(year).child(String.valueOf(monthNumber)).getChildren()) {

                    attendanceTableID_My.setVisibility(View.VISIBLE);
                    String date = snapshot.getKey();
                    date_List.add(date);
                    if (date_List.size() < 1) {
                        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
                    }

                    databaseReference2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String entryTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(date).child("Entry").child("entryTime").getValue(String.class);
                            String exitTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(date).child("Exit").child("exitTime").getValue(String.class);
                            entry_Time.add(entryTime);
                            exit_Time.add(exitTime);

                            attandence_list_adapter = new Attandence_List_Adapter(getContext(),
                                    date_List, entry_Time, exit_Time,date_List.size());
                            attendanceTableID_My.setAdapter(attandence_list_adapter);


                            //recyclerView On Item Click
                            attandence_list_adapter.setOnItemClickListener(new Attandence_List_Adapter.ClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {

                                }

                                @Override
                                public void onItemLongClick(int position, View v) {

                                }
                            });
                            //recyclerView On Item Click END
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toasty.error(getContext(), "Data Error", Toasty.LENGTH_SHORT).show();
                        }
                    });//second Data coming end
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getContext(), "date not Found", Toasty.LENGTH_SHORT).show();
            }
        });
//first data (date) END

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
