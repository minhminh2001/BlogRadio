package com.vnnplus.blogradio.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Giangdv on 3/9/2017.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int numberColumn;

    public SpacesItemDecoration(int space, int numberColumn) {
        this.space = space;
        this.numberColumn = numberColumn;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
       /* if (parent.getChildAdapterPosition(view) % numberColumn == 0) {

        } else if (parent.getChildAdapterPosition(view) % numberColumn == (numberColumn - 1)) {
            outRect.left = space / 3;
            outRect.right = space;
        } else {
            outRect.left = (space * 2) / 3;
            outRect.right = (space * 2) / 3;
        }*/

        if (parent.getChildAdapterPosition(view) < numberColumn && outRect.top == 0) outRect.top = space;

        if (outRect.left == 0) outRect.left = space / 2;
        if (outRect.right == 0) outRect.right = space / 2;
        if (outRect.bottom == 0) outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items

    }
}
