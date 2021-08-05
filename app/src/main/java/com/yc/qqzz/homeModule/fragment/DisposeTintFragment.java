package com.yc.qqzz.homeModule.fragment;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseDialogFragment;
import com.yc.qqzz.utils.SoundPoolUtils;


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
            SpannableStringBuilder spannableStringBuilder = setNumColor(contents);
            tvContents.setText(spannableStringBuilder);
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (listenCash != null) {
                    listenCash.close();
                }
                try {
                    dismiss();
                }catch (Exception e){

                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (listenCash != null) {
                    listenCash.sure();
                }
                try {
                    dismiss();
                }catch (Exception e){

                }
            }
        });
    }

    public void setListenCash(OnClickListenCash listenCash) {
        this.listenCash = listenCash;
    }

    public OnClickListenCash listenCash;
    private String contents;
    private String sure;

    public  SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4513")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (".".equals(String.valueOf(a))) {
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4513")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return style;
    }

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
        void close();
    }


}
