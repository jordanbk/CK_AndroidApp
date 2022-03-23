package com.example.course_keeper_capstone.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.Adapters.TermAdapter;
import java.util.ArrayList;
import java.util.List;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        repository = new Repository(getApplication());
        repository.getAllTerms();
        //RecyclerView recyclerView = findViewById(R.id.recycler_view_terms);
        RecyclerView recyclerViewTerms = findViewById(R.id.recycler_view_terms);

        userID = getIntent().getIntExtra("id", -1);

        // populate recycler view with term data
        final TermAdapter adapter = new TermAdapter(this);
        recyclerViewTerms.setAdapter(adapter);
        recyclerViewTerms.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(repository.getAllTerms());
    }

/*        Repository repository = new Repository(getApplication());
        RecyclerView recyclerViewTerms = findViewById(R.id.recycler_view_terms);
        final TermAdapter adapter = new TermAdapter(this);
        recyclerViewTerms.setAdapter(adapter);
        recyclerViewTerms.setLayoutManager(new LinearLayoutManager(this));
        List<Term> associatedTerms = new ArrayList<>();
        for(Term t : repository.getAllTerms()){
            if(t.getUserID() == userID){
                associatedTerms.add(t);
            }
        }
        adapter.setTerms(associatedTerms);

    }*/


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
}