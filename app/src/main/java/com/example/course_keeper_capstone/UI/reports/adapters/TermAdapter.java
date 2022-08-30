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
import com.example.course_keeper_capstone.UI.reports.models.TermR;
import com.example.course_keeper_capstone.databinding.ListItemReportTermsBinding;

public class TermAdapter extends ListAdapter<TermR, TermAdapter.ViewHolder> {

    public TermAdapter() {
        super(new DiffUtil.ItemCallback<TermR>() {
            @Override
            public boolean areItemsTheSame(@NonNull TermR oldItem, @NonNull TermR newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull TermR oldItem, @NonNull TermR newItem) {
                return oldItem.getTermID()==newItem.getTermID();
            }
        });
    }


    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportTermsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item_report_terms, parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
        if(position == 0){
            holder.mBinding.lineTop.setVisibility(ViewGroup.VISIBLE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            holder.mBinding.lineTop.setVisibility(ViewGroup.GONE);
            holder.mBinding.titleTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.startDateTv.setTypeface(Typeface.DEFAULT);
            holder.mBinding.endDateTv.setTypeface(Typeface.DEFAULT);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemReportTermsBinding mBinding;

        public ViewHolder(@NonNull ListItemReportTermsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(TermR term) {
            mBinding.setTerm(term);
            mBinding.executePendingBindings();
        }
    }
}
