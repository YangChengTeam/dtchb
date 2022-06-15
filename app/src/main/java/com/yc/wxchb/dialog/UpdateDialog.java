package com.yc.wxchb.dialog;

import android.content.Context;
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
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;

public class UpdateDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_update_info)
    TextView mUpdateInfoTV;
    @BindView(R.id.tv_update)
    TextView mUpdateTV;

    @BindView(R.id.fl_update)
    FrameLayout mUpdateFL;
    @BindView(R.id.pb_update)
    ProgressBar mProcessBar;
    @BindView(R.id.iv_close)
    ImageView mCloseIV;
    @BindView(R.id.ll_close)
    LinearLayout llClose;

    private UpgradeInfozq upgradeInfozq;

    public UpdateDialog(Context context) {
        super(context);
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
        mTitleTV.setText("版本:" + upgradeInfozq.getVersion());
        RichText.from(upgradeInfozq.getDesc()).into(mUpdateInfoTV);
        if (upgradeInfozq.getForce_update() == 1) {//强制更新
            llClose.setVisibility(View.GONE);
        }else {
            llClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initViews() {
        mUpdateFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgradeInfozq.isDownloading()) {
                    if (upgradeInfozq.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
                        DownloadManager.installSelf();
                    }
                } else {
                    if (upgradeInfozq !=null){
                        upgradeInfozq.setDownloading(true);
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
