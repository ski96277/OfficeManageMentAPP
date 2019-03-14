package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rocketechit.officemanagementapp.Activity.LoginActivity;
import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_profile_view_by_admin extends Fragment {

    @BindView(R.id.employee_profile_image_ID)
    CircleImageView employeeProfileImageID;
    @BindView(R.id.employee_Position_TV_ID)
    TextView employeePositionTVID;

    Employee_Information employee_information;

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

        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Log.e("TAG", "onViewCreated: " + imageUrl);
        Log.e("TAG", "onViewCreated: " + name);
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);

        employeePositionTVID.setText(name);

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
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("employee_inforamtion",employee_information);
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
