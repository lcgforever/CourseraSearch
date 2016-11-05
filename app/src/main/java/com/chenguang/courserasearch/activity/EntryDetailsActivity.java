package com.chenguang.courserasearch.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.model.CourseDetails;
import com.chenguang.courserasearch.model.EntryDetails;
import com.chenguang.courserasearch.model.SpecializationDetails;
import com.chenguang.courserasearch.network.RestImplFactory;
import com.chenguang.courserasearch.network.api.RestApi;
import com.chenguang.courserasearch.network.data.CourseData;
import com.chenguang.courserasearch.network.data.CourseQueryResult;
import com.chenguang.courserasearch.network.data.PartnerData;
import com.chenguang.courserasearch.network.data.SpecializationData;
import com.chenguang.courserasearch.network.data.SpecializationQueryResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryDetailsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_ENTRY_DETAILS = "EXTRA_ENTRY_DETAILS";

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView toolbarPhotoImageView;
    private TextView nameTextView;
    private TextView partnerNameTextView;
    private TextView descriptionTextView;
    private RelativeLayout viewCoursesTextContainer;
    private TextView viewCoursesTextView;

    private EntryDetails entryDetails;
    private RestApi restApi;

    public static void start(Context context, EntryDetails entryDetails) {
        Intent intent = new Intent(context, EntryDetailsActivity.class);
        intent.putExtra(EXTRA_ENTRY_DETAILS, entryDetails);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        if (savedInstanceState == null) {
            entryDetails = (EntryDetails) getIntent().getSerializableExtra(EXTRA_ENTRY_DETAILS);
        } else {
            entryDetails = (EntryDetails) savedInstanceState.getSerializable(EXTRA_ENTRY_DETAILS);
        }

        restApi = RestImplFactory.createRestImpl(getString(R.string.rest_base_url));

        Toolbar toolbar = (Toolbar) findViewById(R.id.entry_details_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.entry_details_activity_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_accent),
                getResources().getColor(android.R.color.holo_red_light));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.entry_details_activity_collapse_toolbar_layout);
        toolbarPhotoImageView = (ImageView) findViewById(R.id.entry_details_activity_toolbar_photo_image_view);
        nameTextView = (TextView) findViewById(R.id.entry_details_activity_name_text_view);
        partnerNameTextView = (TextView) findViewById(R.id.entry_details_activity_partner_name_text_view);
        descriptionTextView = (TextView) findViewById(R.id.entry_details_activity_description_text_view);
        viewCoursesTextContainer = (RelativeLayout) findViewById(R.id.entry_details_activity_courses_container);
        viewCoursesTextView = (TextView) findViewById(R.id.entry_details_activity_courses_text_view);

        viewCoursesTextContainer.setOnClickListener(this);

        updateViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_ENTRY_DETAILS, entryDetails);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (entryDetails instanceof CourseDetails) {
            RestApi.CourseQueryApi courseQueryApi = restApi.getCourseQueryApi();
            Call<CourseQueryResult> queryResultCall = courseQueryApi.getCourseQueryResult(entryDetails.getId());
            queryResultCall.enqueue(new Callback<CourseQueryResult>() {
                @Override
                public void onResponse(Call<CourseQueryResult> call, Response<CourseQueryResult> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    CourseQueryResult courseQueryResult = response.body();
                    List<CourseData> courseDataList = courseQueryResult.getCourseDataList();
                    if (!courseDataList.isEmpty()) {
                        entryDetails = new CourseDetails(courseDataList.get(0));
                        updateViews();
                    }
                }

                @Override
                public void onFailure(Call<CourseQueryResult> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    t.printStackTrace();
                    showErrorMessage(String.format(getString(R.string.rest_call_error_message), t.getMessage()));
                }
            });
        } else {
            RestApi.SpecializationQueryApi specializationQueryApi = restApi.getSpecializationQueryApi();
            Call<SpecializationQueryResult> queryResultCall = specializationQueryApi.getSpecializationQueryResult(entryDetails.getId());
            queryResultCall.enqueue(new Callback<SpecializationQueryResult>() {
                @Override
                public void onResponse(Call<SpecializationQueryResult> call, Response<SpecializationQueryResult> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    SpecializationQueryResult specializationQueryResult = response.body();
                    List<SpecializationData> specializationDataList = specializationQueryResult.getSpecializationDataList();
                    if (!specializationDataList.isEmpty()) {
                        entryDetails = new SpecializationDetails(specializationDataList.get(0));
                        updateViews();
                    }
                }

                @Override
                public void onFailure(Call<SpecializationQueryResult> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    t.printStackTrace();
                    showErrorMessage(String.format(getString(R.string.rest_call_error_message), t.getMessage()));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entry_details_activity_courses_container:
                if (entryDetails instanceof SpecializationDetails) {
                    SpecializationDetails specializationDetails = (SpecializationDetails) entryDetails;
                    AssociateCourseListActivity.start(this, new ArrayList<>(specializationDetails.getCourseDetailsList()));
                }
                break;
        }
    }

    private void updateViews() {
        String name = entryDetails.getName();
        collapsingToolbarLayout.setTitle(name);
        nameTextView.setText(entryDetails.getName());
        if (!entryDetails.getPartnerDataList().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (PartnerData partnerData : entryDetails.getPartnerDataList()) {
                stringBuilder.append(partnerData.getName()).append(", ");
            }
            String partnerName = stringBuilder.substring(0, stringBuilder.length() - 2);
            partnerNameTextView.setText(partnerName);
        }
        descriptionTextView.setText(entryDetails.getDescription());
        if (entryDetails instanceof CourseDetails) {
            CourseDetails courseDetails = (CourseDetails) entryDetails;
            if (!TextUtils.isEmpty(courseDetails.getPhotoUrl())) {
                Picasso.with(this)
                        .load(courseDetails.getPhotoUrl())
                        .placeholder(R.drawable.ic_no_image_placeholder)
                        .error(R.drawable.ic_no_image_placeholder)
                        .into(toolbarPhotoImageView);
            }
            viewCoursesTextContainer.setVisibility(View.GONE);
        } else {
            SpecializationDetails specializationDetails = (SpecializationDetails) entryDetails;
            if (!TextUtils.isEmpty(specializationDetails.getLogo())) {
                Picasso.with(this)
                        .load(specializationDetails.getLogo())
                        .placeholder(R.drawable.ic_no_image_placeholder)
                        .error(R.drawable.ic_no_image_placeholder)
                        .into(toolbarPhotoImageView);
            }
            int courseNumber = specializationDetails.getCourseDetailsList().size();
            if (courseNumber > 0) {
                viewCoursesTextView.setText(getResources().getQuantityString(R.plurals.view_courses_text, courseNumber, courseNumber));
                viewCoursesTextContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}
