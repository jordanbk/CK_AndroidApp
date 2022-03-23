package com.example.course_keeper_capstone.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.course_keeper_capstone.Adapters.CourseAdapter;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class TermDetailActivity extends AppCompatActivity {
    private Repository repo;

    int userID;
    int termID;
    String termName;
    String termStart;
    String termEnd;
    private static int numCourses;
    EditText updateName;
    EditText updateStart;
    EditText updateEnd;

    Term termSelected;

    Calendar termStartCal = Calendar.getInstance();
    Calendar termEndCal = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener termStartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        // get term data
        termID = getIntent().getIntExtra("termID", -1);
        userID = getIntent().getIntExtra("userID", -1);

        // repository instance
        repo = new Repository(getApplication());
        List<Term> allTerms = repo.getAllTerms();

        // get selected term
        for(Term term : allTerms) {
            if(term.getTermID() == termID) {
                termSelected = term;
                termName = termSelected.getTermName();
                termStart = termSelected.getTermStart();
                termEnd = termSelected.getTermEnd();
            }
        }

        // associate editText variables with view ID's
        updateName = findViewById(R.id.term_name_edit_dt);
        updateStart = findViewById(R.id.term_start_edit_dt);
        updateEnd = findViewById(R.id.term_start_edit_dt);

        // populate fields with selected terms data
        if(termID!=-1) {
            updateName.setText(termName);
            updateStart.setText(termStart);
            updateEnd.setText(termEnd);
        }

        // initialize calendar object for start date
        termStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                termStartCal.set(Calendar.YEAR, year);
                termStartCal.set(Calendar.MONTH, month);
                termStartCal.set(Calendar.DAY_OF_MONTH, day);
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);

                showStartDate();
            }

            private void showStartDate() {
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                updateStart.setText(sdf.format(termStartCal.getTime()));
            }

        };

        // initialize calendar object for end date
        termEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                termEndCal.set(Calendar.YEAR, year);
                termEndCal.set(Calendar.MONTH, month);
                termEndCal.set(Calendar.DAY_OF_MONTH, day);
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);

                showEndDate();
            }

            private void showEndDate() {
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);

                updateEnd.setText(format.format(termEndCal.getTime()));
            }

        };

        updateStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(TermDetailActivity.this, termStartDate, termStartCal
                        .get(Calendar.YEAR), termStartCal.get(Calendar.MONTH),
                        termStartCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        updateEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(TermDetailActivity.this, termEndDate, termEndCal
                        .get(Calendar.YEAR), termEndCal.get(Calendar.MONTH),
                        termEndCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // populate recyclerview with associated courses
        repo = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recycler_view_terms_dt);
        final CourseAdapter courseAdapter = new CourseAdapter(TermDetailActivity.this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> associateCourses = new ArrayList<>();
        List<Course> allCourses = repo.getAllCourses();
        for(Course c : repo.getAllCourses()){
            if(c.getTermID_FK() == termID){
                associateCourses.add(c);
            }
        }
        courseAdapter.setCourses(associateCourses);
        numCourses = associateCourses.size();

    }

    /**
     *  Validate start and end dates
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean checkDate(String startDate, String endDate){
        try
        {
            String dateFormat= "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date endD = sdf.parse(endDate);
            Date startD = sdf.parse(startDate);
            if(endD.after(startD))
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;

        }
    }

    @OnClick(R.id.save_term_dt)
    public void saveTerm(){
        Term term;
        if (!checkDate(updateStart.getText().toString(), updateEnd.getText().toString()) ||
                updateStart.getText().toString().trim().isEmpty() ||
                updateEnd.getText().toString().trim().isEmpty() || updateEnd.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields and make the start date is before the end date", Toast.LENGTH_LONG).show();
            return;
        }/*
        else if (termID != -1)
            term = new Term(termID, updateName.getText().toString(),
                    updateStart.getText().toString(),updateEnd.getText().toString());*/
        else {
/*            List<Term> allTerms = repo.getAllTerms();
            termID = allTerms.get(allTerms.size() - 1).getTermID();*/
            term = new Term(termID, updateName.getText().toString(), updateStart.getText().toString(),
                    updateEnd.getText().toString(), userID);
        }

        repo.update(term);
        Toast.makeText(getApplicationContext(), "Term updated!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TermDetailActivity.this,TermActivity.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

}