package com.example.course_keeper_capstone.UI.Terms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Adapters.TermAdapter;
import com.example.course_keeper_capstone.UI.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view_terms)
    RecyclerView recyclerViewTerms;
    private Repository repository;
    int userID;
    int termID;
    Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        userID = getIntent().getIntExtra("id", -1);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Terms");
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.term_activity_toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search_terms:
                        Context context = TermActivity.this;
                        String message = "search clicked";
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_home_term:
                        Intent intent2 = new Intent(TermActivity.this, HomeActivity.class);
                        startActivity(intent2);
                        return true;
                    default:
                        //default intent
                        return true;
                }
            }
        });

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recycler_view_terms);

        // populate recycler view with term data
       final TermAdapter adapter = new TermAdapter(this);
       recyclerViewTerms.setAdapter(adapter);
       recyclerViewTerms.setLayoutManager(new LinearLayoutManager(this));
       adapter.setTerms(repository.getUserTerms(userID));

    }



    /**
     *  OnClick listener for Edit Term button
     * @param view
     */
    public void editTerm(View view) {
        Intent intent = new Intent(TermActivity.this, TermDetailActivity.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    /**
     *  OnClick listener for Add Term button
     * @param view
     */
    public void add_term(View view) {
        Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_activity_toolbar, menu);
        return true;
    }

}