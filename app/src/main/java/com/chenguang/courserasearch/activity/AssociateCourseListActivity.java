package com.chenguang.courserasearch.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.fragment.EntryFragment;
import com.chenguang.courserasearch.model.CourseDetails;

import java.util.ArrayList;

public class AssociateCourseListActivity extends AppCompatActivity implements EntryFragment.EntryLoadListener {

    private static final String EXTRA_ASSOCIATE_COURSE_LIST = "EXTRA_ASSOCIATE_COURSE_LIST";
    private static final String TAG_COURSE_FRAGMENT = "TAG_COURSE_FRAGMENT";

    private ArrayList<CourseDetails> courseDetailsList;

    public static void start(Context context, ArrayList<CourseDetails> courseDetailsList) {
        Intent intent = new Intent(context, AssociateCourseListActivity.class);
        intent.putExtra(EXTRA_ASSOCIATE_COURSE_LIST, courseDetailsList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_course_list);

        if (savedInstanceState == null) {
            courseDetailsList = getIntent().getParcelableArrayListExtra(EXTRA_ASSOCIATE_COURSE_LIST);
        } else {
            courseDetailsList = savedInstanceState.getParcelableArrayList(EXTRA_ASSOCIATE_COURSE_LIST);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.associate_course_list_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.associate_course_list_activity_content_frame_layout, EntryFragment.newInstance(false), TAG_COURSE_FRAGMENT)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EntryFragment entryFragment = (EntryFragment) getSupportFragmentManager().findFragmentByTag(TAG_COURSE_FRAGMENT);
        entryFragment.updateCourseDetailsList(courseDetailsList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_ASSOCIATE_COURSE_LIST, courseDetailsList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void loadMoreData() {
        // No-op
    }
}
