package com.chenguang.courserasearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.activity.EntryDetailsActivity;
import com.chenguang.courserasearch.adapter.EntryAdapter;
import com.chenguang.courserasearch.model.CourseDetails;
import com.chenguang.courserasearch.model.EntryDetails;

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends Fragment implements EntryAdapter.EntryClickListener {

    private static final String EXTRA_SHOULD_SHOW_HEADER = "EXTRA_SHOULD_SHOW_HEADER";

    private RecyclerView entryListRecyclerView;

    private EntryAdapter entryAdapter;
    private EntryLoadListener entryLoadListener;
    private boolean shouldShowHeader;
    private boolean isLoading;

    public interface EntryLoadListener {
        void loadMoreData();
    }

    public enum SortType {
        SORT_BY_NAME,
        SORT_BY_SCORE,
        SORT_BY_TYPE
    }

    public static EntryFragment newInstance(boolean shouldShowHeader) {
        EntryFragment fragment = new EntryFragment();
        fragment.setRetainInstance(true);
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SHOULD_SHOW_HEADER, shouldShowHeader);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            entryLoadListener = (EntryLoadListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Parent activity must implement " + EntryLoadListener.class.getName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            shouldShowHeader = getArguments().getBoolean(EXTRA_SHOULD_SHOW_HEADER, true);
        } else {
            shouldShowHeader = savedInstanceState.getBoolean(EXTRA_SHOULD_SHOW_HEADER, true);
        }
        entryAdapter = new EntryAdapter(getContext(), new ArrayList<EntryDetails>(), 0, shouldShowHeader, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_SHOULD_SHOW_HEADER, shouldShowHeader);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        entryListRecyclerView = (RecyclerView) view.findViewById(R.id.entry_fragment_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        entryListRecyclerView.setLayoutManager(layoutManager);
        entryListRecyclerView.setAdapter(entryAdapter);
        entryListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //check for scrolling down
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = true;
                            entryLoadListener.loadMoreData();
                        }
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onEntryClicked(EntryDetails entryDetails) {
        EntryDetailsActivity.start(getActivity(), entryDetails);
    }

    public void updateEntryDetailsList(List<EntryDetails> entryDetailsList) {
        isLoading = false;
        entryAdapter.updateEntryDetailsList(entryDetailsList);
        entryListRecyclerView.setVisibility(entryAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }

    public void appendEntryDetailsList(List<EntryDetails> entryDetailsList) {
        isLoading = false;
        entryAdapter.appendEntryDetailsList(entryDetailsList);
        entryListRecyclerView.setVisibility(entryAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }

    public void updateCourseDetailsList(List<CourseDetails> courseDetailsList) {
        isLoading = false;
        entryAdapter.updateCourseDetailsList(courseDetailsList);
        entryListRecyclerView.setVisibility(entryAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }

    public void updateTotalCount(int totalCount) {
        entryAdapter.updateTotalCount(totalCount);
    }

    public void sort(SortType sortType) {
        entryAdapter.sort(sortType);
    }
}
