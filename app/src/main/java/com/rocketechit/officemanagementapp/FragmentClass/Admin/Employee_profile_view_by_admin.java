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
import com.rocketechit.officemanagementapp.JavaClass.ConstantClass;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.JavaClass.Entry_Exit_Time;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference();

        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Log.e("TAG", "onViewCreated: " + imageUrl);
        Log.e("TAG", "onViewCreated: " + name);
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);
        employeePositionTVID.setText(name);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String month = spinner_month.getSelectedItem().toString();
                String year = spinner_year.getSelectedItem().toString();
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
                        for (DataSnapshot snapshot : dataSnapshot.child("Attendance")
                                .child(userID_Employee).child(year).child(String.valueOf(monthNumber)).getChildren()) {

                            String key = snapshot.getKey();
                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.e(TAG, "onDataChange: "+userID_Employee+"-"+year+"-"+monthNumber+"-"+key+"" );
                                    String entryTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                            .child(key).child("Entry").child("entryTime").getValue(String.class);
                                    String exitTime = dataSnapshot.child("Attendance").child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                            .child(key).child("Exit").child("exitTime").getValue(String.class);
                                    Toast.makeText(getContext(), ""+entryTime, Toast.LENGTH_SHORT).show();
                                    Log.e("TAG ", "Entry time: "+entryTime);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                          /*  Log.e("TAG -- ", "Date: " + key);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot2 : dataSnapshot.child("Attendance")
                                            .child(userID_Employee).child(year).child(String.valueOf(monthNumber))
                                            .child(key).getChildren()) {
                                       String entryTime = (String) snapshot2.child("Entry").getValue();
                                       String exitTime= (String) snapshot2.child("Exit").getValue();
                                        Log.e("TAG -- ", "Entry Time: " + entryTime);
                                        Log.e("TAG -- ", "Exit Time: " + exitTime);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
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

}
