package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.AdapterClass.Attandence_List_Adapter;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Employee_profile_view_by_admin extends Fragment {

    @BindView(R.id.employee_profile_image_ID)
    CircleImageView employeeProfileImageID;
    @BindView(R.id.employee_Position_TV_ID)
    TextView employeePositionTVID;
    @BindView(R.id.attendanceTable_ID)
    RecyclerView attendanceTableID;

    Employee_Information employee_information;
    @BindView(R.id.spinner)
    Spinner spinner_month;
    @BindView(R.id.spinner2)
    Spinner spinner_year;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    List<String> keyList;
    List<String> entry_Time;
    List<String> exit_Time;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_profile_view_by_admin_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        keyList = new ArrayList<>();
        entry_Time = new ArrayList<>();
        exit_Time = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();
        databaseReference3 = firebaseDatabase.getReference();

        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Log.e("TAG", "onViewCreated: " + imageUrl);
        Log.e("TAG", "onViewCreated: " + name);
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);
        employeePositionTVID.setText(name);

        getAttendenceValue(spinner_month.getSelectedItem().toString()
                , spinner_year.getSelectedItem().toString(), new GetAttendanceList() {
                    @Override
                    public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time) {

callAdapter(date,entry_Time,exit_Time);

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


                                callAdapter(date,entry_Time,exit_Time);
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

                                callAdapter(date,entry_Time,exit_Time);
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
        attendanceTableID.setLayoutManager(linearLayoutManager);

    }

    private void callAdapter(List<String> date, List<String> entry_time, List<String> exit_time) {
        Attandence_List_Adapter attandence_list_adapter=new Attandence_List_Adapter(getContext(),date,
                entry_time,exit_time);
        attendanceTableID.setAdapter(attandence_list_adapter);
        attandence_list_adapter.setOnItemClickListener(new Attandence_List_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    private void getAttendenceValue(String month, String year, GetAttendanceList getAttendanceList) {
        String userID_Employee = employee_information.getUserID_Employee();

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
    // the create options menu with a MenuInflater to have the menu from your fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.employee_information).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.employee_information:

                Fragment fragment = new Employee_Information_F();
                if (fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("employee_inforamtion", employee_information);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack("");
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.screen_Area_For_Admin, fragment);
                    fragmentTransaction.commit();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public interface GetAttendanceList {
        public void get_Date(List<String> date, List<String> entry_Time, List<String> exit_Time);

    }

}
