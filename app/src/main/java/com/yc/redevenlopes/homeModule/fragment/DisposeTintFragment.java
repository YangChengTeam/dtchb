package com.yc.redevenlopes.homeModule.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseDialogFragment;

import butterknife.BindView;

/**
 * Created by suns  on 2020/11/18 16:19.
 */
public class DisposeTintFragment extends BaseDialogFragment {

    TextView tvContents;
    TextView tvSure;
    ImageView ivClose;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dispose_tint;
    }

    @Override
    public void initViews() {

         ivClose = rootView.findViewById(R.id.iv_close);
         tvContents = rootView.findViewById(R.id.tv_contents);
         tvSure = rootView.findViewById(R.id.tv_sure);
        if (!TextUtils.isEmpty(sure)){
            tvSure.setText(sure);
        }
        if (!TextUtils.isEmpty(contents)){
            tvContents.setText(contents);
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenCash != null) {
                    listenCash.sure();
                }
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenCash != null) {
                    listenCash.sure();
                }
                dismiss();
            }
        });

    }

    public void setListenCash(OnClickListenCash listenCash) {
        this.listenCash = listenCash;
    }

    public OnClickListenCash listenCash;
    private String contents;
    private String sure;

    public void setViewStatus(String contents, String sure) {
        if (!TextUtils.isEmpty(contents)) {
            this.contents=contents;
        }
        if (!TextUtils.isEmpty(sure)) {
            this.sure=sure;
        }
    }

    public interface OnClickListenCash {
        void sure();
    }


}
