package com.treebricks.tourbangladesh.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.treebricks.tourbangladesh.R;

/**
 * Created by fahim on 11/22/16.
 */

public class EmergencyCardViewHolder extends RecyclerView.ViewHolder
{
    private TextView emergServiceName;
    private TextView emergeServiceLocation;
    private TextView emergeServiceDistance;
    public CardView emergencyCard;

    public EmergencyCardViewHolder(View itemView) {
        super(itemView);
        emergServiceName = (TextView) itemView.findViewById(R.id.emerg_card_name);
        emergeServiceLocation = (TextView) itemView.findViewById(R.id.emer_card_location);
        emergeServiceDistance = (TextView) itemView.findViewById(R.id.emerg_card_distance);
        emergencyCard = (CardView) itemView.findViewById(R.id.emerg_card);
    }

    public void bindData(String name, String location, String distance)
    {
        emergServiceName.setText(name);
        emergeServiceLocation.setText(location);
        emergeServiceDistance.setText(distance);
    }

}
