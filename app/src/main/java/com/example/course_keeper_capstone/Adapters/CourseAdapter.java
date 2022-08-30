package com.example.course_keeper_capstone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> implements Filterable {
    private List<Course> userCourses;
    List<Course> list = new ArrayList<>();
    private OnItemClickListener listener;

    public CourseAdapter(List<Course> userCourses, OnItemClickListener listener) {
        this.listener = listener;
        this.userCourses = new ArrayList<>(userCourses);
    }


    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.card_layout_courses,parent,false);
        return new CourseAdapter.CourseViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    Filter courseFilter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Course> filteredCoursesList = new ArrayList<>();
            if(charSequence == null || charSequence.length() ==0){
                filteredCoursesList.addAll(list);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Course courses : list){
                    if(courses.getCourseTitle().toLowerCase().contains(filterPattern)){
                        filteredCoursesList.add(courses);
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
            userCourses.clear();
            userCourses.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class CourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_course_title)
        TextView tvTitle;
        @BindView(R.id.card_course_dates)
        TextView tvDates;
        @BindView(R.id.course_status)
        TextView tvStatus;
        @BindView(R.id.course_notes)
        TextView tvNotes;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course currentCourse = userCourses.get(position);
                    listener.onItemClick(currentCourse);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        Course currentCourse = userCourses.get(position);
        holder.tvTitle.setText(currentCourse.getCourseTitle());
        String startEnd = currentCourse.getCourseStart() + " to " + currentCourse.getCourseEnd();
        holder.tvDates.setText(startEnd);
        holder.tvStatus.setText(currentCourse.getCourseStatus());
        holder.tvNotes.setText(currentCourse.getCourseNotes());

    }

    public void setUserCourses(List<Course> course){
        userCourses.clear();
        userCourses.addAll(course);
        this.list.addAll(course);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return userCourses.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Course course);
    }

}
