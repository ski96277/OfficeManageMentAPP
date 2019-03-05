package com.rocketechit.officemanagementapp.FragmentClass.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rocketechit.officemanagementapp.Activity.ScanResultActivity;
import com.rocketechit.officemanagementapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Employee_Home extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_home_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick({R.id.entry_Button, R.id.exit_Button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.entry_Button:
                startActivity(new Intent(getActivity(), ScanResultActivity.class)
                        .putExtra("value", "Your Entry Time"));

                break;
            case R.id.exit_Button:
                startActivity(new Intent(getActivity(), ScanResultActivity.class)
                        .putExtra("value", "Your Exit Time"));
                break;
        }
    }

}