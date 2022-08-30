package com.example.course_keeper_capstone.UI.Base;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Assessments.AssessmentActivity;
import com.example.course_keeper_capstone.UI.Base.HomeActivity;
import com.example.course_keeper_capstone.UI.Courses.CourseActivity;
import com.example.course_keeper_capstone.UI.Instructors.InstructorActivity;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;
import com.example.course_keeper_capstone.UI.Assessments.AssessmentActivity;
import com.example.course_keeper_capstone.UI.Courses.CourseActivity;
import com.example.course_keeper_capstone.UI.Instructors.InstructorActivity;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;

public class BaseActivity extends AppCompatActivity {

    protected boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intentHome = new Intent(this, HomeActivity.class);
                startActivity(intentHome);
                finish();
                return true;
            case R.id.nav_terms:
                Intent intentTerms = new Intent(this, TermActivity.class);
                startActivity(intentTerms);
                finish();
                return true;
            case R.id.nav_courses:
                Intent intentCourses = new Intent(this, CourseActivity.class);
                startActivity(intentCourses);
                finish();
                return true;
            case R.id.nav_assessments:
                Intent intentAssessments = new Intent(this, AssessmentActivity.class);
                startActivity(intentAssessments);
                finish();
                return true;
            case R.id.nav_instructors:
                Intent intentInstructors = new Intent(this, InstructorActivity.class);
                startActivity(intentInstructors);
                finish();
                return true;
            default:
                //default intent
                return false;
        }
    }
}
