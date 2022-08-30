package com.example.course_keeper_capstone.UI.Instructors;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.course_keeper_capstone.Adapters.InstructorAdapter;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructorActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int userID;
    private InstructorViewModel viewModel;
    private InstructorAdapter instructorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        userID = getIntent().getIntExtra("id", -1);

        viewModel = new ViewModelProvider(this).get(InstructorViewModel.class);

        instructorAdapter = new InstructorAdapter(new ArrayList<>(), new InstructorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Instructor instructor) {
                Intent intent = new Intent(InstructorActivity.this, InstructorDetailActivity.class);
                intent.putExtra(InstructorDetailActivity.EXTRA_INSTRUCTORS, (Parcelable) instructor);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(instructorAdapter);

        viewModel.getInstructorsByUserId(userID).observe(this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(List<Instructor> instructors) {
                instructorAdapter.setUserInstructors(instructors);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorActivity.this, AddInstructorActivity.class);
                intent.putExtra("id", userID);
                startActivity(intent);
            }
        });


        toolbar.setTitle("Instructors");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.navigation_menu);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return InstructorActivity.super.onMenuItemClick(item);
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
                instructorAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

