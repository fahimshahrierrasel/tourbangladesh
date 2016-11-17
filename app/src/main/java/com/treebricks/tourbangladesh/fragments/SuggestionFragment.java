package com.treebricks.tourbangladesh.fragments;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotCardModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahim on 11/10/16.
 */

public class SuggestionFragment extends Fragment {
    RecyclerView spotsRecyclerView;
    List<SpotCardModel> allSpots;
    SuggestionCardAdapter suggestionCardAdapter;

    Cursor cursor = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.suggestion_layout, container, false);
        spotsRecyclerView = (RecyclerView) view.findViewById(R.id.spot_recyclerview);

        allSpots = new ArrayList<SpotCardModel>();


        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage Where District = ?", new String[] {"Dhaka"});
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                String spotName = cursor.getString(cursor.getColumnIndex("SpotName"));
                String spotImage = cursor.getString(cursor.getColumnIndex("ImageURL"));
                String spotDistrict = cursor.getString(cursor.getColumnIndex("District"));
                System.out.println("Spot Name: " + spotName + "\n" + "Spot Image" + spotImage);
                allSpots.add(new SpotCardModel(spotImage, spotName, spotDistrict, String.valueOf(i)+" km"));
                i++;
            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                databaseHelper.close();
            }
        }




        /*
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
        */

        suggestionCardAdapter = new SuggestionCardAdapter(allSpots, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        spotsRecyclerView.setLayoutManager(linearLayoutManager);
        spotsRecyclerView.setAdapter(suggestionCardAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
