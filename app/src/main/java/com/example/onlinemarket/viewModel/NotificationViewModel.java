package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.utils.OnlineMarketPreferences;
import com.example.onlinemarket.worker.PollWorker;

public class NotificationViewModel extends AndroidViewModel {
    private OnlineMarketPreferences mOnlineMarketPreferences;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        mOnlineMarketPreferences=OnlineMarketPreferences.getInstance(application);
    }

    public boolean isTaskScheduled() {
        return PollWorker.
                isWorkEnqueued(getApplication());
    }
    public void togglePolling() {
        //is alarm set
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        //long time = mOnlineMarketPreferences.getNotificationTime(getApplication());
       // PollWorker.enqueueWork(getApplication(), !isOn, time);

    }


}
