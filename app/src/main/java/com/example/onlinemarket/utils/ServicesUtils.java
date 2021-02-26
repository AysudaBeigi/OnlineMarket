package com.example.onlinemarket.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.event.NotificationEvent;
import com.example.onlinemarket.view.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ServicesUtils {
    private static String TAG = "OnlineMarket";
    private static final int NOTIFICATION_ID = 1;
    private static ProductRepository mProductRepository;
    private static OnlineMarketPreferences mOnlineMarketPreferences;


    public static void pollAndShowNotification(Context context, LifecycleOwner owner) {
        mOnlineMarketPreferences = OnlineMarketPreferences.getInstance(context);
        mProductRepository = ProductRepository.getInstance(context);
        mProductRepository.setLatestProductsLiveData();
        mProductRepository.getLatestProductsLiveData().observe(owner
                , new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        checkUpdateProductsState(products, context);
                    }
                });

    }

    public static void checkUpdateProductsState(List<Product> products, Context context) {

        if (products.get(0) == null)
            return;
        if (products.get(0).getId() != mOnlineMarketPreferences.getLatestProductId()) {
            sendNotificationEvent(context, products.get(0));
        } else {
            Log.d(TAG, "there is no new product");
        }
        mOnlineMarketPreferences.setLatestProductId(products.get(0).getId());


    }

    private static void sendNotificationEvent(Context context, Product product) {
        URL url;
        Bitmap image = null;
        try {
            if (product.getImages().size() != 0) {
                url = new URL(product.getImages().get(0).getSrc());
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.online_market_icon);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                MainActivity.newIntent(context),
                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = context.getResources().getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getResources().getString(R.string.new_product_title))
                .setContentText(product.getName())
                .setColor(context.getResources().getColor(R.color.digikala_red))
                .setSmallIcon(R.drawable.online_market_icon)
                .setContentIntent(pendingIntent)
                .setLargeIcon(image)
                .setAutoCancel(true)
                .build();

        NotificationEvent notificationEvent=new NotificationEvent(NOTIFICATION_ID,
                notification);
        EventBus.getDefault().post(notificationEvent);
    }

}