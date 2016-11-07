package com.treebricks.tourbangladesh.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.treebricks.tourbangladesh.R;

/**
 * Created by fahim on 11/7/16.
 */

public class SuggestionCardViewHolder extends RecyclerView.ViewHolder {

    private ImageView cardSpotImage;
    private TextView cardSpotName;
    private TextView cardSpotLocation;
    private TextView cardSpotDistance;

    public SuggestionCardViewHolder(View itemView) {
        super(itemView);
        cardSpotImage = (ImageView) itemView.findViewById(R.id.card_spot_image);
        cardSpotName = (TextView) itemView.findViewById(R.id.card_spot_name);
        cardSpotLocation = (TextView) itemView.findViewById(R.id.card_spot_location);
        cardSpotDistance = (TextView) itemView.findViewById(R.id.card_spot_distance);
    }

    public void bindData(Context cardContext, String spotImageUrl, String spotName, String spotLocation, String spotDistance)
    {
        Glide.with(cardContext).load(spotImageUrl).crossFade().into(cardSpotImage);
        cardSpotName.setText(spotName);
        cardSpotLocation.setText(spotLocation);
        cardSpotDistance.setText(spotDistance);
    }
}
