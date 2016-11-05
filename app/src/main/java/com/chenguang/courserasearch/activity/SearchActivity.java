package com.chenguang.courserasearch.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.fragment.EntryFragment;
import com.chenguang.courserasearch.model.CourseDetails;
import com.chenguang.courserasearch.model.EntryDetails;
import com.chenguang.courserasearch.model.SpecializationDetails;
import com.chenguang.courserasearch.network.RestImplFactory;
import com.chenguang.courserasearch.network.api.RestApi;
import com.chenguang.courserasearch.network.data.CourseData;
import com.chenguang.courserasearch.network.data.Element;
import com.chenguang.courserasearch.network.data.Entry;
import com.chenguang.courserasearch.network.data.LinkedData;
import com.chenguang.courserasearch.network.data.PartnerData;
import com.chenguang.courserasearch.network.data.QueryResult;
import com.chenguang.courserasearch.network.data.SpecializationData;
import com.chenguang.courserasearch.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements View.OnClickListener, ActionMenuView.OnMenuItemClickListener {

    private static final String TAG_ENTRY_FRAGMENT = "TAG_ENTRY_FRAGMENT";
    private static final String EMPTY_SEARCH_TEXT = "";
    private static final int DISPLAY_MAIN_CONTENT_CHILD = 1;

    private Toolbar toolbar;
    private ActionMenuView actionMenuView;
    private ViewSwitcher mainContentViewSwitcher;
    private LinearLayout searchViewContainer;
    private EditText searchEditText;
    private ImageButton clearSearchButton;

    private RestApi.QueryApi queryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.search_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setDisplayShowHomeEnabled(false);

        actionMenuView = (ActionMenuView) findViewById(R.id.action_menu_view);
        mainContentViewSwitcher = (ViewSwitcher) findViewById(R.id.search_activity_view_switcher);
        searchViewContainer = (LinearLayout) findViewById(R.id.search_bar_container);
        clearSearchButton = (ImageButton) findViewById(R.id.clear_search_text_button);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        ImageButton navigateBackFromSearchLayoutButton = (ImageButton) findViewById(R.id.navigate_back_from_search_button);

        actionMenuView.setOnMenuItemClickListener(this);
        clearSearchButton.setOnClickListener(this);
        navigateBackFromSearchLayoutButton.setOnClickListener(this);
        searchEditText.addTextChangedListener(new SearchTextChangeListener());
        searchEditText.setOnEditorActionListener(new SearchEditorActionListener());

        RestApi restApi = RestImplFactory.createRestImpl(getString(R.string.rest_base_url));
        queryApi = restApi.getQueryApi();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame_layout, EntryFragment.newInstance(), TAG_ENTRY_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        getMenuInflater().inflate(R.menu.menu_activity_search, actionMenu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchViewContainer.getVisibility() == View.VISIBLE) {
            hideSearchView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                showSearchView();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_search_text_button:
                searchEditText.setText(EMPTY_SEARCH_TEXT);
                break;

            case R.id.navigate_back_from_search_button:
                searchEditText.setText(EMPTY_SEARCH_TEXT);
                hideSearchView();
                break;
        }
    }

    private void showSearchView() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int centerX = (int) actionMenuView.getX() + actionMenuView.getWidth() / 2;
            int centerY = (int) actionMenuView.getY() + actionMenuView.getHeight() / 2;
            int finalRadius = searchViewContainer.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(searchViewContainer, centerX, centerY, 0, finalRadius);
            searchEditText.requestFocus();
            searchViewContainer.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            searchViewContainer.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
        }

        mainContentViewSwitcher.setDisplayedChild(DISPLAY_MAIN_CONTENT_CHILD);
        KeyboardUtils.openKeyboard(this);
    }

    private void hideSearchView() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int centerX = (int) actionMenuView.getX() + actionMenuView.getWidth() / 2;
            int centerY = (int) actionMenuView.getY() + actionMenuView.getHeight() / 2;
            int initialRadius = searchViewContainer.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(searchViewContainer, centerX, centerY, initialRadius, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    searchViewContainer.setVisibility(View.INVISIBLE);
                }
            });
            animator.start();
        } else {
            searchViewContainer.setVisibility(View.INVISIBLE);
        }

        KeyboardUtils.hideKeyboard(this);
    }

    private void searchForKeyword(String keyword, int startIndex, int limitNumber) {
        Log.e("findme", "making rest call");
        Call<QueryResult> resultCall = queryApi.getQueryResult(keyword, startIndex, limitNumber);
        resultCall.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                Log.e("findme", "Get response back: " + response.raw().toString());
                try {
                    QueryResult queryResult = response.body();
                    List<EntryDetails> entryDetailsList = loadQueryResultData(queryResult);
                    EntryFragment entryFragment = (EntryFragment) getSupportFragmentManager().findFragmentByTag(TAG_ENTRY_FRAGMENT);
                    entryFragment.updateEntryDetailsList(entryDetailsList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("findme", "exception: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                t.printStackTrace();
                Log.e("findme", "error: " + t.getMessage());
                showErrorMessage(String.format(getString(R.string.rest_call_error_message), t.getMessage()));
            }
        });
    }

    private List<EntryDetails> loadQueryResultData(QueryResult queryResult) {
        List<EntryDetails> entryDetailsList = new ArrayList<>();
        LinkedData linkedData = queryResult.getLinkedData();
        if (linkedData == null) {
            return entryDetailsList;
        }

        Map<String, Entry> entryMap = new HashMap<>();
        for (Element element : queryResult.getElementList()) {
            for (Entry entry : element.getEntryList()) {
                entryMap.put(entry.getId(), entry);
            }
        }
        Map<String, PartnerData> partnerDataMap = new HashMap<>();
        for (PartnerData partnerData : linkedData.getPartnerDataList()) {
            partnerDataMap.put(partnerData.getId(), partnerData);
        }
        Map<String, CourseDetails> courseDetailsMap = new HashMap<>();
        for (CourseData courseData : linkedData.getCourseDataList()) {
            CourseDetails courseDetails = new CourseDetails(courseData);
            String courseId = courseData.getId();
            if (entryMap.containsKey(courseId)) {
                courseDetails.setScore(entryMap.get(courseId).getScore());
            }
            for (String partnerId : courseData.getPartnerIdList()) {
                if (partnerDataMap.containsKey(partnerId)) {
                    courseDetails.getPartnerDataList().add(partnerDataMap.get(partnerId));
                }
            }
            courseDetailsMap.put(courseId, courseDetails);
            entryDetailsList.add(courseDetails);
        }
        for (SpecializationData specializationData : linkedData.getSpecializationDataList()) {
            SpecializationDetails specializationDetails = new SpecializationDetails(specializationData);
            if (entryMap.containsKey(specializationData.getId())) {
                specializationDetails.setScore(entryMap.get(specializationData.getId()).getScore());
            }
            for (String partnerId : specializationData.getPartnerIdList()) {
                if (partnerDataMap.containsKey(partnerId)) {
                    specializationDetails.getPartnerDataList().add(partnerDataMap.get(partnerId));
                }
            }
            for (String courseId : specializationData.getCourseIdList()) {
                if (courseDetailsMap.containsKey(courseId)) {
                    specializationDetails.getCourseDetailsList().add(courseDetailsMap.get(courseId));
                }
            }
            entryDetailsList.add(specializationDetails);
        }

        return entryDetailsList;
    }


    private class SearchTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                clearSearchButton.setVisibility(View.GONE);
            } else {
                clearSearchButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class SearchEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NONE) {
                KeyboardUtils.hideKeyboard(SearchActivity.this);
                searchForKeyword(searchEditText.getText().toString(), 0, 10);
                return true;
            }
            return false;
        }
    }
}
