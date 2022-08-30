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
import com.example.course_keeper_capstone.databinding.ListItemReportInstructorBinding;
import com.example.course_keeper_capstone.databinding.ListItemReportInstructorBinding;
import com.example.course_keeper_capstone.UI.reports.models.InstructorR;

public class InstructorAdapter extends ListAdapter<InstructorR, InstructorAdapter.ViewHolder> {

    public InstructorAdapter() {
        super(new DiffUtil.ItemCallback<InstructorR>() {
            @Override
            public boolean areItemsTheSame(@NonNull InstructorR oldItem, @NonNull InstructorR newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull InstructorR oldItem, @NonNull InstructorR newItem) {
                return oldItem.getInstructorEmail().equals(newItem.getInstructorEmail());
            }
        });
    }


    @NonNull
    @Override
    public InstructorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportInstructorBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item_report_instructor, parent, false
        );
        return new InstructorAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
        if(position == 0){
            holder.mBinding.lineTop.setVisibility(ViewGroup.VISIBLE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.emailTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.phoneTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.courseTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            holder.mBinding.lineTop.setVisibility(ViewGroup.GONE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.emailTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.phoneTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.courseTv.setTypeface(Typeface.DEFAULT);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemReportInstructorBinding mBinding;

        public ViewHolder(@NonNull ListItemReportInstructorBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(InstructorR instructor) {
            mBinding.setInstructor(instructor);
            mBinding.executePendingBindings();
        }
    }
}