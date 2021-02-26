package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.worker.PollWorker;

public class NotificationViewModel extends AndroidViewModel {

    public NotificationViewModel(@NonNull Application application) {
        super(application);
    }
    public boolean isTaskScheduled() {
        return PollWorker.
                isWorkEnqueued(getApplication());
    }

}
