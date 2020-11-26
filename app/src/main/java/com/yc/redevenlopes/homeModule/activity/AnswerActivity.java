package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.AnswserAdapter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.present.AnswerPresenter;
import com.yc.redevenlopes.homeModule.widget.ScrollWithRecyclerView;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.VUiKit;
import java.util.List;
import butterknife.BindView;

/**
 * 答题任务
 */
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    private AnswserAdapter answserAdapter;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initEventAndData() {
        setTitle("答题任务");
        initRecyclerView();
        mPresenter.getAnswerQuestionList(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void answerJump(Context context) {
        Intent intent = new Intent(context, AnswerActivity.class);
        context.startActivity(intent);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        answserAdapter = new AnswserAdapter(null);
        recyclerView.setAdapter(answserAdapter);
        answserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AnswerBeans> lists = adapter.getData();
                if ( lists.get(position).getIs_continue()==1){
                    index=position;
                    showRedDialog(lists.get(position).getMoney());
                }
            }
        });
    }
    private RedDialog redDialog;
    public void showRedDialog(String money) {
        redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_getRedDetails = builder.findViewById(R.id.tv_getRedDetails);
        TextView tv_getRedDes = builder.findViewById(R.id.tv_getRedDes);
        line_getRed.setVisibility(View.VISIBLE);
        rela_status.setVisibility(View.GONE);
        iv_open.setImageDrawable(getResources().getDrawable(R.drawable.red_ans));
        tv_type.setText("答题红包");
        tv_money.setText(money);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo();
            }
        });
        VUiKit.postDelayed(2000, () -> {
            iv_close.setVisibility(View.VISIBLE);
        });
        redDialog.setShow();
    }

    @Override
    public void getAnswerQuestionListSuccess(List<AnswerBeans> data) {
         answserAdapter.setNewData(data);
         answserAdapter.notifyDataSetChanged();
    }

    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.showRewardVideoVerticalAd(this, new AdCallback() {
            @Override
            public void onDismissed() {
                List<AnswerBeans> lists = answserAdapter.getData();
                AnswerBeans answerBeans = lists.get(index);
                answerBeans.setIs_continue(0);
                answserAdapter.notifyItemChanged(index);
                AnswerDetailsActivity.AnswerDetailsJump(AnswerActivity.this,answerBeans.getId()+"",answerBeans.getTotal());
                if (redDialog!=null){
                    redDialog.setDismiss();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "---------onNoAd: "+adError.getMessage()+"---"+adError.getCode());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }
        });
    }
}