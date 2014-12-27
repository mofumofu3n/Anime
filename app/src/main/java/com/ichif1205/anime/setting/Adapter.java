package com.ichif1205.anime.setting;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String[] mDataset;

    public Adapter(String[] dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View view = inflater.inflate(R.layout.layout_setting, parent, false);

        return new Usage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Usage) holder).bind(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class Usage extends RecyclerView.ViewHolder implements View.OnClickListener, SwipeDelegate {
        private final String TAG = Usage.class.getSimpleName();

        @InjectView(R.id.title)
        TextView titleView;

        private View mItemView;
        private Bitmap mItemViewCapture;
        private ViewGroup.MarginLayoutParams mParams;

        public Usage(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.inject(this, mItemView);
            mItemView.setOnClickListener(this);
            mItemView.setOnTouchListener(moving);
            mParams = (ViewGroup.MarginLayoutParams) mItemView.getLayoutParams();
        }

        private void bind(String title) {
            titleView.setText(title);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick");
        }

        @Override
        public void onLeftSwipe(float distanceX) {
            mParams.leftMargin = mParams.leftMargin + (int) distanceX;

            mItemView.layout(mParams.leftMargin,
                    mParams.topMargin,
                    mParams.leftMargin + mItemView.getWidth(),
                    mParams.topMargin + mItemView.getHeight());
        }

        @Override
        public void onEndSwipe() {

        }

        final View.OnTouchListener moving = new View.OnTouchListener() {

            private float downX;
            private float downY;

            private int downLeftMargin;
            private int downTopMargin;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // ViewGroup.MarginLayoutParamsでキャストすることで
                // FrameLayoutの子要素であっても同様に扱える。
                final ViewGroup.MarginLayoutParams param =
                        (ViewGroup.MarginLayoutParams) v.getLayoutParams();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    downX = event.getRawX();
                    downY = event.getRawY();

                    downLeftMargin = param.leftMargin;
                    downTopMargin = param.topMargin;

                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    param.leftMargin = downLeftMargin + (int) (event.getRawX() - downX);
                    param.topMargin = downTopMargin + (int) (event.getRawY() - downY);

                    v.layout(param.leftMargin,
                            param.topMargin,
                            param.leftMargin + v.getWidth(),
                            param.topMargin + v.getHeight());

                    return true;
                }

                return false;
            }
        };
    }

    public interface SwipeDelegate {
        void onLeftSwipe(float distanceX);

        void onEndSwipe();
    }

    public class LeftSwipeGestureListener implements View.OnTouchListener {
        private final String TAG = LeftSwipeGestureListener.class.getSimpleName();

        private SwipeDelegate mDelegate;
        private float mDownX;

        public LeftSwipeGestureListener(SwipeDelegate delegate) {
            mDelegate = delegate;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mDelegate == null) {
                return true;
            }

            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                mDownX = event.getX();
                return true;
            }

            if (MotionEvent.ACTION_UP == event.getAction()) {
                mDelegate.onEndSwipe();
                return false;
            }

            if (MotionEvent.ACTION_MOVE == event.getAction()) {
                final float distanceX = event.getRawX() - mDownX;

                if (distanceX <= 0) {
                    return true;
                }

                mDelegate.onLeftSwipe(distanceX);
                return true;
            }

            return false;
        }
    }

}
