package com.example.course_keeper_capstone.UI.Assessments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Adapters.AssessmentAdapter;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.ui.Base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int userID;
    private AssessmentAdapter assessmentAdapter;
    private AssessmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        userID = getIntent().getIntExtra("id", -1);

        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        assessmentAdapter = new AssessmentAdapter(new ArrayList<>(), new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailActivity.class);
                intent.putExtra(AssessmentDetailActivity.EXTRA_ASSESSMENTS, (Parcelable) assessment);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(assessmentAdapter);


        viewModel.getAssessmentsByUserId(userID).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                assessmentAdapter.setUserAssessment(assessments);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentActivity.this, AddAssessmentActivity.class);
                intent.putExtra("id", userID);
                startActivity(intent);
            }
        });


        toolbar.setTitle("Assessments");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.navigation_menu);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return AssessmentActivity.super.onMenuItemClick(item);
            }
        });

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
                assessmentAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}