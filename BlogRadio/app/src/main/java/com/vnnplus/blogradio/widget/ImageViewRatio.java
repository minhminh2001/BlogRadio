package com.vnnplus.blogradio.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.plus.blogradio.R;


/**
 * Created by ngson on 28/12/2014.
 */
public class ImageViewRatio extends ImageView {
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;

    private int datum;
    private float ratioWidth;
    private float ratioHeight;

    public ImageViewRatio(Context context) {
        super(context);
    }

    public ImageViewRatio(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttrs(attrs);
    }

    public ImageViewRatio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageViewRatio);

        ratioWidth = typedArray.getFloat(R.styleable.ImageViewRatio_ratioWidth, -1);
        ratioHeight = typedArray.getFloat(R.styleable.ImageViewRatio_ratioHeight, -1);
        datum = typedArray.getInt(R.styleable.ImageViewRatio_datum, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratioWidth > 0 && ratioHeight > 0) {
            int w;
            int h;

            if (datum == WIDTH) {
                w = MeasureSpec.getSize(widthMeasureSpec);
                h = (int) (((float)w / ratioWidth) * ratioHeight);
            } else {
                h = MeasureSpec.getSize(heightMeasureSpec);
                w = (int) (((float)h / ratioHeight) * ratioWidth);
            }

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
