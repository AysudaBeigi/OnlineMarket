package com.example.onlinemarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private Context mContext;
    private List<Image> mImagesItems = new ArrayList<>();
    public static String TAG = "OnlineMarket";

    public List<Image> getImagesItems() {
        return mImagesItems;
    }

    public void setImagesItems(List<Image> imagesItems) {
        mImagesItems = imagesItems;
        notifyDataSetChanged();
    }

    public ImageSliderAdapter(Context context, List<Image> imagesItems) {
        Log.d(TAG,"ImageSliderAdapter");
        mContext = context;
        mImagesItems = imagesItems;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG,"ImageSliderAdapter +onCreateViewHolder");

        View view = LayoutInflater.from(mContext).
                inflate(R.layout.image_slider_item_view, null);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, final int position) {
        Log.d(TAG,"ImageSliderAdapter +onBindViewHolder");

        Image imageItem = mImagesItems.get(position);
        viewHolder.bindImageItem(imageItem);
    }

    @Override
    public int getCount() {
        return mImagesItems.size();
    }

    public class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder {

        private ShapeableImageView imageViewBackground;

        public ImageSliderViewHolder(View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }

        private void findHolderViews(View itemView) {
            imageViewBackground = itemView.findViewById(R.id.image_view_auto_image_slider);
        }

        private void bindImageItem(Image image) {
            Log.d(TAG,"ImageSliderAdapter +bindImageItem");

            if (image.getSrc().length() != 0)
                UIUtils.setImageUsingPicasso(image.getSrc(), imageViewBackground);

        }

    }

}
