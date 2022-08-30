package com.example.course_keeper_capstone.UI.Assessments;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class AddAssessmentActivity extends AppCompatActivity {
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
    private AddAssessmentViewModel addAssessmentViewModel;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        ButterKnife.bind(this);

        userID = getIntent().getIntExtra("id", -1);
        addAssessmentViewModel = new ViewModelProvider(this).get(AddAssessmentViewModel.class);

        startDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar, startDateEt.getText().toString());
                new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(AddAssessmentActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                } else if (addAssessmentViewModel.courseLD.getValue().isEmpty()) {
                    Toast.makeText(AddAssessmentActivity.this, "Please add some courses for adding assessments.", Toast.LENGTH_LONG).show();
                    return;
                } else if (courseSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(AddAssessmentActivity.this, "Please select a CourseR to proceed further.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int courseId = addAssessmentViewModel.courseLD.getValue().get(courseSpinner.getSelectedItemPosition() - 1).getCourseID();
                    Assessment assessment = new Assessment(titleEt.getText().toString(),
                            startDateEt.getText().toString(),
                            endDateEt.getText().toString(),
                            courseId,
                            userID
                    );
                    addAssessmentViewModel.addAssessment(assessment);
                }
            }
        });

        addAssessmentViewModel.isSaveSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isSaveSuccess) {
                if (isSaveSuccess != null) {
                    if (isSaveSuccess.first) {
                        Toast.makeText(AddAssessmentActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddAssessmentActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        addAssessmentViewModel.courseLD.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses != null) {

                }
            }
        });
        addAssessmentViewModel.getCourseTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> termTitles) {
                if (termTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddAssessmentActivity.this, android.R.layout.simple_list_item_1, termTitles);
                    courseSpinner.setAdapter(adapter);
                }
            }
        });

    }
}