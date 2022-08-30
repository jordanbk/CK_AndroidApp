package com.example.course_keeper_capstone.UI.Courses;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.AddTermActivity;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.example.course_keeper_capstone.Util.Constants;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourseActivity extends AppCompatActivity {
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
    @BindView(R.id.save_course_dt)
    Button addCourseBtn;
    private AddCourseViewModel mAddCourseViewModel;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);

        userID = getIntent().getIntExtra("id", -1);
        mAddCourseViewModel = new ViewModelProvider(this).get(AddCourseViewModel.class);

        courseStartEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar,courseStartEdt.getText().toString());
                new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                setDate(calendar,courseEndEdt.getText().toString());
                new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDate(courseStartEdt.getText().toString(), courseEndEdt.getText().toString())
                        || courseNameEdt.getText().toString().trim().isEmpty()
                        || courseStartEdt.getText().toString().trim().isEmpty()
                        || courseEndEdt.getText().toString().isEmpty()
                        || courseStatusEdt.getText().toString().trim().isEmpty()
                        || courseNotesEdt.getText().toString().trim().isEmpty()
                ) {
                    Toast.makeText(AddCourseActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                }else if(mAddCourseViewModel.termLD.getValue().isEmpty()){
                    Toast.makeText(AddCourseActivity.this, "Please add some terms for adding courses.", Toast.LENGTH_LONG).show();
                    return;
                }else if(termSpinner.getSelectedItemPosition()==0){
                    Toast.makeText(AddCourseActivity.this, "Please select a TermR to proceed further.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    int termId = mAddCourseViewModel.termLD.getValue().get(termSpinner.getSelectedItemPosition()-1).getTermID();
                    Course course = new Course(courseNameEdt.getText().toString(),
                            courseStatusEdt.getText().toString(),
                            courseStartEdt.getText().toString(),
                            courseEndEdt.getText().toString(),
                            courseNotesEdt.getText().toString(),
                            termId,
                            userID
                    );
                    mAddCourseViewModel.addCourse(course);
                }
            }
        });

        mAddCourseViewModel.isSaveSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isSaveSuccess) {
                if(isSaveSuccess!=null){
                    if (isSaveSuccess.first) {
                        Toast.makeText(AddCourseActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddCourseActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mAddCourseViewModel.termLD.observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                if(terms!=null){

                }
            }
        });
        mAddCourseViewModel.getTermsTitles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> termTitles) {
                if(termTitles!=null){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_list_item_1,termTitles);
                    termSpinner.setAdapter(adapter);
                }
            }
        });

    }
}