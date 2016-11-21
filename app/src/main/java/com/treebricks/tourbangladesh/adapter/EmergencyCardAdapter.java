package com.treebricks.tourbangladesh.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.activities.Details;
import com.treebricks.tourbangladesh.activities.Emergency;
import com.treebricks.tourbangladesh.model.EmergencyModel;
import com.treebricks.tourbangladesh.model.SpotModel;
import com.treebricks.tourbangladesh.viewholder.EmergencyCardViewHolder;
import com.treebricks.tourbangladesh.viewholder.SuggestionCardViewHolder;

import java.util.List;
import java.util.Locale;

/**
 * Created by fahim on 11/7/16.
 */

public class EmergencyCardAdapter extends RecyclerView.Adapter<EmergencyCardViewHolder> {

    private Context context;
    private List<EmergencyModel> allServices;
    Location myLocation;

    public EmergencyCardAdapter(Context context, List<EmergencyModel> allServices, Location myLocation) {
        this.context = context;
        this.allServices = allServices;
        this.myLocation = myLocation;
    }

    @Override
    public EmergencyCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_card, parent, false);
        return new EmergencyCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmergencyCardViewHolder holder, final int position)
    {
        final EmergencyModel emergencyModel = allServices.get(position);

        Location spotLocation = new Location(LocationManager.GPS_PROVIDER);
        spotLocation.setLatitude(emergencyModel.getProviderLatitude());
        spotLocation.setLongitude(emergencyModel.getProviderLongitude());

        float distance = spotLocation.distanceTo(myLocation) / 1000;
        String sDistance = String.format(Locale.US, "Distance %.2f km", distance);

        holder.bindData(emergencyModel.getProviderName(), emergencyModel.getProviderLocatino(), sDistance);
        holder.emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+ allServices.get(position).getProviderLatitude() +","+allServices.get(position).getProviderLongitude()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allServices.size();
    }
}
