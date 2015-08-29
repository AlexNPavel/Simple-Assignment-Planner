package com.example.alex.contactstest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Assignments extends AppCompatActivity {

    int course;
    AssignmentAdapter mAdapter;
    List<Assignment> assignmentList;
    CourseDBHelper dbHelper;
    Cursor nameRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das_list);
        Intent intent = getIntent();
        course = intent.getIntExtra("course", 1);
        dbHelper = new CourseDBHelper(this);
        Cursor res = dbHelper.getCourse(course);
        res.moveToFirst();
        setTitle("Assignments for " + res.getString(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_NAME)));
    }

    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_das_list);
        ListView lv = (ListView) findViewById(R.id.listView);

        //Toast.makeText(DasList.this, Arrays.toString(classFileList), Toast.LENGTH_LONG).show();

        assignmentList = new ArrayList<>();
        Cursor res = dbHelper.getCourseAssignments(course);
        res.moveToFirst();
        //Toast.makeText(this, "RES COUNT " + res.getCount(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < res.getCount(); i++) {
            assignmentList.add(new Assignment(res.getInt(res.getColumnIndex(CourseDBHelper.LINK_ASSIGN_ID)), dbHelper, this));
            res.moveToNext();
        }

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new AssignmentAdapter(this, assignmentList);
        lv.setAdapter(mAdapter);


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabbutton);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                Toast.makeText(Assignments.this, "Item with id [" + id + "] - Position [" + position + "]", Toast.LENGTH_SHORT).show();
            }

        });

        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle("Options for " + assignmentList.get(aInfo.position).getName());
        menu.add(1, 1, 1, "Edit");
        menu.add(1, 2, 2, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item) {
        String itemTitle = (String) item.getTitle();
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (itemTitle.equals("Edit")) {
            Intent intent = new Intent(this, NewAssignment.class);
            intent.putExtra("isNew", false);
            intent.putExtra("assignID", assignmentList.get(aInfo.position).getID());
            intent.putExtra("courseID", course);
            startActivity(intent);
        } else if (itemTitle.equals("Delete")){
            int removalID = assignmentList.get(aInfo.position).getID();
            boolean deleted = dbHelper.deleteAssignment(removalID);
            if (deleted) {
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
            onResume();
        }
        return true;
    }

    public void newCourse(View view) {
        Intent intent = new Intent(this, NewAssignment.class);
        intent.putExtra("courseID", course);
        startActivity(intent);
    }
}
