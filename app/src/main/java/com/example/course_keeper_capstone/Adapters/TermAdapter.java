package com.example.course_keeper_capstone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Terms.TermDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{


    class TermViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_term_title)
        TextView tvTitle;
        @BindView(R.id.card_term_dates)
        TextView tvDates;
        //private final TextView termItemView;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.termItemView = itemView.findViewById(R.id.card_term_title);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetailActivity.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    intent.putExtra("userID", current.getUserID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflator;
    public TermAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.card_layout_terms,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
            Term current = mTerms.get(position);
            holder.tvTitle.setText(current.getTermName());
            String startEnd = current.getTermStart() + " to " + current.getTermEnd();
            holder.tvDates.setText(startEnd);

    }

    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
}
