package com.atta.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateTaskActivity extends AppCompatActivity {

    Task task;

    ImageView deleteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        task = (Task) getIntent().getSerializableExtra("task");

        String taskName = task.getTask();



        deleteTask = findViewById(R.id.imageView);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });

    }


    private void deleteTask(){

        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }


            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(UpdateTaskActivity.this)
                        .getAppDatabase().taskDao().delete(task);

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent(UpdateTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        DeleteTask task = new DeleteTask();
        task.execute();
    }

}