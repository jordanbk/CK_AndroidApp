package com.example.course_keeper_capstone.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Adapters.TermAdapter;
import com.example.course_keeper_capstone.UI.Base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermActivity extends BaseActivity {
    @BindView(R.id.recycler_view_terms)
    RecyclerView recyclerViewTerms;
    int userID;
    Context context;
    private List<Term> userTerms = new ArrayList<>();
    private TermAdapter termsAdapter;
    private Toolbar toolbar;
    TermViewModel mTermViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        userID = getIntent().getIntExtra("id", -1);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_terms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = TermActivity.this;
        termsAdapter = new TermAdapter(userTerms, context);
        recyclerView.setAdapter(termsAdapter);

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        mTermViewModel.getmTermsByUserId(userID).observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termsAdapter.setUserTerms(terms);
            }
        });


        toolbar.setTitle("Terms");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.navigation_menu);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return TermActivity.super.onMenuItemClick(item);
            }
        });
    }


    /**
     * OnClick listener for Add TermR button
     *
     * @param view
     */
    public void add_term(View view) {
        Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type search here");
        //searchView.isSubmitButtonEnabled();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                termsAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}