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

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private Context context;
    private List<EntryDetails> entryDetailsList;

    public EntryAdapter(Context context, List<EntryDetails> entryDetailsList) {
        this.context = context;
        Collections.sort(entryDetailsList);
        this.entryDetailsList = new ArrayList<>();
        this.entryDetailsList.addAll(entryDetailsList);
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_entry_card_view, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        EntryDetails entryDetails = entryDetailsList.get(position);

        holder.nameTextView.setText(entryDetails.getName());
        if (!entryDetails.getPartnerDataList().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (PartnerData partnerData : entryDetails.getPartnerDataList()) {
                stringBuilder.append(partnerData.getName()).append(", ");
            }
            holder.partnerNameTextView.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
        }

        if (entryDetails instanceof CourseDetails) {
            CourseDetails courseDetails = (CourseDetails) entryDetails;
            if (!TextUtils.isEmpty(courseDetails.getPhotoUrl())) {
                Picasso.with(context)
                        .load(courseDetails.getPhotoUrl())
                        .placeholder(R.drawable.ic_no_image_placeholder)
                        .error(R.drawable.ic_no_image_placeholder)
                        .into(holder.photoImageView);
            }
            holder.courseNumberTextView.setVisibility(View.GONE);
        } else {
            SpecializationDetails specializationDetails = (SpecializationDetails) entryDetails;
            int courseNumber = specializationDetails.getCourseDetailsList().size();
            if (courseNumber > 0) {
                CourseDetails firstCourseDetails = specializationDetails.getCourseDetailsList().get(0);
                if (!TextUtils.isEmpty(firstCourseDetails.getPhotoUrl())) {
                    Picasso.with(context)
                            .load(firstCourseDetails.getPhotoUrl())
                            .placeholder(R.drawable.ic_no_image_placeholder)
                            .error(R.drawable.ic_no_image_placeholder)
                            .into(holder.photoImageView);
                }
            }
            holder.courseNumberTextView.setText(context.getResources().getQuantityString(
                    R.plurals.course_number_text, courseNumber, courseNumber));
            holder.courseNumberTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return entryDetailsList == null ? 0 : entryDetailsList.size();
    }

    public void appendEntryDetailsList(List<EntryDetails> entryDetailsList) {
        if (entryDetailsList != null) {
            Collections.sort(entryDetailsList);
            this.entryDetailsList.addAll(entryDetailsList);
            notifyDataSetChanged();
        }
    }


    class EntryViewHolder extends RecyclerView.ViewHolder {

        ImageView photoImageView;
        TextView courseNumberTextView;
        TextView nameTextView;
        TextView partnerNameTextView;

        EntryViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.entry_photo_image_view);
            courseNumberTextView = (TextView) itemView.findViewById(R.id.entry_course_number_text_view);
            nameTextView = (TextView) itemView.findViewById(R.id.entry_name_text_view);
            partnerNameTextView = (TextView) itemView.findViewById(R.id.entry_partner_name_text_view);
        }
    }
}
