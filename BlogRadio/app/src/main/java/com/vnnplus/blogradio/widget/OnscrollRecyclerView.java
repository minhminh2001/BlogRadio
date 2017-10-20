package com.vnnplus.blogradio.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Giangdv on 4/5/2017.
 */

public abstract class OnscrollRecyclerView extends RecyclerView.OnScrollListener {
    public static String TAG = OnscrollRecyclerView.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private SwipeRefreshLayout refreshLayout;


    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public OnscrollRecyclerView(GridLayoutManager linearLayoutManager, SwipeRefreshLayout refreshLayout) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.refreshLayout=refreshLayout;
    }

    public OnscrollRecyclerView(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            current_page++;
            onLoadMore(current_page);
            loading = true;
        }



    }


    public abstract void onLoadMore(int current_page);

    public void refresh() {
        previousTotal = 0;
        loading = true;
        current_page = 1;
        firstVisibleItem = visibleItemCount = totalItemCount = 0;
    }
}
