package com.example.course_keeper_capstone.UI.Instructors;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddInstructorActivity extends AppCompatActivity {
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.spinnerCourse)
    Spinner courseSpinner;
    @BindView(R.id.addInstructorBtn)
    Button addInstructorBtn;
    private com.example.course_keeper_capstone.UI.Instructors.AddInstructorViewModel addInstructorViewModel;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        ButterKnife.bind(this);

        userID = getIntent().getIntExtra("id", -1);
        addInstructorViewModel = new ViewModelProvider(this).get(AddInstructorViewModel.class);

        addInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEt.getText().toString().trim().isEmpty()
                        || emailEt.getText().toString().isEmpty()
                        || phoneEt.getText().toString().isEmpty()
                ) {
                    Toast.makeText(AddInstructorActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                } else if (addInstructorViewModel.courseLD.getValue().isEmpty()) {
                    Toast.makeText(AddInstructorActivity.this, "Please add some courses for adding assessments.", Toast.LENGTH_LONG).show();
                    return;
                } else if (courseSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(AddInstructorActivity.this, "Please select a CourseR to proceed further.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int courseId = addInstructorViewModel.courseLD.getValue().get(courseSpinner.getSelectedItemPosition() - 1).getCourseID();
                    Instructor instructor = new Instructor(nameEt.getText().toString(),
                            emailEt.getText().toString(),
                            phoneEt.getText().toString(),
                            courseId,
                            userID
                    );
                    addInstructorViewModel.addInstructor(instructor);
                }
            }
        });

        addInstructorViewModel.isSaveSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isSaveSuccess) {
                if (isSaveSuccess != null) {
                    if (isSaveSuccess.first) {
                        Toast.makeText(AddInstructorActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddInstructorActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        addInstructorViewModel.courseLD.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses != null) {

                }
            }
        });
        addInstructorViewModel.getCourseTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> termTitles) {
                if (termTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddInstructorActivity.this, android.R.layout.simple_list_item_1, termTitles);
                    courseSpinner.setAdapter(adapter);
                }
            }
        });

    }
}