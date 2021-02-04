package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Image;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder>{
    private Context mContext;
    private List<Image> mImagesItems = new ArrayList<>();

    public List<Image> getImagesItems() {
        return mImagesItems;
    }

    public void setImagesItems(List<Image> imagesItems) {
        mImagesItems = imagesItems;
        notifyDataSetChanged();
    }

    public ImageSliderAdapter(Context context, List<Image> imagesItems) {
        mContext = context;
        mImagesItems = imagesItems;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_slide, null);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, final int position) {

        Image imageItem = mImagesItems.get(position);
        viewHolder.bindImageItem(viewHolder, imageItem);
    }

    @Override
    public int getCount() {
        return mImagesItems.size();
    }

    public class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder {

        private ImageView imageViewBackground;
        private Image mImageItem;
        private View mItemView;

        public ImageSliderViewHolder(View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }

        private void findHolderViews(View itemView) {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            mItemView = itemView;
        }

        private void bindImageItem(ImageSliderViewHolder holder, Image image) {
            mImageItem = image;
            if (image.getSrc().length() != 0)
                Picasso.get()
                        .load(image.getSrc())
                        .into(imageViewBackground);

        }

    }

}
