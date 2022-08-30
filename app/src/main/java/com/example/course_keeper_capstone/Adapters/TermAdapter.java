package com.example.course_keeper_capstone.Adapters;

import static com.example.course_keeper_capstone.Util.Constants.TERM_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.TermDetailActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> implements Filterable {
    private List<Term> userTerms;
    private final Context context;
    List<Term> list = new ArrayList<>();

    public TermAdapter(List<Term> userTerms, Context context) {
        this.context = context;
        this.userTerms = new ArrayList<>(userTerms);
    }


    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.card_layout_terms,parent,false);
        return new TermViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return termsFilter;
    }

    Filter termsFilter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Term> filteredTermsList = new ArrayList<>();
            if(charSequence == null || charSequence.length() ==0){
                filteredTermsList.addAll(list);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Term terms : list){
                    if(terms.getTermName().toLowerCase().contains(filterPattern)){
                        filteredTermsList.add(terms);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredTermsList;
            results.count = filteredTermsList.size();
            return results;

        }
        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userTerms.clear();
            userTerms.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class TermViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_term_title)
        TextView tvTitle;
        @BindView(R.id.card_term_dates)
        TextView tvDates;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Term currentTerm = userTerms.get(position);
                    Intent intent = new Intent(context, TermDetailActivity.class);
                    intent.putExtra(TermDetailActivity.EXTRA_TERMS,currentTerm);
                    context.startActivity(intent);
                }
            });
        }

    }


    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        Term currentTerm = userTerms.get(position);
        holder.tvTitle.setText(currentTerm.getTermName());
        String startEnd = currentTerm.getTermStart() + " to " + currentTerm.getTermEnd();
        holder.tvDates.setText(startEnd);

    }

    public void setUserTerms(List<Term> terms){
        userTerms.clear();
        userTerms.addAll(terms);
        this.list.addAll(terms);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return userTerms.size();
    }
}
