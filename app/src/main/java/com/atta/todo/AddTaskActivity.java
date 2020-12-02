package com.atta.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    EditText editTextTask, editTextDesc, editTextFinishBy;

    Button saveBtn;

    String sTask, sDesc, sFinishBy;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);

        progressBar = findViewById(R.id.progress_circular);

        saveBtn = findViewById(R.id.button_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });

    }

    private void saveTask(){
        sTask = editTextTask.getText().toString().trim();
        sDesc = editTextDesc.getText().toString().trim();
        sFinishBy = editTextFinishBy.getText().toString().trim();

        if(!verify()){
            return;
        }

        class AddTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

            }


            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishBy);
                task.setFinished(false);

                DatabaseClient.getInstance(AddTaskActivity.this)
                        .getAppDatabase().taskDao().insert(task);

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        AddTask task = new AddTask();
        task.execute();
    }

    private boolean verify(){
        boolean valid = true;
        if (sTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            valid = false;
        }else  if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            valid = false;
        }else  if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            valid = false;
        }

        return valid;

    }

}