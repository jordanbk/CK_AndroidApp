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
import com.example.course_keeper_capstone.UI.reports.models.CourseR;
import com.example.course_keeper_capstone.databinding.ListItemReportCoursesBinding;

public class CourseAdapter extends ListAdapter<CourseR, CourseAdapter.ViewHolder> {

    public CourseAdapter() {
        super(new DiffUtil.ItemCallback<CourseR>() {
            @Override
            public boolean areItemsTheSame(@NonNull CourseR oldItem, @NonNull CourseR newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull CourseR oldItem, @NonNull CourseR newItem) {
                return oldItem.getCourseTitle().equals(newItem.getCourseTitle());
            }
        });
    }


    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportCoursesBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item_report_courses, parent, false
        );
        return new CourseAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
        if(position == 0){
            holder.mBinding.lineTop.setVisibility(ViewGroup.VISIBLE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.termTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            holder.mBinding.lineTop.setVisibility(ViewGroup.GONE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.termTv.setTypeface(Typeface.DEFAULT);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemReportCoursesBinding mBinding;

        public ViewHolder(@NonNull ListItemReportCoursesBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(CourseR course) {
            mBinding.setCourse(course);
            mBinding.executePendingBindings();
        }
    }
}