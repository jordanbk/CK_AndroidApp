package com.example.course_keeper_capstone.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.course_keeper_capstone.Util.Util.checkDate;
import static com.example.course_keeper_capstone.Util.Util.setDate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity {
    private EditText termNameEdt, termStartEdt, termEndEdt;
    private Button addTermBtn;
    Calendar termStartCal = Calendar.getInstance();
    Calendar termEndCal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener termStartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    private AddTermViewModel addTermViewModel;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        userID = getIntent().getIntExtra("id", -1);

        termNameEdt = findViewById(R.id.term_name_edit);
        termStartEdt = findViewById(R.id.term_start_edit);
        termEndEdt = findViewById(R.id.term_end_edit);
        addTermBtn = findViewById(R.id.save_term);

        addTermViewModel = new ViewModelProvider(this).get(AddTermViewModel.class);

        // initialize calendar object for start date
        termStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                termStartCal.set(Calendar.YEAR, year);
                termStartCal.set(Calendar.MONTH, month);
                termStartCal.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                termStartEdt.setText(format.format(termStartCal.getTime()));
            }
        };

        // initialize calendar object for end date
        termEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                termEndCal.set(Calendar.YEAR, year);
                termEndCal.set(Calendar.MONTH, month);
                termEndCal.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                termEndEdt.setText(format.format(termEndCal.getTime()));
            }
        };

        termStartEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(termStartCal,termStartEdt.getText().toString());
                new DatePickerDialog(AddTermActivity.this, termStartDate, termStartCal
                        .get(Calendar.YEAR), termStartCal.get(Calendar.MONTH),
                        termStartCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termEndEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(termEndCal,termEndEdt.getText().toString());
                new DatePickerDialog(AddTermActivity.this, termEndDate, termEndCal
                        .get(Calendar.YEAR), termEndCal.get(Calendar.MONTH),
                        termEndCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDate(termStartEdt.getText().toString(), termEndEdt.getText().toString()) || termNameEdt.getText().toString().trim().isEmpty() ||
                        termStartEdt.getText().toString().trim().isEmpty() || termEndEdt.getText().toString().isEmpty()) {
                    Toast.makeText(AddTermActivity.this, "Please make sure all fields are filled out", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    addTermViewModel.saveUserTerm(new Term(termNameEdt.getText().toString(), termStartEdt.getText().toString(),
                            termEndEdt.getText().toString(), userID));
                }
            }
        });

        addTermViewModel.isSaveSuccessLD.observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> isSaveSuccess) {
                if (isSaveSuccess != null) {
                    if (isSaveSuccess.first) {
                        Toast.makeText(AddTermActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddTermActivity.this, isSaveSuccess.second, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}