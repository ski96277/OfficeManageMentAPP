package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_Profile_F extends Fragment {
    @BindView(R.id.employee_profileview_image)
    CircleImageView profileviewImage_img;
    @BindView(R.id.employee_profileview_position)
    TextView position_TV;
    @BindView(R.id.employee_profileview_name_TV)
    TextView name_TV;
    @BindView(R.id.employee_profileview_phoneNumber_TV)
    TextView phoneNumber_TV;
    @BindView(R.id.employee_profileview_email_TV)
    TextView email_TV;
    @BindView(R.id.employee_profileview_join_Date_TV)
    TextView joinDate_TV;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Fragment fragment = null;
    Employee_Information employee_information;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_profile_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Profile");


        get_Employee_Information(new myCallBack() {
            @Override
            public void onCallBack(Employee_Information employee_information) {
                String image_link = employee_information.getImageLink();
                String position = employee_information.getDesignation();
                String email = employee_information.getEmail_Employee();
                String join_date = employee_information.getJoin_Date();
                String name = employee_information.getName_Employee();
                String pass = employee_information.getPassword_Employee();
                String phone = employee_information.getPhone();


                Log.e("image - -", "onCallBack: " + image_link);
                Log.e("position- -", "onCallBack: " + position);
                Log.e("email - -", "onCallBack: " + email);
                Log.e("join Date - -", "onCallBack: " + join_date);
                Log.e("name - -", "onCallBack: " + name);
                Log.e("password - -", "onCallBack: " + pass);
                Log.e("phone Number - -", "onCallBack: " + phone);
                Picasso.get().load(image_link).error(R.drawable.man).placeholder(R.drawable.progress_animation).into(profileviewImage_img);
                position_TV.setText(position);
                name_TV.setText(name);
                phoneNumber_TV.setText(phone);
                email_TV.setText(email);
                joinDate_TV.setText(join_date);

            }
        });


    }

    private void get_Employee_Information(myCallBack myCallBack) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee_information = dataSnapshot.child("Employee_List").child(getUserID()).getValue(Employee_Information.class);

                myCallBack.onCallBack(employee_information);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.employee_profileview_Edit_button)
    public void onClick() {

        fragment = new Edit_employee_profile();
        if (fragment != null) {
            Bundle bundle=new Bundle();
            bundle.putSerializable("employee_information", employee_information);
            Log.e("TAG - - ", "onClick: "+employee_information.getDesignation() );
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.screen_Area_For_Employee, fragment);
            fragmentTransaction.commit();
        }

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

    public interface myCallBack {

        public void onCallBack(Employee_Information employee_information);
    }
}
