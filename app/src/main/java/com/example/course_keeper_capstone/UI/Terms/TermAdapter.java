package com.example.course_keeper_capstone.UI.Terms;

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

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{


    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            this.termItemView = itemView.findViewById(R.id.textView2);
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
        View itemView = mInflator.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current = mTerms.get(position);
            String name = current.getTermName();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No term name");
        }
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
