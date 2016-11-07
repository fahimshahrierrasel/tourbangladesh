package com.treebricks.tourbangladesh.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.model.SpotCardModel;

import java.util.ArrayList;
import java.util.List;

public class SpotSuggestion extends AppCompatActivity {

    RecyclerView spotsRecyclerView;
    List<SpotCardModel> allSpots;
    SuggestionCardAdapter suggestionCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_suggestion);
        spotsRecyclerView = (RecyclerView) findViewById(R.id.spot_recyclerview);

        allSpots = new ArrayList<SpotCardModel>();

        allSpots.add(new SpotCardModel("http://touristplace.bangladeshinformation.info/wp-content/uploads/sites/7/2014/09/Lalbagh_Fort_BangladeshInformation.Info1_.jpg",
                "Lalbagh Fort", "Dhaka", "12 km"));

        allSpots.add(new SpotCardModel("https://s2.yimg.com/sm/5542/12675206653_1528b784b8.jpg",
                "Shaheed Minar", "Dhaka", "15 km"));

        allSpots.add(new SpotCardModel("https://media-cdn.tripadvisor.com/media/photo-s/05/f3/69/85/bangabandhu-museum-terrace.jpg",
                "Bangabanfu Museum", "Dhaka", "10 km"));

        allSpots.add(new SpotCardModel("http://archive.dhakatribune.com/sites/default/files/imagecache/870x488_article_high/article/2013/06/09/botanical.gif",
                "National Botanical Garden", "Dhaka", "13 km"));

        allSpots.add(new SpotCardModel("http://visitbangladesh.gov.bd/wp-content/uploads/2015/03/Image-of-Ahsan-Manzil-1.jpg",
                "Ahsan Manzil", "Dhaka", "18 km"));

        allSpots.add(new SpotCardModel("http://static.panoramio.com/photos/large/99747335.jpg",
                "Suhrawardy Udyan", "Dhaka", "20 km"));

        allSpots.add(new SpotCardModel("http://4.bp.blogspot.com/_Mnz_ftEtg7c/S2XNWhRyZlI/AAAAAAAAAg8/4crZL7TWgLE/s400/ZOO+2.jpg",
                "Bangladesh National Zoo", "Dhaka", "17 km"));

        suggestionCardAdapter = new SuggestionCardAdapter(allSpots, SpotSuggestion.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpotSuggestion.this, LinearLayoutManager.VERTICAL, false);

        spotsRecyclerView.setLayoutManager(linearLayoutManager);
        spotsRecyclerView.setAdapter(suggestionCardAdapter);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Spot Suggestion");
        }


    }
}
