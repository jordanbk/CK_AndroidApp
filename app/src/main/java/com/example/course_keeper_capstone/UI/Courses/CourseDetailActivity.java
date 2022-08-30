package com.example.course_keeper_capstone.UI.Courses;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course_keeper_capstone.Adapters.AssessmentAdapter;
import com.example.course_keeper_capstone.Adapters.CourseAdapter;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.example.course_keeper_capstone.UI.Terms.TermDetailActivity;
import com.example.course_keeper_capstone.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_COURSES = "EXTRA_COURSES_KEY";
    int userID;
    int courseID;
    @BindView(R.id.course_name_edit_dt)
    EditText courseNameEdt;
    @BindView(R.id.course_start_edit_dt)
    EditText courseStartEdt;
    @BindView(R.id.course_end_edit_dt)
    EditText courseEndEdt;
    @BindView(R.id.course_status_edit_dt)
    EditText courseStatusEdt;
    @BindView(R.id.course_notes_edit_dt)
    EditText courseNotesEdt;
    @BindView(R.id.spinnerTerm)
    Spinner termSpinner;
    @BindView(R.id.updateCourseBtn)
    Button updateCourseBtn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView assessmentRecyclerView;
    private CourseDetailViewModel mCourseDetailViewModel;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);
        //get course data
        Course course = getIntent().getParcelableExtra(EXTRA_COURSES);
        userID = course.getUserID();
        courseID = course.getCourseID();

        mCourseDetailViewModel = ViewModelProviders.of(this,new CourseDetailViewModel.CourseDetailViewModelFactory(getApplication(),userID)).get(CourseDetailViewModel.class);

        courseNameEdt.setText(course.getCourseTitle());
        courseStartEdt.setText(course.getCourseStart());
        courseEndEdt.setText(course.getCourseEnd());
        courseStatusEdt.setText(course.getCourseStatus());
        courseNotesEdt.setText(course.getCourseNotes());

        //Inflate options menu on toolbar of the activity
        mToolbar.inflateMenu(R.menu.detail_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    mCourseDetailViewModel.delete(course);
                    return true;
                }
                return false;
            }
        });

        mCourseDetailViewModel.isUpdateSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isUpdateSuccess) {
                if (isUpdateSuccess != null) {
                    if (isUpdateSuccess.first) {
                        Toast.makeText(CourseDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CourseDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        courseStartEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar, courseStartEdt.getText().toString());
                new DatePickerDialog(CourseDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        courseStartEdt.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseEndEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar, courseEndEdt.getText().toString());
                new DatePickerDialog(CourseDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        courseEndEdt.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDate(courseStartEdt.getText().toString(), courseEndEdt.getText().toString())
                        || courseNameEdt.getText().toString().trim().isEmpty()
                        || courseStartEdt.getText().toString().trim().isEmpty()
                        || courseEndEdt.getText().toString().isEmpty()
                        || courseStatusEdt.getText().toString().trim().isEmpty()
                        || courseNotesEdt.getText().toString().trim().isEmpty()
                ) {
                    Toast.makeText(CourseDetailActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                } else if (mCourseDetailViewModel.termLD.getValue().isEmpty()) {
                    Toast.makeText(CourseDetailActivity.this, "Please add some terms for adding courses.", Toast.LENGTH_LONG).show();
                    return;
                } else if (termSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(CourseDetailActivity.this, "Please select a TermR to proceed further.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int termId = mCourseDetailViewModel.termLD.getValue().get(termSpinner.getSelectedItemPosition() - 1).getTermID();
                    Course localCourse = new Course(course.getCourseID(), courseNameEdt.getText().toString(),
                            courseStatusEdt.getText().toString(),
                            courseStartEdt.getText().toString(),
                            courseEndEdt.getText().toString(),
                            courseNotesEdt.getText().toString(),
                            termId,
                            userID
                    );
                    mCourseDetailViewModel.update(localCourse);
                }
            }
        });

        mCourseDetailViewModel.termLD.observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                if (terms != null) {
                    for (int i = 0; i < terms.size(); i++) {
                        if (terms.get(i).getTermID() == course.getTermID_FK()) {
                            currentPosition = i;
                            break;
                        }
                    }
                }
            }
        });
        mCourseDetailViewModel.getTermsTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> termTitles) {
                if (termTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseDetailActivity.this, android.R.layout.simple_list_item_1, termTitles);
                    termSpinner.setAdapter(adapter);
                    termSpinner.setSelection(currentPosition + 1);
                }
            }
        });

        AssessmentAdapter adapter = new AssessmentAdapter(new ArrayList<>(), new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
            }
        });
        assessmentRecyclerView.setAdapter(adapter);
        mCourseDetailViewModel.getAssessmentsForCourse(course.getCourseID(),course.getUserID()).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setUserAssessment(assessments);
            }
        });
    }

}