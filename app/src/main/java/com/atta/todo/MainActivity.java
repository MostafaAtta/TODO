package com.atta.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerview_tasks);

        getTasks();
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {


            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() != 0) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                            LinearLayoutManager.VERTICAL, false));
                    TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(MainActivity.this, "No tasks added till now", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}