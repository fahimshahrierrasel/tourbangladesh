package com.treebricks.tourbangladesh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.model.SpotCardModel;
import com.treebricks.tourbangladesh.viewholder.SuggestionCardViewHolder;

import java.util.List;

/**
 * Created by fahim on 11/7/16.
 */

public class SuggestionCardAdapter extends RecyclerView.Adapter<SuggestionCardViewHolder> {

    private Context context;
    private List<SpotCardModel> allSpots;

    public SuggestionCardAdapter(List<SpotCardModel> allSpots, Context context) {
        this.allSpots = allSpots;
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
        SpotCardModel scm = allSpots.get(position);
        holder.bindData(context, scm.getSpotImageUrl(), scm.getSpotName(), scm.getSpotLocation(), scm.getSpotDistance());
    }

    @Override
    public int getItemCount() {
        return allSpots.size();
    }
}
