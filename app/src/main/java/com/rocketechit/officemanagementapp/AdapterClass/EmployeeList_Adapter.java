package com.rocketechit.officemanagementapp.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rocketechit.officemanagementapp.JavaClass.Employee_Information;
import com.rocketechit.officemanagementapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeList_Adapter extends RecyclerView.Adapter<EmployeeList_Adapter.ViewClass> {
    Context context;
    List<Employee_Information>employee_informations;
    //for onClick from java class (Second ....)
    private static ClickListener clickListener;

    public EmployeeList_Adapter(Context context, List<Employee_Information> employee_informations) {
        this.context = context;
        this.employee_informations = employee_informations;
    }

    @NonNull
    @Override
    public ViewClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.employee_list_item, null);

        return new ViewClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClass viewClass, int i) {
        String imageUrl=employee_informations.get(i).getImageLink();
        Picasso.get().load(imageUrl).error( R.drawable.man )
                .placeholder( R.drawable.progress_animation ).into(viewClass.imageView);

        viewClass.nameTV.setText(employee_informations.get(i).getName_Employee());

    }

    @Override
    public int getItemCount() {
        return employee_informations.size();
    }

    //implement interface for onClick from java class (third ....)
    public class ViewClass extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        ImageView imageView;
        TextView nameTV;

        public ViewClass(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            imageView = itemView.findViewById(R.id.employee_image_item);
            nameTV = itemView.findViewById(R.id.employee_name_item);
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
        EmployeeList_Adapter.clickListener = clickListener;
    }

    //for onClick from java class (First ....)
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }


}
