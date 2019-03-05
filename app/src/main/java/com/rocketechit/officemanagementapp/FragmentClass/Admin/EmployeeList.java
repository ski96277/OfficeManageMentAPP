package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.AdapterClass.EmployeeList_Adapter;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeList extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userID;

    List<Employee_Information> employee_informations;

    @BindView(R.id.employee_list_Recycler_ID)
    RecyclerView employeeListRecyclerID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        employee_informations = new ArrayList<>();
//get Current Admin User ID
        userID = getUserID();
//get Employee Information as a list
        employee_informations = getUserData();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        employeeListRecyclerID.setLayoutManager(gridLayoutManager);

        EmployeeList_Adapter adapterClass_recycler = new EmployeeList_Adapter(getContext(), employee_informations);

        employeeListRecyclerID.setAdapter(adapterClass_recycler);

        adapterClass_recycler.setOnItemClickListener(new EmployeeList_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Employee_Information employee_information = new Employee_Information(
                        employee_informations.get(position).getUserID_Employee(),
                        employee_informations.get(position).getEmail_Employee(),
                        employee_informations.get(position).getPassword_Employee(),
                        employee_informations.get(position).getName_Employee(),
                        employee_informations.get(position).getJoin_Date(),
                        employee_informations.get(position).getImageLink()
                );

                Fragment fragment = new Employee_profile_view_by_admin();
                Bundle bundle = new Bundle();

                if (fragment != null) {
                    bundle.putSerializable("employee_information", employee_information);
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.screen_Area_For_Admin,fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });


    }

    //get Employee Information as a list
    private List<Employee_Information> getUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("Company_Employee").child(userID).getChildren()) {

                    Employee_Information employee_information = snapshot.getValue(Employee_Information.class);
                    employee_informations.add(employee_information);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
