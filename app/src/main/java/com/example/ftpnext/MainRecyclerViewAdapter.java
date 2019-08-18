package com.example.ftpnext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ftpnext.core.LogManager;
import com.example.ftpnext.database.FTPServerTable.FTPServer;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.CustomItemViewAdapter> {

    private static final String TAG = "MAIN RECYCLER VIEW ADAPTER";

    private List<FTPServer> mItemList;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private int lastPosition = -1;

    public MainRecyclerViewAdapter(List<FTPServer> iItemList, RecyclerView iRecyclerView, Context iContext) {
        mItemList = iItemList;
        mRecyclerView = iRecyclerView;
        mContext = iContext;
    }

    public MainRecyclerViewAdapter(RecyclerView iRecyclerView, Context iContext) {
        mItemList = new ArrayList<>();
        mRecyclerView = iRecyclerView;
        mContext = iContext;
    }

    @NonNull
    @Override
    public CustomItemViewAdapter onCreateViewHolder(@NonNull ViewGroup iViewGroup, int iI) {

        LinearLayout lLayout = (LinearLayout) LayoutInflater.
                from(iViewGroup.getContext()).inflate(R.layout.activity_main_item, iViewGroup, false);

        return new CustomItemViewAdapter(lLayout,
                (TextView) lLayout.findViewById(R.id.main_recycler_item_main_text),
                (TextView) lLayout.findViewById(R.id.main_recycler_item_secondary_text));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomItemViewAdapter iCustomItemViewAdapter, int iPosition) {
        FTPServer lServer = mItemList.get(iPosition);

//        iCustomItemViewAdapter.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogManager.info("click on item view");
//            }
//        });

        iCustomItemViewAdapter.mMainText.setText(lServer.getName());
        iCustomItemViewAdapter.mSecondaryText.setText(lServer.getUser());
//        LogManager.error(String.valueOf(iPosition));

        Animation animation = AnimationUtils.loadAnimation(mRecyclerView.getContext(), R.anim.item_animation_fall_down);
        iCustomItemViewAdapter.itemView.startAnimation(animation);

        // If the bound view wasn't previously displayed on screen, it's animated
//        if (iPosition > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(mRecyclerView.getContext(), R.anim.item_animation_fall_down);
//            iCustomItemViewAdapter.itemView.startAnimation(animation);
//            lastPosition = iPosition;
//        }

//        Animation animation = AnimationUtils.loadAnimation(mRecyclerView.getContext(),
//                (position > lastPosition) ?
//                        : R.anim.down_from_top);
//        holder.itemView.startAnimation(animation);
//        lastPosition = position;

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

//    public void runLayoutAnimation() {
//        final LayoutAnimationController lAnimation;
//
//        lAnimation = AnimationUtils.loadLayoutAnimation(
//                mRecyclerView.getContext(),
//                R.anim.layout_animation_fall_down);
//
//        mRecyclerView.setLayoutAnimation(lAnimation);
//        notifyDataSetChanged();
//        mRecyclerView.scheduleLayoutAnimation();
//    }

    public void insertItem(FTPServer iItem) {
        if (mItemList == null) {
            LogManager.error(TAG, "Cannot insert an item if the list of items isn't initialized.");
            return;
        }
        mItemList.add(iItem);
        notifyItemRangeInserted(mItemList.size() + 1, 1);
    }

    public void insertItem(FTPServer iItem, int iPosition) {
        if (mItemList == null) {
            LogManager.error(TAG, "Cannot insert an item if the list of items isn't initialized.");
            return;
        }
        mItemList.add(iPosition, iItem);
        notifyItemRangeInserted(iPosition, 1);
    }

    public void setData(List<FTPServer> iData) {
        if (mItemList == null) {
            LogManager.error(TAG, "Cannot set the data if the given parameter is null.");
            return;
        }
        mItemList = new ArrayList<FTPServer>(iData);
        notifyDataSetChanged();
    }

    static class CustomItemViewAdapter extends RecyclerView.ViewHolder {
        TextView mMainText;
        TextView mSecondaryText;

        public CustomItemViewAdapter(@NonNull View iItemView, TextView iMainText, TextView iSecondaryText) {
            super(iItemView);
            mMainText = iMainText;
            mSecondaryText = iSecondaryText;
        }
    }

}
