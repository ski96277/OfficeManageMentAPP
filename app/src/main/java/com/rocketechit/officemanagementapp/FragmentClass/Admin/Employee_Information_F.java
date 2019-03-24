package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_Information_F extends Fragment {
    Employee_Information employee_information;
    @BindView(R.id.employee_profileview_image)
    CircleImageView employeeProfileviewImage;
    @BindView(R.id.employee_information_position)
    TextView employeeInformationPosition;
    @BindView(R.id.employee_information_name_TV)
    TextView employeeInformationNameTV;
    @BindView(R.id.employee_information_phoneNumber_TV)
    TextView employeeInformationPhoneNumberTV;
    @BindView(R.id.employee_information_email_TV)
    TextView employeeInformationEmailTV;
    @BindView(R.id.employee_information_join_Date_TV)
    TextView employeeInformationJoinDateTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_information_f, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        employee_information = (Employee_Information) bundle.getSerializable("employee_inforamtion");
        Picasso.get().load(employee_information.getImageLink()).error(R.drawable.man).placeholder(R.drawable.progress_animation).into(employeeProfileviewImage);
        employeeInformationPosition.setText(employee_information.getDesignation());
        employeeInformationNameTV.setText(employee_information.getName_Employee());
        employeeInformationPhoneNumberTV.setText(employee_information.getPhone());
        employeeInformationEmailTV.setText(employee_information.getEmail_Employee());
        employeeInformationJoinDateTV.setText(employee_information.getJoin_Date());
    }

    @OnClick({R.id.employee_information_phoneCall_button, R.id.employee_information_email_button, R.id.employee_information_whatsup_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.employee_information_phoneCall_button:
                String number=employee_information.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                break;
            case R.id.employee_information_email_button:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + employee_information.getEmail_Employee()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RocketechIT");

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

                break;
            case R.id.employee_information_whatsup_button:
                String url = "https://api.whatsapp.com/send?phone=+88"+employee_information.getPhone();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }
}
