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
import android.widget.ArrayAdapter;
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
import java.util.Calendar;
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

    List<String> date_List;
    List<String> entry_Time;
    List<String> exit_Time;

    String TAG = "Employee_profile_view_by_admin";


    Attandence_List_Adapter attandence_list_adapter;

    String month[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"};
    String year[] = {"2018", "2019", "2020", "2021", "2022", "2023"};

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

        getActivity().setTitle("Attendance Information");
    }

    @Override
    public void onResume() {
        super.onResume();
        date_List = new ArrayList<>();
        entry_Time = new ArrayList<>();
        exit_Time = new ArrayList<>();
        date_List.clear();
        entry_Time.clear();
        exit_Time.clear();

        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);
        employeePositionTVID.setText(name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        attendanceTableID.setLayoutManager(linearLayoutManager);

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



        /*


//set on selected item in spinner month
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date_List.clear();
                entry_Time.clear();
                exit_Time.clear();
                getAttendenceValue(spinner_month.getSelectedItem().toString()
                        , spinner_year.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toasty.info(getContext(), "No Selected", Toast.LENGTH_SHORT, true).show();
            }
        });
//set on selected item in spinner month END

        //set on selected item in spinner year
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getAttendenceValue(spinner_month.getSelectedItem().toString(),
                        spinner_year.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toasty.info(getContext(), "No Selected", Toast.LENGTH_SHORT, true).show();
            }
        });*/
    }

    //get Attendence value from database
    private void getAttendenceValue(String month, String year) {
        String userID_Employee = employee_information.getUserID_Employee();

        date_List.clear();
        entry_Time.clear();
        exit_Time.clear();

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
                    attendanceTableID.setVisibility(View.GONE);

                }
//check the user  has attendance in this year
                if (!dataSnapshot.child("Attendance").child(userID_Employee).hasChild(year)) {
                    attendanceTableID.setVisibility(View.GONE);
                }
//check the user  has attendance in this month
                if (!dataSnapshot.child("Attendance").child(userID_Employee).child(year).hasChild(month)) {
                    attendanceTableID.setVisibility(View.GONE);

                }
                //clear the date_list for reduce the data replete
                date_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("Attendance")
                        .child(userID_Employee).child(year).child(String.valueOf(monthNumber)).getChildren()) {

                    attendanceTableID.setVisibility(View.VISIBLE);
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
                            attendanceTableID.setAdapter(attandence_list_adapter);


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

//get Attendence value from database   END

    // the create options menu with a MenuInflater to have the menu from your fragment

    //add new menu Action in Fragment @Override
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
    //add new menu Action in End

}
