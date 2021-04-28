package com.yc.majiaredgrab.homeModule.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.dialog.SignDialog;
import com.yc.majiaredgrab.homeModule.adapter.InvationAdapter;
import com.yc.majiaredgrab.homeModule.contact.InvationContact;
import com.yc.majiaredgrab.homeModule.module.bean.InvationBeans;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsBeans;
import com.yc.majiaredgrab.homeModule.present.InvationPresenter;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InvationActivity extends BaseActivity<InvationPresenter> implements InvationContact.View {
    @BindView(R.id.tv_my_invation_code)
    TextView tvMyInvationCode;
    @BindView(R.id.tv_invation_people)
    TextView tvInvationPeople;
    @BindView(R.id.tv_effective_people)
    TextView tvEffectivePeople;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line_in)
    LinearLayout lineIn;
    private InvationAdapter invationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_invation;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initRecyclerView();
    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getInvationData(CacheDataUtils.getInstance().getUserInfo().getId());
    }


    private SignDialog inputCodeDialog;

    private void showInputCodeDialog() {
        inputCodeDialog = new SignDialog(this);
        View builder = inputCodeDialog.builder(R.layout.invation_share_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        EditText et_code=builder.findViewById(R.id.et_code);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCodeDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codes = et_code.getText().toString().replaceAll(" ", "");
                if (TextUtils.isEmpty(codes)){
                    ToastUtil.showToast("请输入邀请码");
                    return;
                }
                mPresenter.inputCode(CacheDataUtils.getInstance().getUserInfo().getId(),codes);
            }
        });
        inputCodeDialog.setOutCancle(false);
        if (!CommonUtils.isDestory(InvationActivity.this)) {
            inputCodeDialog.setShow();
        }
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        invationAdapter = new InvationAdapter(null);
        recyclerView.setAdapter(invationAdapter);
        invationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showRuleDialog(1);
            }
        });
    }

    private SignDialog snatchDialog;
    private void showRuleDialog(int type) {
        snatchDialog = new SignDialog(this);
        View builder = snatchDialog.builder(R.layout.invation_rule_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        LinearLayout lin_tixian=builder.findViewById(R.id.lin_tixian);
        if (type==1){
            lin_tixian.setVisibility(View.VISIBLE);
        }else {
            lin_tixian.setVisibility(View.GONE);
        }
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(InvationActivity.this)) {
            snatchDialog.setShow();
        }
    }


    @OnClick({R.id.line_copy_invationCode, R.id.tv_relu, R.id.tv_invation, R.id.tv_invatio_code, R.id.tv_sure, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_copy_invationCode:
                if (TextUtils.isEmpty(invite_code)){
                    ToastUtil.showToast("获取邀请码失败，请重新试试哦！");
                    return;
                }
                copyCode();
                break;
            case R.id.tv_relu:
                showRuleDialog(2);
                break;
            case R.id.tv_invation:
                ShareActivity.shareJump(this,invite_code);
                break;
            case R.id.tv_invatio_code:
                showInputCodeDialog();
                break;
            case R.id.tv_sure:
                ShareWithDrawActivity.ShareWithDrawJump(this,invite_code);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public static void InvationJupm(Context context) {
        Intent intent = new Intent(context, InvationActivity.class);
        context.startActivity(intent);
    }
    private  String invite_code;
    @Override
    public void getInvationDataSuccess(InvationsBeans data) {
        if (data != null) {
            tvInvationPeople.setText(data.getInvite_num() + "");
            tvEffectivePeople.setText(data.getInvite_meet_num()+"");
            invite_code = data.getInvite_code();
            tvMyInvationCode.setText(invite_code+ "");
            List<InvationBeans> invite_list = data.getInvite_list();
            if (invite_list != null) {
                invationAdapter.setNewData(invite_list);
                invationAdapter.notifyDataSetChanged();
            }
            if (data.getInvite_config() != null) {
                if (data.getInvite_config().getIs_open()==1){
                    lineIn.setVisibility(View.VISIBLE);
                }else {
                    lineIn.setVisibility(View.GONE);
                }
            }else {
                lineIn.setVisibility(View.GONE);
            }
        }
    }
    public void copyCode() {
        try {
            ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText(null, invite_code);
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtil.showToast("复制邀请码成功！");
        }catch (Exception e){
            ToastUtil.showToast("复制邀请码失败！");
        }
    }

    @Override
    public void getInputCodeSuccess() {
        ToastUtil.showToast("填写成功，您已经为好友完成邀请任务");
        if (inputCodeDialog!=null){
            inputCodeDialog.setDismiss();
        }
    }
}