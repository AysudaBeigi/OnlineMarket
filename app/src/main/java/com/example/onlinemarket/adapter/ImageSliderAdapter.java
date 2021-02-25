package com.example.onlinemarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.databinding.ImageSliderItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private Context mContext;
    private List<Image> mImagesItems = new ArrayList<>();
    public static String TAG = "OnlineMarket";
    private ImageSliderItemViewBinding mBinding;

    public void setImagesItems(List<Image> imagesItems) {
        Log.d(TAG, "ImageSliderAdapter : setImagesItems");

        mImagesItems = imagesItems;
        notifyDataSetChanged();
    }

    public ImageSliderAdapter(Context context, List<Image> imagesItems) {
        Log.d(TAG, "ImageSliderAdapter");
        mContext = context;
        mImagesItems = imagesItems;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "ImageSliderAdapter +onCreateViewHolder");

        mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext),
                        R.layout.image_slider_item_view, parent, false);
        return new ImageSliderViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, final int position) {
        Log.d(TAG, "ImageSliderAdapter +onBindViewHolder");
        Log.d(TAG, "ImageSliderAdapter : position is "+position);

        Image imageItem = mImagesItems.get(position);
        viewHolder.bindImageItem(imageItem);
    }

    @Override
    public int getCount() {
        return mImagesItems.size();
    }

    public class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder {

        public ImageSliderViewHolder(View itemView) {
            super(itemView);
        }

        private void bindImageItem(Image image) {
            Log.d(TAG, "ImageSliderAdapter +bindImageItem");

            if (image.getSrc().length() != 0)
                UIUtils.setImageUsingPicasso(image.getSrc(),
                        mBinding.imageViewAutoImageSlider);

        }

    }

}
