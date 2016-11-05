package com.chenguang.courserasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenguang.courserasearch.R;
import com.chenguang.courserasearch.model.CourseDetails;
import com.chenguang.courserasearch.model.EntryDetails;
import com.chenguang.courserasearch.model.SpecializationDetails;
import com.chenguang.courserasearch.network.data.PartnerData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.BaseViewHolder> {

    private static final int TYPE_HEADER_VIEW = 0;
    private static final int TYPE_ENTRY_VIEW = 1;

    private Context context;
    private List<EntryDetails> entryDetailsList;
    private int totalCount;
    private boolean shouldShowHeader;
    private EntryClickListener entryClickListener;

    public interface EntryClickListener {
        void onEntryClicked(EntryDetails entryDetails);
    }

    public EntryAdapter(Context context, List<EntryDetails> entryDetailsList, int totalCount, boolean shouldShowHeader, EntryClickListener entryClickListener) {
        this.context = context;
        Collections.sort(entryDetailsList);
        this.entryDetailsList = new ArrayList<>();
        this.entryDetailsList.addAll(entryDetailsList);
        this.totalCount = totalCount;
        this.shouldShowHeader = shouldShowHeader;
        this.entryClickListener = entryClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_VIEW:
                View headerView = LayoutInflater.from(context).inflate(R.layout.layout_entry_header_view, parent, false);
                return new HeaderViewHolder(headerView);

            case TYPE_ENTRY_VIEW:
            default:
                View entryView = LayoutInflater.from(context).inflate(R.layout.layout_entry_card_view, parent, false);
                return new EntryViewHolder(entryView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.totalCountTextView.setText(context.getResources().getQuantityString(
                    R.plurals.entry_list_total_count_text, totalCount, totalCount));
        } else {
            int correctPosition = shouldShowHeader ? position - 1 : position;
            EntryDetails entryDetails = entryDetailsList.get(correctPosition);
            EntryViewHolder entryViewHolder = (EntryViewHolder) holder;

            entryViewHolder.nameTextView.setText(entryDetails.getName());
            if (!entryDetails.getPartnerDataList().isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (PartnerData partnerData : entryDetails.getPartnerDataList()) {
                    stringBuilder.append(partnerData.getName()).append(", ");
                }
                entryViewHolder.partnerNameTextView.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
            }

            if (entryDetails instanceof CourseDetails) {
                CourseDetails courseDetails = (CourseDetails) entryDetails;
                if (!TextUtils.isEmpty(courseDetails.getPhotoUrl())) {
                    Picasso.with(context)
                            .load(courseDetails.getPhotoUrl())
                            .placeholder(R.drawable.ic_no_image_placeholder)
                            .error(R.drawable.ic_no_image_placeholder)
                            .into(entryViewHolder.photoImageView);
                }
                entryViewHolder.courseNumberTextView.setVisibility(View.GONE);
            } else {
                SpecializationDetails specializationDetails = (SpecializationDetails) entryDetails;
                int courseNumber = specializationDetails.getCourseDetailsList().size();
                if (!TextUtils.isEmpty(specializationDetails.getLogo())) {
                    Picasso.with(context)
                            .load(specializationDetails.getLogo())
                            .placeholder(R.drawable.ic_no_image_placeholder)
                            .error(R.drawable.ic_no_image_placeholder)
                            .into(entryViewHolder.photoImageView);
                }
                entryViewHolder.courseNumberTextView.setText(context.getResources().getQuantityString(
                        R.plurals.course_number_text, courseNumber, courseNumber));
                entryViewHolder.courseNumberTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (entryDetailsList == null || entryDetailsList.isEmpty()) {
            return 0;
        }
        return shouldShowHeader ? entryDetailsList.size() + 1 : entryDetailsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (shouldShowHeader) {
            return position == 0 ? TYPE_HEADER_VIEW : TYPE_ENTRY_VIEW;
        } else {
            return TYPE_ENTRY_VIEW;
        }
    }

    public void updateEntryDetailsList(List<EntryDetails> entryDetailsList) {
        if (entryDetailsList != null) {
            Collections.sort(entryDetailsList);
            this.entryDetailsList.clear();
            this.entryDetailsList.addAll(entryDetailsList);
            notifyDataSetChanged();
        }
    }

    public void appendEntryDetailsList(List<EntryDetails> entryDetailsList) {
        if (entryDetailsList != null) {
            Collections.sort(entryDetailsList);
            this.entryDetailsList.addAll(entryDetailsList);
            notifyDataSetChanged();
        }
    }

    public void updateCourseDetailsList(List<CourseDetails> courseDetailsList) {
        if (courseDetailsList != null) {
            Collections.sort(courseDetailsList);
            this.entryDetailsList.clear();
            this.entryDetailsList.addAll(courseDetailsList);
            notifyDataSetChanged();
        }
    }

    public void updateTotalCount(int totalCount) {
        this.totalCount = totalCount;
        notifyItemChanged(0);
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class HeaderViewHolder extends BaseViewHolder {

        private TextView totalCountTextView;

        HeaderViewHolder(View itemView) {
            super(itemView);

            totalCountTextView = (TextView) itemView.findViewById(R.id.entry_header_count_text_view);
        }
    }

    private class EntryViewHolder extends BaseViewHolder implements View.OnClickListener {

        private ImageView photoImageView;
        private TextView courseNumberTextView;
        private TextView nameTextView;
        private TextView partnerNameTextView;

        EntryViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.entry_photo_image_view);
            courseNumberTextView = (TextView) itemView.findViewById(R.id.entry_course_number_text_view);
            nameTextView = (TextView) itemView.findViewById(R.id.entry_name_text_view);
            partnerNameTextView = (TextView) itemView.findViewById(R.id.entry_partner_name_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = shouldShowHeader ? getAdapterPosition() - 1 : getAdapterPosition();
            entryClickListener.onEntryClicked(entryDetailsList.get(position));
        }
    }
}
