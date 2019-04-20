package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.rocketechit.officemanagementapp.AdapterClass.EmployeeList_Adapter;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class EmployeeList extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userID;

    List<Employee_Information> employee_informations ;
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
            Log.e("TAG", "onResume: " + "on_resume_Employee_list");
        } else {
            Toasty.info(getContext(), "Internet error ", Toasty.LENGTH_SHORT).show();
        }
       /* if (employee_informations.isEmpty()){
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //your method
                    getRecyclerView_Data();
                }
            }, 0, 1000);//put here time 1000 milliseconds=1 second
        }*/
    }

    //show Data in Recycler View
    private void getRecyclerView_Data() {

//get Current Admin User ID
        userID = getUserID();
//get Employee Information as a list
        employee_informations = getUserData();

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
                    Log.e("TAG", "onDataChange:-- "+employee_informations.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getContext(), ""+employee_informations.size(), Toast.LENGTH_SHORT).show();
        return employee_informations;
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
