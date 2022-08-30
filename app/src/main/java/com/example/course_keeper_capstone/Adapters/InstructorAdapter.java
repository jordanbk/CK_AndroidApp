package com.example.course_keeper_capstone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder> implements Filterable {
    private List<Instructor> instructors;
    List<Instructor> list = new ArrayList<>();
    private InstructorAdapter.OnItemClickListener listener;

    public InstructorAdapter(List<Instructor> instructors, InstructorAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.instructors = new ArrayList<>(instructors);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.card_layout_instructors, parent, false);
        return new InstructorAdapter.ViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    Filter courseFilter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Instructor> filteredCoursesList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredCoursesList.addAll(list);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Instructor instructor : list) {
                    if (instructor.getInstructorName().toLowerCase().contains(filterPattern)) {
                        filteredCoursesList.add(instructor);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCoursesList;
            results.count = filteredCoursesList.size();
            return results;

        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            instructors.clear();
            instructors.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTv)
        TextView nameTv;
        @BindView(R.id.emailTv)
        TextView emailTv;
        @BindView(R.id.phoneTv)
        TextView phoneTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Instructor instructor = instructors.get(position);
                    listener.onItemClick(instructor);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instructor instructor = instructors.get(position);
        holder.nameTv.setText(instructor.getInstructorName());
        holder.emailTv.setText(instructor.getInstructorEmail());
        holder.phoneTv.setText(instructor.getInstructorPhone());

    }

    public void setUserInstructors(List<Instructor> listInstructor) {
        instructors.clear();
        instructors.addAll(listInstructor);
        this.list.addAll(listInstructor);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return instructors.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Instructor instructor);
    }

}

