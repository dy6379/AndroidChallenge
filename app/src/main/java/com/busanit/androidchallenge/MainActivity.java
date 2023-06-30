package com.busanit.androidchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager2 pager;
    private List<String> tabElement;
    private Fragment1 fragment1;
    private Fragment2 fragment2;

    private GridLayout gridTable;
    RecyclerView recyclerView;
    LessonAdapter lessonAdaptor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        Fragment fragment1 = new Fragment1();
        Fragment fragment2 = new Fragment2();
        fragments.add(fragment1);
        fragments.add(fragment2);

        MyPagerAdapter adapter = new MyPagerAdapter(this,fragments);
        pager.setAdapter(adapter);

        List<String> tabElement = Arrays.asList("강의목록","시간표");
        new TabLayoutMediator(tabs, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(tabElement.get(position));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                textView.setTextColor(Color.MAGENTA);
                textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                tab.setCustomView(textView);
            }
        }).attach();
    }

    private class MyPagerAdapter extends FragmentStateAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentActivity fragmentActivity, ArrayList<Fragment> items) {
            super(fragmentActivity);
            this.items = items;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_settings:
                Toast.makeText(this, "설정 메뉴가 설정되었습니다.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}