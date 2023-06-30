package com.busanit.androidchallenge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment1 extends Fragment {
    private EditText editText;
    private Button button;
    private Button btndata;
    private List<Lesson> lessonList;
    private List<Lesson> searchResultList;
    private Handler handler = new Handler();
    private LessonAdapter adapter;
    private RecyclerView recyclerView;
    private static RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        lessons.add(new Lesson("안드로이드","화 5,6","안00"));
        lessons.add(new Lesson("자바","수 1,2,3","김00"));
        lessons.add(new Lesson("코틀린","화 7,8","박00"));
        lessons.add(new Lesson("Cobol","금 2,3,6","이순신"));
        lessons.add(new Lesson("C","월 1,2","김원"));
        lessons.add(new Lesson("Basic","목 3,4","박고지"));
        lessons.add(new Lesson("OS","금 2,3,6","이기적"));

        adapter = new LessonAdapter(this,getActivity());
        adapter.setItems(lessons);
        recyclerView.setAdapter(adapter);

        editText = rootView.findViewById(R.id.editText);
        button = rootView.findViewById(R.id.button);
        searchResultList = new ArrayList<Lesson>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString().toLowerCase();
                if (!searchText.isEmpty()) {
                    searchResultList.clear();
                    for (Lesson lesson : lessons) {
                        if (lesson.getName().toLowerCase().contains(searchText) || lesson.getTeachers().toLowerCase().contains(searchText)) {
                            searchResultList.add(lesson);
                        }
                    }
                    Log.d("SEARCH","검색 결과 개수 : "+searchResultList);
                } else {
                    updateList(lessons);
                }
                updateList(searchResultList);
            }
        });

        btndata = rootView.findViewById(R.id.btndata);
        btndata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultList = new ArrayList<Lesson>();
                lessonList = new ArrayList<Lesson>();
                makeRequest();
            }
        });
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }

        return rootView;
    }

    private void makeRequest() {
        String url = "https://apis.data.go.kr/B552881/kmooc/courseList?ServiceKey=rzXo3lWZma7I3uM%2BmgxuqM%2FjxV5OqkzF0kb80qAd25ZGJVDxtaWzt0tP1vvCqDLQW4JP%2Bo36Y0nhvNrnCBxWDQ%3D%3D&page=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //인코딩
                    response = new String(response.getBytes("ISO-8859-1"),"UTF-8");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String name = obj.getString("name");
                        String start_display = obj.getString("start_display");
                        String teachers = obj.getString("teachers");

                        Lesson lesson = new Lesson(name, start_display, teachers);
                        lessonList.add(lesson);
                    }
                    // 검색 결과 리스트 대신 전체 강의 리스트인 lessonList를 기반으로 리스트 업데이트
                    updateList(lessonList);
                } catch (JSONException e) {
                    Log.e("LessonRequest", "JSON Parsing error : " + e.getMessage());
                } catch (UnsupportedEncodingException e) { //인코딩에서 에러가 났을 경우
                    Log.e("LessonRequest", "Unsupported Encoding : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LessonRequest", "Error : " + error.getMessage());
            }
        });
        requestQueue.add(request);
        updateList(lessonList);
    }

    public void updateList(List<Lesson> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.setItems((ArrayList<Lesson>) list);
                }
            }
        });
    }
}