package com.rocketechit.officemanagementapp.FragmentClass.Admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_Information_F extends Fragment {
    static Employee_Information employee_information;
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
    public static TextView employeeInformationJoinDateTV2;

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

        employeeInformationJoinDateTV2 = view.findViewById(R.id.employee_information_join_Date_TV);

        employee_information = (Employee_Information) bundle.getSerializable("employee_inforamtion");
        Picasso.get().load(employee_information.getImageLink()).error(R.drawable.man).placeholder(R.drawable.progress_animation).into(employeeProfileviewImage);
        employeeInformationPosition.setText(employee_information.getDesignation());
        employeeInformationNameTV.setText(employee_information.getName_Employee());
//set Title as employee name
        getActivity().setTitle(employee_information.getName_Employee());

        employeeInformationPhoneNumberTV.setText(employee_information.getPhone());
        employeeInformationEmailTV.setText(employee_information.getEmail_Employee());
        employeeInformationJoinDateTV.setText(employee_information.getJoin_Date());

        employeeInformationJoinDateTV.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

        });

    }

    @OnClick({R.id.employee_information_phoneCall_button, R.id.employee_information_email_button, R.id.employee_information_whatsup_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.employee_information_phoneCall_button:
                String number = employee_information.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
                break;
            case R.id.employee_information_email_button:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + employee_information.getEmail_Employee()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RocketechIT");

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

                break;
            case R.id.employee_information_whatsup_button:
                String url = "https://api.whatsapp.com/send?phone=+88" + employee_information.getPhone();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            employeeInformationJoinDateTV2.setText(String.valueOf(day + "-" + month + "-" + year));
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();


            //set user join date in Company_Employee
            databaseReference.child("Company_Employee").child(Employee_Information_F.getUserID())
                    .child(employee_information.getUserID_Employee()).child("join_Date").setValue(employeeInformationJoinDateTV2.getText());
            //set user join date in company profile
            databaseReference2.child("Employee_List").child(employee_information.getUserID_Employee())
                    .child("join_Date").child(employeeInformationJoinDateTV2.getText().toString());
        }
    }

    //get Current Admin User ID
    private static String getUserID() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        return userID;
    }
}
