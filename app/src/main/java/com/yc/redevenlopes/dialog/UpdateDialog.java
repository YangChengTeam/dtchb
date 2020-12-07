package com.yc.redevenlopes.dialog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.UpgradeInfo;
import com.yc.redevenlopes.updata.DownloadManager;
import com.yc.redevenlopes.updata.DownloadStatus;
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

    private UpgradeInfo upgradeInfo;

    public UpdateDialog(Context context) {
        super(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_app_update;
    }

    public void setInfo(UpgradeInfo upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
        mTitleTV.setText("版本:" + upgradeInfo.getVersion());
        RichText.from(upgradeInfo.getDesc()).into(mUpdateInfoTV);
        if (upgradeInfo.getForce_update() == 1) {//强制更新
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
                if (upgradeInfo.isDownloading()) {
                    if (upgradeInfo.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
                        DownloadManager.installSelf();
                    }
                } else {
                    if (upgradeInfo!=null){
                        upgradeInfo.setDownloading(true);
                        DownloadManager.updateApp(upgradeInfo);
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
    public void onDownload(UpgradeInfo upgradeInfo) {
        double process = upgradeInfo.getOffsetSize() / (double) upgradeInfo.getTotalSize();
        this.upgradeInfo.setDownloadStatus(upgradeInfo.getDownloadStatus());
        if (upgradeInfo.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
            mUpdateTV.setText("安装");
            mProcessBar.setProgress(100);
        } else if (upgradeInfo.getDownloadStatus() == DownloadStatus.DOWNLOADING) {
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
