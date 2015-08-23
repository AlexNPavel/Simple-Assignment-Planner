package com.example.alex.contactstest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DasList extends AppCompatActivity {

    CourseAdapter mAdapter;
    List<Course> courseList;
    Context context = this;
    public final static String EXTRA_COURSE = "com.example.alex.contactstest.COURSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_das_list);
        ListView lv = (ListView) findViewById(R.id.listView);
        //Toast.makeText(DasList.this, Arrays.toString(courseFoldList), Toast.LENGTH_LONG).show();

        // Create a progress bar to display while the list loads
        //ProgressBar progressBar = new ProgressBar(this);
        //progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
        //        LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        //progressBar.setIndeterminate(true);
        //lv.setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        //ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        //root.addView(progressBar);

        //Toast.makeText(DasList.this, "In IF", Toast.LENGTH_LONG).show();
        courseList = new ArrayList<>();
        CourseDBHelper dbHelper = new CourseDBHelper(this);
        Cursor res = dbHelper.getCourses();
        res.moveToFirst();
        //Toast.makeText(this, "RES COUNT " + res.getCount(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < res.getCount(); i++) {
            courseList.add(new Course(res.getInt(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_ID)), dbHelper));
            res.moveToNext();
        }
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new CourseAdapter(this, courseList);
        lv.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabbutton);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                Intent intent = new Intent(context, Assignments.class);
                int courseChoice = courseList.get(position).getID();
                intent.putExtra(EXTRA_COURSE, courseChoice);
                startActivity(intent);
            }

        });

        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_das_list, menu);
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
        AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle("Options for " + courseList.get(aInfo.position).getName());
        menu.add(1, 1, 1, "Details");
        menu.add(1, 2, 2, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String itemTitle = (String) item.getTitle();
        // Implements our logic
        if (itemTitle.equals("Details")) {
            Toast.makeText(this, "Item id [" + item.getItemId() + "]" + " and location: "+item.getGroupId(), Toast.LENGTH_SHORT).show();
        } else if (itemTitle.equals("Delete")){
            AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
            Context context = this;
            String path = context.getFilesDir().getAbsolutePath();
            File folderToDel = new File (path + "/" + courseList.get(aInfo.position).getName());
            String[] courseFoldList = folderToDel.list();
            for (String thing : courseFoldList) {
                (new File(path + "/" + courseList.get(aInfo.position).getName() + "/" + thing)).delete();
            }
            boolean folderDeleted = folderToDel.delete();
            if (folderDeleted) {
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
            System.out.println("Going to run onResume");
            onResume();
            System.out.println("Ran onResume");
        }
        return true;
    }



    public void newCourse(View view) {
        Intent intent = new Intent(this, NewCourse.class);
        startActivity(intent);
    }

}
