package com.yc.wxchb.beans.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.ComplaintEdContract;
import com.yc.wxchb.beans.present.ComplaintEdPresenter;
import com.yc.wxchb.utils.CacheDataUtils;


import butterknife.BindView;
import butterknife.OnClick;

public class ComplaintEdActivity extends BaseActivity<ComplaintEdPresenter> implements ComplaintEdContract.View {

    @BindView(R.id.tv_titles)
    TextView tvTitle;
    @BindView(R.id.et_contents)
    EditText etContents;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private String infoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_complaint_ed;
    }

    @Override
    public void initEventAndData() {
        setTitle("投诉");
        infoId = getIntent().getStringExtra("infoid");
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void comPlainEdJump(Context context,String infoId,String title){
        Intent intent=new Intent(context,ComplaintEdActivity.class);
        intent.putExtra("infoid",infoId);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }


    @OnClick({ R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                String trim = etContents.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    ToastUtil.showToast("请输入投诉内容");
                    return;
                }
                 mPresenter.comPlaint(CacheDataUtils.getInstance().getUserInfo().getId()+"",trim,infoId);
                break;
        }
    }

    @Override
    public void comPlaintSuccess() {
        ToastUtil.showToast("投诉成功");
        if (ComplaintActivity.instance != null && ComplaintActivity.instance.get() != null) {
            ComplaintActivity.instance.get().finish();
        }
        finish();
    }

}