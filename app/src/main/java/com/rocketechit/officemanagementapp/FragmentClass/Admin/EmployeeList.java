package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.rocketechit.officemanagementapp.Activity.SplashActivity;
import com.rocketechit.officemanagementapp.AdapterClass.EmployeeList_Adapter;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeList extends Fragment {
    String TAG = "EmployeeList ";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Employee_Information> employee_informations;
    @BindView(R.id.employee_list_Recycler_ID)
    RecyclerView employeeListRecyclerID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_list, null);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        getActivity().setTitle("Employee List");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        Log.e(TAG, "onResume: U ID : -  " + getUserID());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Company_Employee").child(getUserID());
        employee_informations = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        employeeListRecyclerID.setLayoutManager(gridLayoutManager);
        KAlertDialog alertDialog_Progress = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        if (CheckNetwork.isInternetAvailable(getContext())) {
            alertDialog_Progress.setTitleText("Loading....");
            alertDialog_Progress.setCancelable(false);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    employee_informations.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Employee_Information employee_information = snapshot.getValue(Employee_Information.class);
                        employee_informations.add(employee_information);
                    }

                    EmployeeList_Adapter employeeList_adapter = new EmployeeList_Adapter(getContext(), employee_informations);
                    employeeListRecyclerID.setAdapter(employeeList_adapter);
                    //hide alert
                    alertDialog_Progress.dismiss();
                    employeeList_adapter.setOnItemClickListener(new EmployeeList_Adapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Employee_Information employee_information = new Employee_Information(
                                    employee_informations.get(position).getUserID_Employee(),
                                    employee_informations.get(position).getUserID_company(),
                                    employee_informations.get(position).getEmail_Employee(),
                                    employee_informations.get(position).getPassword_Employee(),
                                    employee_informations.get(position).getName_Employee(),
                                    employee_informations.get(position).getJoin_Date(),
                                    employee_informations.get(position).getImageLink(),
                                    employee_informations.get(position).getDesignation(),
                                    employee_informations.get(position).getPhone()
                            );

                            Fragment fragment = new Employee_profile_view_by_admin();
                            Bundle bundle = new Bundle();

                            if (fragment != null) {
                                bundle.putSerializable("employee_information", employee_information);
                                fragment.setArguments(bundle);

                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.screen_Area_For_Admin, fragment);
                                fragmentTransaction.addToBackStack("");
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {
                            KAlertDialog alertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                            alertDialog.setTitleText("Are you sure?")
                                    .setContentText("Won't be able to recover this User!")
                                    .setConfirmText("Yes,delete it!")
                                    .setConfirmClickListener(kAlertDialog -> {

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Company_Employee").child(employee_informations.get(position)
                                                .getUserID_company()).child(employee_informations.get(position).getUserID_Employee())
                                                .removeValue();
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Employee_List")
                                                .child(employee_informations.get(position).getUserID_Employee())
                                                .removeValue();
                                        alertDialog.dismiss();


                                    })
                                    .show();

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            KAlertDialog alertDialog = new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE);
            alertDialog.setTitleText("Oops...");
            alertDialog.setContentText("Check Internet Connection");
            alertDialog.setCancelable(false);
            alertDialog.setConfirmClickListener(kAlertDialog -> {
                alertDialog.dismissWithAnimation();
                startActivity(new Intent(getContext(), SplashActivity.class));
                getActivity().finish();
            });
            alertDialog.show();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");

        employee_informations.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    /*FirebaseDatabase firebaseDatabase;
                    DatabaseReference databaseReference;
                    String userID;

                    List<Employee_Information> employee_informations;
                    EmployeeList_Adapter adapterClass_recycler;

                    @BindView(R.id.employee_list_Recycler_ID)
                    RecyclerView employeeListRecyclerID;

                    View view;

                    @Nullable
                    @Override
                    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                        view = inflater.inflate(R.layout.employee_list, null);
                        ButterKnife.bind(this, view);

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();
                        employee_informations = new ArrayList<>();
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        employeeListRecyclerID.setLayoutManager(gridLayoutManager);

                        getRecyclerView_Data();
                        return view;
                    }

                    @Override
                    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                        super.onViewCreated(view, savedInstanceState);

                    }

                    //Call the Data For recycler View in Resume Method
                    @Override
                    public void onResume() {
                        super.onResume();
                        if (CheckNetwork.isInternetAvailable(getContext())) {
                            getRecyclerView_Data();
                        } else {
                            //If  Internet Connection Failed
                            KAlertDialog alertDialog = new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE);
                            alertDialog.setTitleText("Oops...");
                            alertDialog.setContentText("Check Internet Connection");
                            alertDialog.setCancelable(false);
                            alertDialog.setConfirmClickListener(kAlertDialog -> {
                                alertDialog.dismissWithAnimation();
                                startActivity(new Intent(getContext(), SplashActivity.class));
                                getActivity().finish();
                            });
                            alertDialog.show();
                        }
                    }

                    //show Data in Recycler View
                    private void getRecyclerView_Data() {

                        KAlertDialog alertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                        alertDialog.setTitleText("Wait some second...");
                        alertDialog.setContentText("Loading..");
                        alertDialog.setCancelable(false);

                //get Current Admin User ID
                        userID = getUserID();
                //get Employee Information as a list
                        employee_informations = getUserData();
                        if (employee_informations.isEmpty()){
                            employee_informations = getUserData();
                        }else {
                            alertDialog.dismissWithAnimation();

                        }

                        adapterClass_recycler = new EmployeeList_Adapter(getContext(), employee_informations);

                        employeeListRecyclerID.setAdapter(adapterClass_recycler);

                        // On Click in recyclerView ID
                        adapterClass_recycler.setOnItemClickListener(new EmployeeList_Adapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {

                                Employee_Information employee_information = new Employee_Information(
                                        employee_informations.get(position).getUserID_Employee(),
                                        employee_informations.get(position).getUserID_company(),
                                        employee_informations.get(position).getEmail_Employee(),
                                        employee_informations.get(position).getPassword_Employee(),
                                        employee_informations.get(position).getName_Employee(),
                                        employee_informations.get(position).getJoin_Date(),
                                        employee_informations.get(position).getImageLink(),
                                        employee_informations.get(position).getDesignation(),
                                        employee_informations.get(position).getPhone()
                                );

                                Fragment fragment = new Employee_profile_view_by_admin();
                                Bundle bundle = new Bundle();

                                if (fragment != null) {
                                    bundle.putSerializable("employee_information", employee_information);
                                    fragment.setArguments(bundle);

                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.screen_Area_For_Admin, fragment);
                                    fragmentTransaction.addToBackStack("");
                                    fragmentTransaction.commit();
                                }
                            }

                            @Override
                            public void onItemLongClick(int position, View v) {

                            }
                        });
                    }

                    //call on resume


                    //get Employee Information as a list
                    private List<Employee_Information> getUserData() {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                employee_informations.clear();
                                for (DataSnapshot snapshot : dataSnapshot.child("Company_Employee").child(userID).getChildren()) {

                                    Employee_Information employee_information = snapshot.getValue(Employee_Information.class);
                                    employee_informations.add(employee_information);
                                    Log.e("TAG", "onDataChange:-- " + employee_informations.size());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getContext(), "" + employee_informations.size(), Toast.LENGTH_SHORT).show();
                        return employee_informations;
                    }
                */
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
