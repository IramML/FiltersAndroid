package com.example.iram.photofilters.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iram.photofilters.Adapter.ThumbnailAdapter;
import com.example.iram.photofilters.Interface.FiltersListFragmentListener;
import com.example.iram.photofilters.Activities.MainActivity;
import com.example.iram.photofilters.R;
import com.example.iram.photofilters.Utils.BitmapUtils;
import com.example.iram.photofilters.Utils.SpacesItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FiltersListFragment extends Fragment implements FiltersListFragmentListener{
    RecyclerView recyclerView;
    ThumbnailAdapter adapter;
    List<ThumbnailItem> thumbnailItems;

    FiltersListFragmentListener listener;


    public void setListener(FiltersListFragmentListener listener) {
        this.listener = listener;
    }

    public FiltersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView=inflater.inflate(R.layout.fragment_filters_list, container, false);
        thumbnailItems=new ArrayList<>();
        adapter=new ThumbnailAdapter(thumbnailItems, this, getActivity());
        recyclerView=(RecyclerView)itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setAdapter(adapter);

        displayThumbnail(null);
        return itemView;
    }

    public void displayThumbnail(final Bitmap bitmap) {
        Runnable r=new Runnable() {
            @Override
            public void run() {
                Bitmap thumbImg;
                if (bitmap==null)thumbImg= BitmapUtils.getBitmapFromAssets(getActivity(), MainActivity.pictureName, 100, 100);
                else thumbImg=Bitmap.createScaledBitmap(bitmap, 100, 100, false);

                if (thumbImg==null)
                    return;
                ThumbnailsManager.clearThumbs();
                thumbnailItems.clear();

                ThumbnailItem thumbnailItem=new ThumbnailItem();
                thumbnailItem.image=thumbImg;
                thumbnailItem.filterName="Normal";
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters=FilterPack.getFilterPack(getActivity());

                for (Filter filter: filters){
                    ThumbnailItem thumbnailItem1=new ThumbnailItem();
                    thumbnailItem1.image=thumbImg;
                    thumbnailItem1.filter=filter;
                    thumbnailItem1.filterName=filter.getName();
                    ThumbnailsManager.addThumb(thumbnailItem1);
                }

                thumbnailItems.addAll(ThumbnailsManager.processThumbs(getActivity()));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        new Thread(r).start();
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (listener!=null) listener.onFilterSelected(filter);
    }
}
