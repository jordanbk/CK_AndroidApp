package com.example.course_keeper_capstone.UI.reports;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Util.Util;
import com.example.course_keeper_capstone.databinding.ActivityReportBinding;
import com.example.course_keeper_capstone.UI.reports.adapters.AssessmentAdapter;
import com.example.course_keeper_capstone.UI.reports.adapters.CourseAdapter;
import com.example.course_keeper_capstone.UI.reports.adapters.InstructorAdapter;
import com.example.course_keeper_capstone.UI.reports.adapters.TermAdapter;
import com.example.course_keeper_capstone.UI.reports.models.AssessmentR;
import com.example.course_keeper_capstone.UI.reports.models.CourseR;
import com.example.course_keeper_capstone.UI.reports.models.InstructorR;
import com.example.course_keeper_capstone.UI.reports.models.TermR;

import java.util.List;


public class ReportActivity extends AppCompatActivity {

    private ActivityReportBinding binding;
    private ReportViewModel mReportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);


        setSupportActionBar(binding.toolbar);
        mReportViewModel = new ViewModelProvider(this, new ReportViewModel.ReportViewModelFactory(getApplication(), Util.LoginPrefs.getCurrentUserId(ReportActivity.this))).get(ReportViewModel.class);

        TermAdapter termAdapter = new TermAdapter();
        binding.reportContentLayout.termsRV.setAdapter(termAdapter);
        mReportViewModel.termsLD.observe(this, new Observer<List<TermR>>() {
            @Override
            public void onChanged(List<TermR> terms) {
                if (terms != null && !terms.isEmpty()) {
                    terms.add(0, new TermR(-1, "Title", "Start Date", "End Date"));
                    termAdapter.submitList(terms);
                }
            }
        });

        CourseAdapter courseAdapter = new CourseAdapter();
        binding.reportContentLayout.coursesRV.setAdapter(courseAdapter);
        mReportViewModel.coursesLD.observe(this, new Observer<List<CourseR>>() {
            @Override
            public void onChanged(List<CourseR> courses) {
                if (courses != null && !courses.isEmpty()) {
                    courses.add(0, new CourseR("Title", "", "Start Date", "End Date", "", "Term"));
                    courseAdapter.submitList(courses);
                }
            }
        });

        AssessmentAdapter assessmentAdapter = new AssessmentAdapter();
        binding.reportContentLayout.assessmentsRv.setAdapter(assessmentAdapter);
        mReportViewModel.assessmentsLD.observe(this, new Observer<List<AssessmentR>>() {
            @Override
            public void onChanged(List<AssessmentR> assessments) {
                if (assessments != null && !assessments.isEmpty()) {
                    assessments.add(0, new AssessmentR("Title", "Start Date", "End Date", "Course"));
                    assessmentAdapter.submitList(assessments);
                }
            }
        });

        InstructorAdapter instructorAdapter = new InstructorAdapter();
        binding.reportContentLayout.instructorsRv.setAdapter(instructorAdapter);
        mReportViewModel.instructorsLD.observe(this, new Observer<List<InstructorR>>() {
            @Override
            public void onChanged(List<InstructorR> instructors) {
                if (instructors != null && !instructors.isEmpty()) {
                    instructors.add(0, new InstructorR("Name", "Email", "Phone", "Course"));
                    instructorAdapter.submitList(instructors);
                }
            }
        });

    }
}