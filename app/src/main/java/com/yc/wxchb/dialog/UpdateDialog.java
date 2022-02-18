package com.yc.wxchb.dialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.UpgradeInfozq;
import com.yc.wxchb.updata.DownloadManager;
import com.yc.wxchb.updata.DownloadStatus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;

public class UpdateDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_update_info)
    TextView mUpdateInfoTV;

    @BindView(R.id.tv_update_infotwo)
    TextView mUpdateTVTwo;

    @BindView(R.id.tv_update)
    TextView mUpdateTV;
    @BindView(R.id.tv_qq)
    TextView tvqq;

    @BindView(R.id.fl_update)
    FrameLayout mUpdateFL;
    @BindView(R.id.pb_update)
    ProgressBar mProcessBar;
    @BindView(R.id.iv_close)
    ImageView mCloseIV;
    @BindView(R.id.ll_close)
    LinearLayout llClose;
    private Context context;
    private UpgradeInfozq upgradeInfozq;

    public UpdateDialog(Context context) {
        super(context);
        this.context=context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_app_updatezq;
    }

    public void setInfo(UpgradeInfozq upgradeInfozq) {
        this.upgradeInfozq = upgradeInfozq;
        mTitleTV.setText("是否升级到4.1.1版本？" + "");
        mUpdateTVTwo.setText("我们更新了赚钱更容易提现更简单的最新版本，点击下方按钮就能下载，任务奖励更丰富，赶紧下载吧！");
        mUpdateInfoTV.setText("亲爱的赚友们！");
        tvqq.setText("联系客服qq：1518760363");
        llClose.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        mUpdateFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgradeInfozq.isDownloading()) {
                    if (!TextUtils.isEmpty(upgradeInfozq.getPageName())){
                        boolean installed = isInstalled(upgradeInfozq.getPageName());
                        if (installed){
                            Intent intent = context.getPackageManager().getLaunchIntentForPackage(upgradeInfozq.getPageName());
                            if (intent != null) {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }else {
                            if (upgradeInfozq.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
                                DownloadManager.installSelf();
                            }
                        }
                    }else {
                        if (upgradeInfozq.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
                            DownloadManager.installSelf();
                        }
                    }

                } else {
                    if (upgradeInfozq !=null){
                        upgradeInfozq.setDownloading(true);
                        Log.d("ccc", "--4----onAnalysisNext: "+upgradeInfozq.getDownUrl()+"-----------");
                        DownloadManager.updateApp(upgradeInfozq);
                        mUpdateTV.setText("等待");
                    }
                }
            }
        });
        mCloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 检查包是否存在
     * @param packname
     * @return
     */
    private boolean isInstalled(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    @Override
    public void onBackPressed() {
    }

    // eventbus download
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownload(UpgradeInfozq upgradeInfozq) {
        double process = upgradeInfozq.getOffsetSize() / (double) upgradeInfozq.getTotalSize();
        this.upgradeInfozq.setDownloadStatus(upgradeInfozq.getDownloadStatus());
        if (upgradeInfozq.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
            mUpdateTV.setText("安装");
            mProcessBar.setProgress(100);
        } else if (upgradeInfozq.getDownloadStatus() == DownloadStatus.DOWNLOADING) {
            mUpdateTV.setText("下载进度(" + (int) (process * 100) + "%)");
            mProcessBar.setProgress((int) (process * 100));
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
