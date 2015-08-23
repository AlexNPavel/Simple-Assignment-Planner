package com.example.alex.contactstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alex on 7/22/15. Add new header later.
 */
public class AssignmentAdapter extends ArrayAdapter<Assignment> {

    private Context context;
    private List<Assignment> assignments;


    private static class AssignmentHolder {
        public TextView assignmentName;
        public TextView dueDate;
    }

    public AssignmentAdapter(Context context, List<Assignment> assignments) {
        super(context, R.layout.list_layout, assignments);
        this.context = context;
        this.assignments = assignments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssignmentHolder holder = new AssignmentHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, parent, false);

            TextView nameTV = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView timesTV = (TextView) convertView.findViewById(R.id.txtTitle2);

            holder.assignmentName = nameTV;
            holder.dueDate = timesTV;

            convertView.setTag(holder);
        }
        else {
            holder = (AssignmentHolder) convertView.getTag();
        }

        Assignment currentAssignment = assignments.get(position);
        holder.assignmentName.setText(currentAssignment.getName());
        holder.dueDate.setText(currentAssignment.getDueDate() + " " + currentAssignment.getDueTime());

        return convertView;
    }

}
