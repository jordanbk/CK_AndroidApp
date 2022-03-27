package com.example.course_keeper_capstone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Courses.CourseDetailActivity;
import com.example.course_keeper_capstone.UI.Terms.TermDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    class CourseViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_term_title)
        TextView tvTitle;
        @BindView(R.id.card_term_dates)
        TextView tvDates;
        //private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //this.courseItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetailActivity.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("notes", current.getCourseNotes());
                    intent.putExtra("termID", current.getTermID_FK());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;
    public CourseAdapter(TermDetailActivity context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.card_layout_courses,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course current = mCourses.get(position);
        holder.tvTitle.setText(current.getCourseTitle());
        String startEnd = current.getCourseStart() + " to " + current.getCourseEnd();
        holder.tvDates.setText(startEnd);
    }

    @Override
    public int getItemCount() {
        if(mCourses != null)
            return mCourses.size();
        else return 0;
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
    }


}
