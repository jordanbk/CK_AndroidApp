package com.example.course_keeper_capstone.UI.Assessments;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ASSESSMENTS = "EXTRA_ASSESSMENTS_KEY";
    int userID;
    @BindView(R.id.assessment_title_et)
    EditText titleEt;
    @BindView(R.id.assessment_start_et)
    EditText startDateEt;
    @BindView(R.id.assessment_end_et)
    EditText endDateEt;
    @BindView(R.id.spinnerCourse)
    Spinner courseSpinner;
    @BindView(R.id.addAssessmentBtn)
    Button addAssessmentBtn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private AssessmentDetailViewModel assessmentDetailViewModel;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        ButterKnife.bind(this);
        //get course data
        Assessment assessment = getIntent().getParcelableExtra(EXTRA_ASSESSMENTS);
        userID = assessment.getUserID();
        assessmentDetailViewModel = ViewModelProviders.of(this,new AssessmentDetailViewModel.AssessmentDetailsViewModelFactory(getApplication(),userID)).get(AssessmentDetailViewModel.class);

        titleEt.setText(assessment.getAssessmentTitle());
        startDateEt.setText(assessment.getAssessmentStartDate());
        endDateEt.setText(assessment.getAssessmentEndDate());

        //Inflate options menu on toolbar of the activity
        mToolbar.inflateMenu(R.menu.detail_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    assessmentDetailViewModel.delete(assessment);
                    return true;
                }
                return false;
            }
        });

        assessmentDetailViewModel.isUpdateSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isUpdateSuccess) {
                if (isUpdateSuccess != null) {
                    if (isUpdateSuccess.first) {
                        Toast.makeText(AssessmentDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AssessmentDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        startDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar, startDateEt.getText().toString());
                new DatePickerDialog(AssessmentDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        startDateEt.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar, endDateEt.getText().toString());
                new DatePickerDialog(AssessmentDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        endDateEt.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDate(startDateEt.getText().toString(), endDateEt.getText().toString())
                        || startDateEt.getText().toString().trim().isEmpty()
                        || endDateEt.getText().toString().isEmpty()
                        || titleEt.getText().toString().isEmpty()
                ) {
                    Toast.makeText(AssessmentDetailActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                } else if (assessmentDetailViewModel.courseLD.getValue().isEmpty()) {
                    Toast.makeText(AssessmentDetailActivity.this, "Please add some courses for adding assessments.", Toast.LENGTH_LONG).show();
                } else if (courseSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(AssessmentDetailActivity.this, "Please select a CourseR to proceed further.", Toast.LENGTH_LONG).show();
                } else {
                    int courseId = assessmentDetailViewModel.courseLD.getValue().get(courseSpinner.getSelectedItemPosition() - 1).getCourseID();
                    Assessment lAssessment = new Assessment(
                            assessment.getAssessmentID(),
                            titleEt.getText().toString(),
                            startDateEt.getText().toString(),
                            endDateEt.getText().toString(),
                            courseId,
                            userID
                    );
                    assessmentDetailViewModel.updateAssessment(lAssessment);
                }
            }
        });

        assessmentDetailViewModel.courseLD.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses != null) {
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getCourseID() == assessment.getCourseID_FK()) {
                            currentPosition = i;
                            break;
                        }
                    }
                }
            }
        });
        assessmentDetailViewModel.getCourseTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> courseTitles) {
                if (courseTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssessmentDetailActivity.this, android.R.layout.simple_list_item_1, courseTitles);
                    courseSpinner.setAdapter(adapter);
                    courseSpinner.setSelection(currentPosition + 1);
                }
            }
        });

    }

}