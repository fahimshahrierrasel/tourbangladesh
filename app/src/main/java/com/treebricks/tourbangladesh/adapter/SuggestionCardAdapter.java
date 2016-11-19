package com.treebricks.tourbangladesh.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.activities.Details;
import com.treebricks.tourbangladesh.model.SpotModel;
import com.treebricks.tourbangladesh.viewholder.SuggestionCardViewHolder;

import java.util.List;
import java.util.Locale;

/**
 * Created by fahim on 11/7/16.
 */

public class SuggestionCardAdapter extends RecyclerView.Adapter<SuggestionCardViewHolder> {

    private Context context;
    private List<SpotModel> allSpots;
    Location myLocation;


    public SuggestionCardAdapter(List<SpotModel> allSpots, Location myLocation, Context context) {
        this.allSpots = allSpots;
        this.myLocation = myLocation;
        this.context = context;
    }

    @Override
    public SuggestionCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_card, parent, false);
        return new SuggestionCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestionCardViewHolder holder, int position)
    {
        final SpotModel scm = allSpots.get(position);

        Location spotLocation = new Location(LocationManager.GPS_PROVIDER);
        spotLocation.setLatitude(Double.parseDouble(scm.getLatitude()));
        spotLocation.setLongitude(Double.parseDouble(scm.getLongitude()));

        float distance = spotLocation.distanceTo(myLocation) / 1000;
        String sDistance = String.format(Locale.US, "Distance %.2f km", distance);

        holder.bindData(context, scm.getSpotImageUrl(), scm.getSpotName(), scm.getSpotLocation(), sDistance);
        holder.suggestionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Details.class);
                details.putExtra("SPOT_NAME", scm.getSpotName());
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allSpots.size();
    }
}
