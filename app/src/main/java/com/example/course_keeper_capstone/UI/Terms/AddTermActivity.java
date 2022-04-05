package com.example.course_keeper_capstone.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.EditViewModel;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.TermViewModel;
import com.example.course_keeper_capstone.UI.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity {
    private EditText termNameEdt, termStartEdt, termEndEdt;
    private Button addTermBtn;
    Term term = new Term();
    Calendar termStartCal = Calendar.getInstance();
    Calendar termEndCal = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener termStartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    String dateFormat = "MM/dd/yyyy";

    private TermViewModel termViewModel;

    int userID;
    private Repository repo;

    int termId;
    String termName;
    String termStart;
    String termEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        repo = new Repository(getApplication());

        userID = getIntent().getIntExtra("id", -1);

        termNameEdt = findViewById(R.id.term_name_edit);
        termStartEdt = findViewById(R.id.term_start_edit);
        termEndEdt = findViewById(R.id.term_end_edit);

        addTermBtn = findViewById(R.id.save_term);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

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

                termStartEdt.setText(sdf.format(termStartCal.getTime()));
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

                termEndEdt.setText(format.format(termEndCal.getTime()));
            }

        };

        termStartEdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddTermActivity.this, termStartDate, termStartCal
                        .get(Calendar.YEAR), termStartCal.get(Calendar.MONTH),
                        termStartCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termEndEdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddTermActivity.this, termEndDate, termEndCal
                        .get(Calendar.YEAR), termEndCal.get(Calendar.MONTH),
                        termEndCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term term;
                if (!checkDate(termStartEdt.getText().toString(), termEndEdt.getText().toString()) || termNameEdt.getText().toString().trim().isEmpty() ||
                        termStartEdt.getText().toString().trim().isEmpty() || termEndEdt.getText().toString().isEmpty()) {
                    Toast.makeText(AddTermActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
/*                    term = new Term(++termId, termNameEdt.getText().toString(), termStartEdt.getText().toString(),
                            termEndEdt.getText().toString(), userID);*/
                    termViewModel.saveUserTerm(termNameEdt.getText().toString(), termStartEdt.getText().toString(),
                            termEndEdt.getText().toString(), userID);
                }

                Intent intent = new Intent(AddTermActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
    // validate Dates
    public static boolean checkDate(String startDate, String endDate){
        try
        {
            String dateFormat= "MM/dd/yyyy";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date endD = sdf.parse(endDate);
            Date startD = sdf.parse(startDate);
            assert endD != null;
            return endD.after(startD);
        }
        catch (Exception e){
            return false;

        }
    }

/*    public void saveAndGoBack(){
        String name = termNameEdt.getText().toString();
        String startDate = termStartEdt.getText().toString();
        String endDate = termEndEdt.getText().toString();
        editViewModel.saveTerm(name, startDate, endDate);
        finish();
    }*/
}