package com.example.onlinemarket.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onlinemarket.utils.ServicesUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PollWorker extends Worker {
    private static String TAG = "OnlineMarket";
    private static final String POLL_WORKER_NAME = "com.example.onlinemarket.worker.PollWorkerName";
    private static LifecycleOwner mOwner;

    public PollWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ServicesUtils.pollAndShowNotification(getApplicationContext(), mOwner);
        return Result.success();
    }

    public static void enqueueWork(Context context, boolean isOn,
                                   long notificationTime, LifecycleOwner owner) {
        Log.d(TAG, "enqueueWork");
        mOwner = owner;
        WorkManager workManager = WorkManager.getInstance(context);
        if (isOn) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED).build();
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                    .Builder(PollWorker.class, notificationTime, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build();
            workManager.enqueueUniquePeriodicWork(POLL_WORKER_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

        } else {
            Log.d(TAG, "canceled");
            workManager.cancelUniqueWork(POLL_WORKER_NAME);
        }

    }

    public static boolean isWorkEnqueued(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);

        try {
            List<WorkInfo> workInfos = workManager.
                    getWorkInfosForUniqueWork(POLL_WORKER_NAME).get();

            for (WorkInfo workInfo : workInfos) {
                if (workInfo.getState() == WorkInfo.State.RUNNING ||
                        workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    return true;
                }
            }
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }
}
