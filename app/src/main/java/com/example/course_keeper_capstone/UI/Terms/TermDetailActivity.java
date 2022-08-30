package com.example.course_keeper_capstone.UI.Terms;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.course_keeper_capstone.Adapters.CourseAdapter;
import com.example.course_keeper_capstone.Adapters.TermAdapter;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.EditViewModel;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Courses.AddCourseActivity;
import com.example.course_keeper_capstone.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TERMS = "Extra_Terms_Key";
    private EditText updateName, updateStart, updateEnd;
    private Term mTerm;
    private TermDetailViewModel mTermDetailViewModel;
    private  Toolbar mToolbar;
    @BindView(R.id.recycler_view_term_courses)
    RecyclerView termCoursesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        ButterKnife.bind(this);

        //Inflate options menu on toolbar of the activity
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.detail_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    mTermDetailViewModel.deleteTerm(mTerm);
                    return true;
                }
                return false;
            }
        });


        updateName = findViewById(R.id.term_name_edit_dt);
        updateStart = findViewById(R.id.term_start_edit_dt);
        updateEnd = findViewById(R.id.term_end_edit_dt);
        mTerm = getIntent().getParcelableExtra(EXTRA_TERMS);

        //Set data received from list of terms
        updateName.setText(mTerm.getTermName());
        updateStart.setText(mTerm.getTermStart());
        updateEnd.setText(mTerm.getTermEnd());

        mTermDetailViewModel = new ViewModelProvider(this).get(TermDetailViewModel.class);

        mTermDetailViewModel.isUpdateSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isUpdateSuccess) {
                if (isUpdateSuccess != null) {
                    if (isUpdateSuccess.first) {
                        Toast.makeText(TermDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TermDetailActivity.this, isUpdateSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        updateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar,updateStart.getText().toString());
                new DatePickerDialog(TermDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        updateStart.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        updateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                setDate(calendar,updateEnd.getText().toString());
                new DatePickerDialog(TermDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        updateEnd.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        CourseAdapter adapter = new CourseAdapter(new ArrayList<>(), new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
            }
        });
        termCoursesRecyclerView.setAdapter(adapter);
        mTermDetailViewModel.getTermCourses(mTerm.getTermID(),mTerm.getUserID()).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setUserCourses(courses);
            }
        });
    }



    public void saveTerm(View view) {
        if (!checkDate(updateStart.getText().toString(), updateEnd.getText().toString()) || updateName.getText().toString().trim().isEmpty() ||
                updateStart.getText().toString().trim().isEmpty() || updateEnd.getText().toString().isEmpty()) {
            Toast.makeText(TermDetailActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
        } else {
            mTerm.setTermName(updateName.getText().toString());
            mTerm.setTermStart(updateStart.getText().toString());
            mTerm.setTermEnd(updateEnd.getText().toString());
            mTermDetailViewModel.update(mTerm);
        }

    }

}