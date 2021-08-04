package com.yc.qqzz.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.bean.GameLoadBeans;
import com.yc.qqzz.homeModule.contact.LoadGameContract;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;
import com.yc.qqzz.homeModule.module.bean.LoadGameLoadApkBeans;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.LoadGamePresenter;
import com.yc.qqzz.service.event.Event;
import com.yc.qqzz.updata.DownloadManager;
import com.yc.qqzz.updata.DownloadStatus;
import com.yc.qqzz.utils.CacheDataUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.OnClick;

public class LoadGameActivity extends BaseActivity<LoadGamePresenter> implements LoadGameContract.View {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_moneys)
    TextView tvMoneys;
    @BindView(R.id.line_down)
    LinearLayout lineDown;
    @BindView(R.id.pb_update)
    ProgressBar progressBar;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.fl_update)
    FrameLayout flUpdate;
    private int taskId;
    private int postion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_load_game;
    }
    private  DownloadManager downloadManager;
    @Override
    public void initEventAndData() {
        setFullScreen();
        EventBus.getDefault().register(this);
        downloadManager=new DownloadManager();
        DayCashTashBeans.DownListBean beans = (DayCashTashBeans.DownListBean) getIntent().getSerializableExtra("beans");
        postion = getIntent().getIntExtra("postion", -1);
        if (beans!=null){
            taskId=beans.getId();
            String icon_url = beans.getIcon_url();
            if (!TextUtils.isEmpty(icon_url)){
                Glide.with(this).load(icon_url).into(ivIcon);
            }
            tvTitle.setText(beans.getApp_name());
            tvDes.setText(beans.getDescribe());
            tvMoneys.setText(beans.getMoney());
            gameLoadBeans=new GameLoadBeans();
            gameLoadBeans.setDownUrl(beans.getDown_url());
            gameLoadBeans.setDownloading(false);
        }
        if (gameLoadBeans!=null&&!TextUtils.isEmpty(gameLoadBeans.getDownUrl())){
            Log.d("ccc", "--------------initEventAndData: "+gameLoadBeans.getDownUrl());
            gameLoadBeans.setDownloading(true);
            downloadManager.gameLoad(gameLoadBeans);
            tvUpdate.setText("立即下载");
        }
    }

    public static void loadGameJump(Context context, DayCashTashBeans.DownListBean downListBean,int postion){
        Intent intent=new Intent(context,LoadGameActivity.class);
        intent.putExtra("beans",downListBean);
        intent.putExtra("postion",postion);
        context.startActivity(intent);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    private GameLoadBeans gameLoadBeans;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownload(GameLoadBeans gameLoadBeans) {
        Log.d("ccc", "-----event---------onDownload: ");
        double process = gameLoadBeans.getOffsetSize() / (double) gameLoadBeans.getTotalSize();
        this.gameLoadBeans.setDownloadStatus(gameLoadBeans.getDownloadStatus());
        if (gameLoadBeans.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
            progressBar.setProgress(100);
            tvUpdate.setText("下载完成");
            UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getCashdown(userInfo.getImei(),userInfo.getGroup_id(),taskId);
        } else if (gameLoadBeans.getDownloadStatus() == DownloadStatus.DOWNLOADING) {
            tvUpdate.setText("下载进度(" + (int) (process * 100) + "%)");
            progressBar.setProgress((int) (process * 100));
        }
    }


    @Override
    protected void onDestroy() {
        if (downloadManager!=null){
            downloadManager.cancle();
            downloadManager=null;
        }
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }



    @OnClick({R.id.iv_back, R.id.fl_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.fl_update:
                if (gameLoadBeans.isDownloading()) {
                    if (gameLoadBeans.getDownloadStatus() == DownloadStatus.DOWNLOADED) {
                        tvUpdate.setText("下载完成");
                        ToastUtil.showToast("完成下载任务，请去提现哦！");
                    }
                } else {
                    if (gameLoadBeans !=null){
                        gameLoadBeans.setDownloading(true);
                        downloadManager.gameLoad(gameLoadBeans);
                        tvUpdate.setText("立即下载");
                    }
                }
                break;
        }
    }

    @Override
    public void getCashdownApkSuccess(LoadGameLoadApkBeans data) {
        if (data!=null){
            EventBus.getDefault().post(new Event.LoadApkEvent(postion,data.getMoney()));
        }
    }
}