package com.yc.redevenlopes.updata;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.homeModule.module.bean.UpgradeInfo;

import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;


public class DownloadManager extends Service {
    private static DownloadManager sInstance;
    private static String parentDir;
    private static WeakReference<Context> mContext;

    private static Context getContext() {
        if (mContext != null && mContext.get() != null) {
            return mContext.get();
        } else if (sInstance != null) {
            return sInstance.getBaseContext();
        } else {
            return MyApplication.getInstance().getBaseContext();
        }
    }


    public static void setContext(WeakReference<Context> context) {
        mContext = context;
    }


    public static void init(WeakReference<Context> context) {
        OkDownload.with();
        parentDir = PathUtils.createDir(context.get(), "/apks");
    }


    public static long getFileSize(File file) {
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    public static void updateApp(UpgradeInfo upgradeInfo) {
        removeOldApk();
        String fileName = "redevenlopes.apk";
        DownloadTask task = new DownloadTask.Builder(upgradeInfo.getDownUrl(), new File(parentDir))
                .setConnectionCount(1)
                .setFilename(fileName)
                .setMinIntervalMillisCallbackProcess(300)
                .setPassIfAlreadyCompleted(true)
                .setPreAllocateLength(false)
                .build();
        task.setTag(upgradeInfo);
        task.enqueue(new DownloadListener4WithSpeed() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {

            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
                UpgradeInfo upgradeInfo = (UpgradeInfo) task.getTag();
                upgradeInfo.setTotalSize(info.getTotalLength());
                upgradeInfo.setOffsetSize(info.getTotalOffset());
                upgradeInfo.setDownloadStatus(DownloadStatus.DOWNLOADING);
                EventBus.getDefault().post(upgradeInfo);
            }

            @Override
            public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
                UpgradeInfo upgradeInfo = (UpgradeInfo) task.getTag();
                upgradeInfo.setSpeed(taskSpeed.speed());
                upgradeInfo.setOffsetSize(currentOffset);
                upgradeInfo.setDownloadStatus(DownloadStatus.DOWNLOADING);
                EventBus.getDefault().post(upgradeInfo);
            }

            @Override
            public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
                if (cause == EndCause.COMPLETED) {
                    installSelf();
                    UpgradeInfo upgradeInfo = (UpgradeInfo) task.getTag();
                    upgradeInfo.setDownloadStatus(DownloadStatus.DOWNLOADED);
                    EventBus.getDefault().post(upgradeInfo);
                }
            }
        });
    }

    public static void installSelf() {
        File file = new File(parentDir, "redevenlopes.apk");
        DownloadUtils.installApp(getContext(), file);
    }

    /**
     * 删除老版本APK文件
     */
    private static void removeOldApk() {
        if (!TextUtils.isEmpty(parentDir)){
            File fileName = new File(parentDir,"redevenlopes.apk");
            if (fileName != null && fileName.exists() && fileName.isFile()) {
                fileName.delete();
            }
        }
    }

//    public  void deleteFile(String apkUrl) {
//        File file = new File(parentDir, getFileName(gameInfo));
//        if (file.exists()) {
//            file.delete();
//        }
//    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sInstance = this;
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


}
