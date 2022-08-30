package com.example.course_keeper_capstone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> implements Filterable {
    private List<Assessment> assessments;
    List<Assessment> list = new ArrayList<>();
    private AssessmentAdapter.OnItemClickListener listener;

    public AssessmentAdapter(List<Assessment> assessments, AssessmentAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.assessments = new ArrayList<>(assessments);
    }


    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.card_layout_assessments, parent, false);
        return new AssessmentAdapter.ViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    Filter courseFilter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Assessment> filteredAssessmentList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredAssessmentList.addAll(list);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Assessment assessment : list) {
                    if (assessment.getAssessmentTitle().toLowerCase().contains(filterPattern)) {
                        filteredAssessmentList.add(assessment);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredAssessmentList;
            results.count = filteredAssessmentList.size();
            return results;

        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            assessments.clear();
            assessments.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.titleTv)
        TextView tvTitle;
        @BindView(R.id.datesTv)
        TextView tvDates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment assessment = assessments.get(position);
                    listener.onItemClick(assessment);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.tvTitle.setText(assessment.getAssessmentTitle());
        String startEnd = assessment.getAssessmentStartDate() + " to " + assessment.getAssessmentEndDate();
        holder.tvDates.setText(startEnd);

    }

    public void setUserAssessment(List<Assessment> assessment) {
        assessments.clear();
        assessments.addAll(assessment);
        this.list.addAll(assessment);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return assessments.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Assessment assessment);
    }

}
