package com.threemb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.threemb.R;
import com.threemb.activity.MainActivity;
import com.threemb.callers.OnItemClickListener;
import com.threemb.models.Advertise;
import com.threemb.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Amol on 9/5/2015.
 */
public class AdvertiseListingAdapter extends RecyclerView.Adapter<AdvertiseListingAdapter.ProjectViewHolder>  {
    private final Typeface tf;
    private OnItemClickListener mItemClickListener;
    private ArrayList<Advertise> advertiseList;
    private Context mContext;

    public AdvertiseListingAdapter(Context context, ArrayList<Advertise> advertiseList) {
        this.advertiseList = advertiseList;
        this.mContext = context;
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Sansation_Regular.ttf");
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advertise_listing_item, null);
        ProjectViewHolder viewHolder = new ProjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder projectViewHolder, int i) {
        final Advertise advertise = advertiseList.get(i);
        CommonUtils.setImage((MainActivity) mContext,advertise.imageUrl,projectViewHolder.iv_image,R.mipmap.ic_launcher);
        projectViewHolder.tv_title.setText(advertise.notificationTitle);
        projectViewHolder.tv_description.setText(advertise.notificationMessage);
        projectViewHolder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(advertise.trackerUrl));
                ((MainActivity)mContext).startActivity(intent);
            }
        });
    }

    public Advertise getAdvertise(int postion)
    {
        return advertiseList.get(postion);
    }

    @Override
    public int getItemCount() {
        return (null != advertiseList ? advertiseList.size() : 0);
    }
    public void addItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener=mItemClickListener;
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView tv_title,tv_description;
        protected ImageView iv_image;

        public ProjectViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_description = (TextView) view.findViewById(R.id.tv_description);
            this.iv_image=(ImageView)view.findViewById(R.id.iv_next);
            this.tv_title.setTypeface(tf);
            this.tv_description.setTypeface(tf);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null) {
                mItemClickListener.onItemClick(view,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            CommonUtils.showMessage(mContext, advertiseList.get(getAdapterPosition()).notificationTitle, advertiseList.get(getAdapterPosition()).notificationMessage, CommonUtils.MESSAGE.MESSAGE_BOX_WITH_OK);
            return false;
        }
    }
}

