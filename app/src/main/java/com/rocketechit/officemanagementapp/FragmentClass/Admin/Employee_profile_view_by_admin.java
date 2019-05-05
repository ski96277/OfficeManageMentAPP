package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    Employee_Information employee_information;
    @BindView(R.id.spinner)
    Spinner spinner_month;
    @BindView(R.id.spinner2)
    Spinner spinner_year;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    String TAG = "Employee_profile_view_by_admin";

    String month[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"};
    String year[] = {"2018", "2019", "2020", "2021", "2022", "2023"};

    @BindView(R.id.table_row_31)
    TableRow tableRow31;
    @BindView(R.id.table)
    TableLayout table;
    @BindView(R.id.table_row_28)
    TableRow tableRow28;
    @BindView(R.id.table_row_29)
    TableRow tableRow29;
    @BindView(R.id.table_row_30)
    TableRow tableRow30;
    @BindView(R.id.date_TV_1)
    TextView dateTV1;
    @BindView(R.id.entry_time_date_1)
    TextView entryTimeDate1;
    @BindView(R.id.exit_time_date_1)
    TextView exitTimeDate1;
    @BindView(R.id.date_TV_2)
    TextView dateTV2;
    @BindView(R.id.entry_time_date_2)
    TextView entryTimeDate2;
    @BindView(R.id.exit_time_date_2)
    TextView exitTimeDate2;
    @BindView(R.id.date_TV_3)
    TextView dateTV3;
    @BindView(R.id.entry_time_date_3)
    TextView entryTimeDate3;
    @BindView(R.id.exit_time_date_3)
    TextView exitTimeDate3;
    @BindView(R.id.date_TV_4)
    TextView dateTV4;
    @BindView(R.id.entry_time_date_4)
    TextView entryTimeDate4;
    @BindView(R.id.exit_time_date_4)
    TextView exitTimeDate4;
    @BindView(R.id.date_TV_5)
    TextView dateTV5;
    @BindView(R.id.entry_time_date_5)
    TextView entryTimeDate5;
    @BindView(R.id.exit_time_date_5)
    TextView exitTimeDate5;
    @BindView(R.id.date_TV_6)
    TextView dateTV6;
    @BindView(R.id.entry_time_date_6)
    TextView entryTimeDate6;
    @BindView(R.id.exit_time_date_6)
    TextView exitTimeDate6;
    @BindView(R.id.date_TV_7)
    TextView dateTV7;
    @BindView(R.id.entry_time_date_7)
    TextView entryTimeDate7;
    @BindView(R.id.exit_time_date_7)
    TextView exitTimeDate7;
    @BindView(R.id.date_TV_8)
    TextView dateTV8;
    @BindView(R.id.entry_time_date_8)
    TextView entryTimeDate8;
    @BindView(R.id.exit_time_date_8)
    TextView exitTimeDate8;
    @BindView(R.id.date_TV_9)
    TextView dateTV9;
    @BindView(R.id.entry_time_date_9)
    TextView entryTimeDate9;
    @BindView(R.id.exit_time_date_9)
    TextView exitTimeDate9;
    @BindView(R.id.date_TV_10)
    TextView dateTV10;
    @BindView(R.id.entry_time_date_10)
    TextView entryTimeDate10;
    @BindView(R.id.exit_time_date_10)
    TextView exitTimeDate10;
    @BindView(R.id.date_TV_11)
    TextView dateTV11;
    @BindView(R.id.date_TV_12)
    TextView dateTV12;
    @BindView(R.id.date_TV_13)
    TextView dateTV13;
    @BindView(R.id.date_TV_14)
    TextView dateTV14;
    @BindView(R.id.date_TV_15)
    TextView dateTV15;
    @BindView(R.id.date_TV_16)
    TextView dateTV16;
    @BindView(R.id.date_TV_17)
    TextView dateTV17;
    @BindView(R.id.date_TV_18)
    TextView dateTV18;
    @BindView(R.id.date_TV_19)
    TextView dateTV19;
    @BindView(R.id.date_TV_20)
    TextView dateTV20;
    @BindView(R.id.date_TV_21)
    TextView dateTV21;
    @BindView(R.id.date_TV_22)
    TextView dateTV22;
    @BindView(R.id.date_TV_23)
    TextView dateTV23;
    @BindView(R.id.date_TV_24)
    TextView dateTV24;
    @BindView(R.id.date_TV_25)
    TextView dateTV25;
    @BindView(R.id.date_TV_26)
    TextView dateTV26;
    @BindView(R.id.date_TV_27)
    TextView dateTV27;
    @BindView(R.id.date_TV_28)
    TextView dateTV28;
    @BindView(R.id.date_TV_29)
    TextView dateTV29;
    @BindView(R.id.date_TV_30)
    TextView dateTV30;
    @BindView(R.id.date_TV_31)
    TextView dateTV31;
    @BindView(R.id.entry_time_date_11)
    TextView entryTimeDate11;
    @BindView(R.id.exit_time_date_11)
    TextView exitTimeDate11;
    @BindView(R.id.entry_time_date_12)
    TextView entryTimeDate12;
    @BindView(R.id.exit_time_date_12)
    TextView exitTimeDate12;
    @BindView(R.id.entry_time_date_13)
    TextView entryTimeDate13;
    @BindView(R.id.exit_time_date_13)
    TextView exitTimeDate13;
    @BindView(R.id.entry_time_date_14)
    TextView entryTimeDate14;
    @BindView(R.id.exit_time_date_14)
    TextView exitTimeDate14;
    @BindView(R.id.entry_time_date_15)
    TextView entryTimeDate15;
    @BindView(R.id.exit_time_date_15)
    TextView exitTimeDate15;
    @BindView(R.id.entry_time_date_16)
    TextView entryTimeDate16;
    @BindView(R.id.exit_time_date_16)
    TextView exitTimeDate16;
    @BindView(R.id.entry_time_date_17)
    TextView entryTimeDate17;
    @BindView(R.id.exit_time_date_17)
    TextView exitTimeDate17;
    @BindView(R.id.entry_time_date_18)
    TextView entryTimeDate18;
    @BindView(R.id.exit_time_date_18)
    TextView exitTimeDate18;
    @BindView(R.id.entry_time_date_19)
    TextView entryTimeDate19;
    @BindView(R.id.exit_time_date_19)
    TextView exitTimeDate19;
    @BindView(R.id.entry_time_date_20)
    TextView entryTimeDate20;
    @BindView(R.id.exit_time_date_20)
    TextView exitTimeDate20;
    @BindView(R.id.entry_time_date_21)
    TextView entryTimeDate21;
    @BindView(R.id.exit_time_date_21)
    TextView exitTimeDate21;
    @BindView(R.id.entry_time_date_22)
    TextView entryTimeDate22;
    @BindView(R.id.exit_time_date_22)
    TextView exitTimeDate22;
    @BindView(R.id.entry_time_date_23)
    TextView entryTimeDate23;
    @BindView(R.id.exit_time_date_23)
    TextView exitTimeDate23;
    @BindView(R.id.entry_time_date_24)
    TextView entryTimeDate24;
    @BindView(R.id.exit_time_date_24)
    TextView exitTimeDate24;
    @BindView(R.id.entry_time_date_25)
    TextView entryTimeDate25;
    @BindView(R.id.exit_time_date_25)
    TextView exitTimeDate25;
    @BindView(R.id.entry_time_date_26)
    TextView entryTimeDate26;
    @BindView(R.id.exit_time_date_26)
    TextView exitTimeDate26;
    @BindView(R.id.entry_time_date_27)
    TextView entryTimeDate27;
    @BindView(R.id.exit_time_date_27)
    TextView exitTimeDate27;
    @BindView(R.id.entry_time_date_28)
    TextView entryTimeDate28;
    @BindView(R.id.exit_time_date_28)
    TextView exitTimeDate28;
    @BindView(R.id.entry_time_date_29)
    TextView entryTimeDate29;
    @BindView(R.id.exit_time_date_29)
    TextView exitTimeDate29;
    @BindView(R.id.entry_time_date_30)
    TextView entryTimeDate30;
    @BindView(R.id.exit_time_date_30)
    TextView exitTimeDate30;
    @BindView(R.id.entry_time_date_31)
    TextView entryTimeDate31;
    @BindView(R.id.exit_time_date_31)
    TextView exitTimeDate31;

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


        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);
        employeePositionTVID.setText(name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

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

    //get Attendence value from database
    private void getAttendenceValue(String month, String year) {
        String userID_Employee = employee_information.getUserID_Employee();


        entryTimeDate1.setText("....");
        entryTimeDate2.setText("....");
        entryTimeDate3.setText("....");
        entryTimeDate4.setText("....");
        entryTimeDate5.setText("....");
        entryTimeDate6.setText("....");
        entryTimeDate7.setText("....");
        entryTimeDate8.setText("....");
        entryTimeDate9.setText("....");
        entryTimeDate10.setText("....");
        entryTimeDate11.setText("....");
        entryTimeDate12.setText("....");
        entryTimeDate13.setText("....");
        entryTimeDate14.setText("....");
        entryTimeDate15.setText("....");
        entryTimeDate16.setText("....");
        entryTimeDate17.setText("....");
        entryTimeDate18.setText("....");
        entryTimeDate19.setText("....");
        entryTimeDate20.setText("....");
        entryTimeDate21.setText("....");
        entryTimeDate22.setText("....");
        entryTimeDate23.setText("....");
        entryTimeDate24.setText("....");
        entryTimeDate25.setText("....");
        entryTimeDate26.setText("....");
        entryTimeDate27.setText("....");
        entryTimeDate28.setText("....");
        entryTimeDate29.setText("....");
        entryTimeDate30.setText("....");
        entryTimeDate31.setText("....");


        exitTimeDate1.setText("....");
        exitTimeDate2.setText("....");
        exitTimeDate3.setText("....");
        exitTimeDate4.setText("....");
        exitTimeDate5.setText("....");
        exitTimeDate6.setText("....");
        exitTimeDate7.setText("....");
        exitTimeDate8.setText("....");
        exitTimeDate9.setText("....");
        exitTimeDate10.setText("....");
        exitTimeDate11.setText("....");
        exitTimeDate12.setText("....");
        exitTimeDate13.setText("....");
        exitTimeDate14.setText("....");
        exitTimeDate15.setText("....");
        exitTimeDate16.setText("....");
        exitTimeDate17.setText("....");
        exitTimeDate18.setText("....");
        exitTimeDate19.setText("....");
        exitTimeDate20.setText("....");
        exitTimeDate21.setText("....");
        exitTimeDate22.setText("....");
        exitTimeDate23.setText("....");
        exitTimeDate24.setText("....");
        exitTimeDate25.setText("....");
        exitTimeDate26.setText("....");
        exitTimeDate27.setText("....");
        exitTimeDate28.setText("....");
        exitTimeDate29.setText("....");
        exitTimeDate30.setText("....");
        exitTimeDate31.setText("....");


        int monthNumber;
        int i = 0;
        if (month.equals("January")) {
            i = 1;
        }
        if (month.equals("February")) {
            tableRow31.setVisibility(View.GONE);
            tableRow30.setVisibility(View.GONE);
            tableRow29.setVisibility(View.GONE);

            boolean flag = false;
            int year_int = Integer.valueOf(year);
            if (year_int % 400 == 0) {
                flag = true;
            } else if (year_int % 100 == 0) {
                flag = false;
            } else if (year_int % 4 == 0) {
                flag = true;
            } else {
                flag = false;
            }
            if (flag) {
                tableRow29.setVisibility(View.VISIBLE);
            }


            i = 2;
        }
        if (month.equals("March")) {
            i = 3;
        }
        if (month.equals("April")) {
            tableRow31.setVisibility(View.GONE);

            i = 4;
        }
        if (month.equals("May")) {
            i = 5;
        }
        if (month.equals("June")) {
            tableRow31.setVisibility(View.GONE);

            i = 6;
        }
        if (month.equals("July")) {
            i = 7;
        }
        if (month.equals("August")) {
            i = 8;
        }
        if (month.equals("September")) {
            tableRow31.setVisibility(View.GONE);

            i = 9;
        }
        if (month.equals("October")) {
            i = 10;
        }
        if (month.equals("November")) {
            tableRow31.setVisibility(View.GONE);

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
                //clear the date_list for reduce the data replete
                for (DataSnapshot snapshot : dataSnapshot.child("Attendance")
                        .child(userID_Employee).child(year).child(String.valueOf(monthNumber)).getChildren()) {

                    String date = snapshot.getKey();

                    databaseReference2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String entryTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(date).child("Entry").child("entryTime").getValue(String.class);
                            String exitTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                    .child(date).child("Exit").child("exitTime").getValue(String.class);


                            if (date.equals(String.valueOf(1))) {
                                entryTimeDate1.setText(entryTime);
                                exitTimeDate1.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(2))) {
                                entryTimeDate2.setText(entryTime);
                                exitTimeDate2.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(3))) {
                                entryTimeDate3.setText(entryTime);
                                exitTimeDate3.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(4))) {
                                entryTimeDate4.setText(entryTime);
                                exitTimeDate4.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(5))) {
                                entryTimeDate5.setText(entryTime);
                                exitTimeDate5.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(6))) {
                                entryTimeDate6.setText(entryTime);
                                exitTimeDate6.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(7))) {
                                entryTimeDate7.setText(entryTime);
                                exitTimeDate7.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(8))) {
                                entryTimeDate8.setText(entryTime);
                                exitTimeDate8.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(9))) {
                                entryTimeDate9.setText(entryTime);
                                exitTimeDate9.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(10))) {
                                entryTimeDate10.setText(entryTime);
                                exitTimeDate10.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(11))) {
                                entryTimeDate11.setText(entryTime);
                                exitTimeDate11.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(12))) {
                                entryTimeDate12.setText(entryTime);
                                exitTimeDate12.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(13))) {
                                entryTimeDate13.setText(entryTime);
                                exitTimeDate13.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(14))) {
                                entryTimeDate14.setText(entryTime);
                                exitTimeDate14.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(15))) {
                                entryTimeDate15.setText(entryTime);
                                exitTimeDate15.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(16))) {
                                entryTimeDate16.setText(entryTime);
                                exitTimeDate16.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(17))) {
                                entryTimeDate17.setText(entryTime);
                                exitTimeDate17.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(18))) {
                                entryTimeDate18.setText(entryTime);
                                exitTimeDate18.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(19))) {
                                entryTimeDate19.setText(entryTime);
                                exitTimeDate19.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(20))) {
                                entryTimeDate20.setText(entryTime);
                                exitTimeDate20.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(21))) {
                                entryTimeDate21.setText(entryTime);
                                exitTimeDate21.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(22))) {
                                entryTimeDate22.setText(entryTime);
                                exitTimeDate22.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(23))) {
                                entryTimeDate23.setText(entryTime);
                                exitTimeDate23.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(24))) {
                                entryTimeDate24.setText(entryTime);
                                exitTimeDate24.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(25))) {
                                entryTimeDate25.setText(entryTime);
                                exitTimeDate25.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(26))) {
                                entryTimeDate26.setText(entryTime);
                                exitTimeDate26.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(27))) {
                                entryTimeDate27.setText(entryTime);
                                exitTimeDate27.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(28))) {
                                entryTimeDate28.setText(entryTime);
                                exitTimeDate28.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(29))) {
                                entryTimeDate29.setText(entryTime);
                                exitTimeDate29.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(30))) {
                                entryTimeDate30.setText(entryTime);
                                exitTimeDate30.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }
                            if (date.equals(String.valueOf(31))) {
                                entryTimeDate31.setText(entryTime);
                                exitTimeDate31.setText(exitTime);
                                Log.e(TAG, "Date :  " + date + " : " + exitTime + " : " + exitTime);
                            }

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
