package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_profile_view_by_admin extends Fragment {

    @BindView(R.id.employee_profile_image_ID)
    CircleImageView employeeProfileImageID;
    @BindView(R.id.employee_Position_TV_ID)
    TextView employeePositionTVID;

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

        Bundle bundle = getArguments();
        Employee_Information employee_information = (Employee_Information) bundle.getSerializable("employee_information");

        String imageUrl = employee_information.getImageLink();
        String name = employee_information.getName_Employee();
        Log.e("TAG", "onViewCreated: "+imageUrl );
        Log.e("TAG", "onViewCreated: "+name);
        Picasso.get().load(imageUrl).error(R.drawable.man)
                .placeholder(R.drawable.progress_animation).into(employeeProfileImageID);

        employeePositionTVID.setText(name);

    }
}
