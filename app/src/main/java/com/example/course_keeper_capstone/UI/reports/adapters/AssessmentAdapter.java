package com.example.course_keeper_capstone.UI.reports.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.reports.models.AssessmentR;
import com.example.course_keeper_capstone.databinding.ListItemReportAssessmentBinding;

public class AssessmentAdapter extends ListAdapter<AssessmentR, AssessmentAdapter.ViewHolder> {

    public AssessmentAdapter() {
        super(new DiffUtil.ItemCallback<AssessmentR>() {
            @Override
            public boolean areItemsTheSame(@NonNull AssessmentR oldItem, @NonNull AssessmentR newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull AssessmentR oldItem, @NonNull AssessmentR newItem) {
                return oldItem.getAssessmentTitle().equals(newItem.getAssessmentTitle());
            }
        });
    }


    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportAssessmentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item_report_assessment, parent, false
        );
        return new AssessmentAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
        if(position == 0){
            holder.mBinding.lineTop.setVisibility(ViewGroup.VISIBLE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.courseTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            holder.mBinding.lineTop.setVisibility(ViewGroup.GONE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.courseTv.setTypeface(Typeface.DEFAULT);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemReportAssessmentBinding mBinding;

        public ViewHolder(@NonNull ListItemReportAssessmentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(AssessmentR assessment) {
            mBinding.setAssessment(assessment);
            mBinding.executePendingBindings();
        }
    }
}