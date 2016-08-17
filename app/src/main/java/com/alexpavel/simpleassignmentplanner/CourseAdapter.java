package com.alexpavel.simpleassignmentplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alex on 6/25/15. Add new header later.
 */
public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    private List<Course> courses;


    private static class CourseHolder {
        public TextView courseName;
        public TextView courseProf;
        public TextView courseTimes;
    }

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, R.layout.list_layout, courses);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseHolder holder = new CourseHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, parent, false);

            TextView nameTV = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView timesTV = (TextView) convertView.findViewById(R.id.txtTitle2);

            holder.courseName = nameTV;
            holder.courseTimes = timesTV;

            convertView.setTag(holder);
        }
        else {
            holder = (CourseHolder) convertView.getTag();
        }

        Course currentCourse = courses.get(position);
        holder.courseName.setText(currentCourse.getName());
        holder.courseTimes.setText(currentCourse.getStartTime() + " - " + currentCourse.getEndTime());

        return convertView;
    }

}
