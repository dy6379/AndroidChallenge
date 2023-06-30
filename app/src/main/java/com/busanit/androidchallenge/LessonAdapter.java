package com.busanit.androidchallenge;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private ArrayList<Lesson> items = new ArrayList<>();
    private Fragment1 fragment1;
    private Context context;

    public LessonAdapter(Fragment1 fragment1, Context context){
        this.fragment1 = fragment1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment1_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson item = items.get(position);

        holder.textViewName.setText(item.getName());
        holder.textViewStart.setText(item.getStart_display());
        holder.textViewTeachers.setText(item.getTeachers());

        // 검색 결과가 업데이트될 때마다 updateList() 메서드를 호출한다
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(item.getName());
                builder.setMessage("시간, 날짜 : " + item.getStart_display() + "\n교수, 선생 : " + item.getTeachers());
                builder.setPositiveButton("확인", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Lesson> items) {
        this.items = items;
        notifyDataSetChanged(); // 리스트 변경을 어댑터에 알려줌

        // 검색 결과 출력
        Log.d("SEARCH", "검색 결과 개수 : " + items.size());
        for (int i = 0; i < items.size(); i++) {
            Lesson lesson = items.get(i);
            Log.d("SEARCH", "강의명 : " + lesson.getName() + ", 강사명 : " + lesson.getTeachers() + ", 시작날짜 : " + lesson.getStart_display());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewTeachers, textViewStart;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = context;

            textViewName = itemView.findViewById(R.id.textView1);
            textViewStart = itemView.findViewById(R.id.textView2);
            textViewTeachers = itemView.findViewById(R.id.textView3);
        }
    }
}