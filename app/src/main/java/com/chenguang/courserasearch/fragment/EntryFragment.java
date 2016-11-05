package com.chenguang.courserasearch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.adapter.EntryAdapter;
import com.chenguang.courserasearch.model.EntryDetails;

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends Fragment {

    private static final int DISPLAY_ENTRY_LIST_CHILD = 1;

    private ViewSwitcher contentViewSwitcher;
    private TextView emptyTextView;
    private RecyclerView entryListRecyclerView;
    private EntryAdapter entryAdapter;

    public static EntryFragment newInstance() {
        EntryFragment fragment = new EntryFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        contentViewSwitcher = (ViewSwitcher) view.findViewById(R.id.entry_fragment_view_switcher);
        emptyTextView = (TextView) view.findViewById(R.id.entry_list_empty_text_view);
        entryListRecyclerView = (RecyclerView) view.findViewById(R.id.entry_fragment_recycler_view);
        entryListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entryAdapter = new EntryAdapter(getContext(), new ArrayList<EntryDetails>());
        entryListRecyclerView.setAdapter(entryAdapter);

        return view;
    }

    public void updateEntryDetailsList(List<EntryDetails> entryDetailsList) {
        contentViewSwitcher.setDisplayedChild(DISPLAY_ENTRY_LIST_CHILD);
        entryAdapter.appendEntryDetailsList(entryDetailsList);
        entryListRecyclerView.setVisibility(entryAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }
}
