package com.example.course_keeper_capstone.UI.Courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.course_keeper_capstone.Adapters.CourseAdapter;
import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Assessments.AssessmentActivity;
import com.example.course_keeper_capstone.UI.Base.BaseActivity;
import com.example.course_keeper_capstone.UI.Base.HomeActivity;
import com.example.course_keeper_capstone.UI.Instructors.InstructorActivity;
import com.example.course_keeper_capstone.UI.Terms.TermActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseActivity extends BaseActivity {
    @BindView(R.id.recycler_view_courses)
    RecyclerView recyclerViewCourses;
    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    private int userID;
    private List<Course> userCourses = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        userID = getIntent().getIntExtra("id", -1);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        courseAdapter = new CourseAdapter(userCourses, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(CourseActivity.this, CourseDetailActivity.class);
                intent.putExtra(CourseDetailActivity.EXTRA_COURSES, (Parcelable) course);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(courseAdapter);

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getmCoursesByUserId(userID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courseAdapter.setUserCourses(courses);
            }
        });

        toolbar.setTitle("Courses");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.navigation_menu);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return CourseActivity.super.onMenuItemClick(item);
            }
        });
    }

    /**
     * OnClick listener for Add TermR button
     *
     * @param view
     */
    public void add_course(View view) {
        Intent intent = new Intent(CourseActivity.this, AddCourseActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                courseAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}

