package com.example.smart_cart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ShoppingListView extends ListView{
    private static final String TAG = ShoppingListView.class.getSimpleName();

    private int touchSlop;
    private boolean isSliding;
    private int xDown;
    private int yDown;
    private int xMove;
    private int yMove;
    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;
    private int mPopupWindowHeight;
    private int mPopupWindowWidth;

    private TextView delete;
    private DeleteClickListener mListener;
    private View mCurrentView;
    private int mCurrentViewPos;


    public ShoppingListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = mInflater.inflate(R.layout.delete_btn, null);
        delete = (TextView) view.findViewById(R.id.delete);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mPopupWindow.getContentView().measure(0, 0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;

                if (mPopupWindow.isShowing()) {
                    dismissPopWindow();
                    return false;
                }

                mCurrentViewPos = pointToPosition(xDown, yDown);
                View view = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                mCurrentView = view;

                break;

            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mCurrentView == null) {
            return false;
        }

        int action = ev.getAction();

        if (isSliding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    mCurrentView.getLocationOnScreen(location);
                    mPopupWindow.update();

                    delete.setHeight(getMeasuredHeight()/getChildCount());

                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP,
                            location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2
                                    - mPopupWindowHeight );

                    delete.setOnClickListener(new OnClickListener() {
                        @Override public void onClick(View view) {
                            if (mListener != null) {
                                mListener.onClickDelete(mCurrentViewPos);
                                mPopupWindow.dismiss();
                            }
                        }
                    });

                    break;

                case MotionEvent.ACTION_UP:
                    isSliding = false;

                    break;
            }


            return true;
        }
        return super.onTouchEvent(ev);

    }


    private void dismissPopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }

    }

    public void setDelButtonClickListener(DeleteClickListener listener) {
        mListener = listener;
    }

}


