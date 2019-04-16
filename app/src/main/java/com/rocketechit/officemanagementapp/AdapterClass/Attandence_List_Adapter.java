package com.rocketechit.officemanagementapp.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Attandence_List_Adapter extends RecyclerView.Adapter<Attandence_List_Adapter.ViewClass> {
    Context context;

    List<String> date_List;
    List<String> entry_Time;
    List<String> exit_Time ;

    //for onClick from java class (Second ....)
    private static ClickListener clickListener;

    public Attandence_List_Adapter(Context context, List<String> date_List, List<String> entry_Time, List<String> exit_Time) {
        this.context = context;

        this.date_List = date_List;
        this.entry_Time = entry_Time;
        this.exit_Time = exit_Time;
    }


    @NonNull
    @Override
    public ViewClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.attandence_item_view, viewGroup, false);

        return new ViewClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClass viewClass, int i) {

        String entry_time = entry_Time.get(i);
        String exit_time = exit_Time.get(i);
        //set Date , Here key list is the day
        viewClass.date_attendance_TV.setText(date_List.get(i));
        if (entry_time == null) {
            viewClass.entryTime_attendance_TV.setText("null");
        } else {
            viewClass.entryTime_attendance_TV.setText(entry_Time.get(i));

        }
        if (exit_time == null) {
            viewClass.exitTime_attendance_TV.setText("null");

        } else {
            viewClass.exitTime_attendance_TV.setText(exit_Time.get(i));

        }

    }

    @Override
    public int getItemCount() {
        return date_List.size();
    }

    //implement interface for onClick from java class (third ....)
    public class ViewClass extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        TextView date_attendance_TV;
        TextView entryTime_attendance_TV;
        TextView exitTime_attendance_TV;

        public ViewClass(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            date_attendance_TV = itemView.findViewById(R.id.date_attendance_TV);
            entryTime_attendance_TV = itemView.findViewById(R.id.entryTime_attendance_TV);
            exitTime_attendance_TV = itemView.findViewById(R.id.exitTime_attendance_TV);
        }

        //Override this method for onClick from java class (fourth ....)
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

    }

    //for onClick from java class
    public void setOnItemClickListener(ClickListener clickListener) {
        Attandence_List_Adapter.clickListener = clickListener;
    }

    //for onClick from java class (First ....)
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
