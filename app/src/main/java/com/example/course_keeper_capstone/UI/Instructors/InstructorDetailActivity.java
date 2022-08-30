package com.example.course_keeper_capstone.UI.Instructors;

import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructorDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INSTRUCTORS = "extra_instructors_key";
    int userID;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.spinnerCourse)
    Spinner courseSpinner;
    @BindView(R.id.updateInstructorBtn)
    Button updateInstructorBtn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private com.example.course_keeper_capstone.UI.Instructors.InstructorDetailViewModel instructorDetailViewModel;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail);
        ButterKnife.bind(this);
        //get course data
        Instructor instructor = getIntent().getParcelableExtra(EXTRA_INSTRUCTORS);
        userID = instructor.getUserID();
        instructorDetailViewModel = ViewModelProviders.of(this,new InstructorDetailViewModel.InstructorDetailViewModelFactory(getApplication(),userID)).get(InstructorDetailViewModel.class);

        nameEt.setText(instructor.getInstructorName());
        emailEt.setText(instructor.getInstructorEmail());
        phoneEt.setText(instructor.getInstructorPhone());

        //Inflate options menu on toolbar of the activity
        mToolbar.inflateMenu(R.menu.detail_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    instructorDetailViewModel.delete(instructor);
                    return true;
                }
                return false;
            }
        });

        instructorDetailViewModel.isUpdateSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isUpdateSuccess) {
                if (isUpdateSuccess != null) {
                    if (isUpdateSuccess.first) {
                        Toast.makeText(InstructorDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(InstructorDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        updateInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEt.getText().toString().trim().isEmpty()
                        || emailEt.getText().toString().isEmpty()
                        || phoneEt.getText().toString().isEmpty()
                ) {
                    Toast.makeText(InstructorDetailActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                } else if (instructorDetailViewModel.courseLD.getValue().isEmpty()) {
                    Toast.makeText(InstructorDetailActivity.this, "Please add some courses for adding assessments.", Toast.LENGTH_LONG).show();
                } else if (courseSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(InstructorDetailActivity.this, "Please select a CourseR to proceed further.", Toast.LENGTH_LONG).show();
                } else {
                    int courseId = instructorDetailViewModel.courseLD.getValue().get(courseSpinner.getSelectedItemPosition() - 1).getCourseID();
                    Instructor localInstructor = new Instructor(
                            instructor.getInstructorID(),
                            nameEt.getText().toString(),
                            emailEt.getText().toString(),
                            phoneEt.getText().toString(),
                            courseId,
                            userID
                    );
                    instructorDetailViewModel.updateInstructor(localInstructor);
                }
            }
        });

        instructorDetailViewModel.courseLD.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses != null) {
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getCourseID() == instructor.getCourseID()) {
                            currentPosition = i;
                            break;
                        }
                    }
                }
            }
        });
        instructorDetailViewModel.getCourseTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> courseTitles) {
                if (courseTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(InstructorDetailActivity.this, android.R.layout.simple_list_item_1, courseTitles);
                    courseSpinner.setAdapter(adapter);
                    courseSpinner.setSelection(currentPosition + 1);
                }
            }
        });

    }


}