package com.yc.rrdsprj.beans.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.rrdsprj.R;
import com.yc.rrdsprj.base.BaseDialogFragment;
import com.yc.rrdsprj.utils.SoundPoolUtils;


/**
 * Created by suns  on 2020/11/18 16:19.
 */
public class ExitTintFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exit_tintzq;
    }

    @Override
    public void initViews() {
        ImageView ivClose = rootView.findViewById(R.id.iv_close);
        TextView tvExit = rootView.findViewById(R.id.tv_exit);
        TextView tvGame = rootView.findViewById(R.id.tv_continue_game);
        TextView tv_contents = rootView.findViewById(R.id.tv_contents);
        if (!TextUtils.isEmpty(contents)){
            tv_contents.setText(contents);
        }
        if (!TextUtils.isEmpty(cancle)){
            tvExit.setText(cancle);
        }
        if (!TextUtils.isEmpty(sure)){
            tvGame.setText(sure);
        }
        tvGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (exitListener != null) {
                    exitListener.onSure();
                }
                dismiss();
            }
        });

        ivClose.setOnClickListener(v -> dismiss());
        tvExit.setOnClickListener(v -> {
            SoundPoolUtils instance = SoundPoolUtils.getInstance();
            instance.initSound();
            if (exitListener != null) {
                exitListener.onExit();
            }
            dismiss();
        });
    }
    public String contents;
    public String sure;
    public String cancle;
    public void setViewStatus(String contents,String sure,String cancle){
         this.contents=contents;
         this.sure=sure;
         this.cancle=cancle;
    }
    private OnExitListener exitListener;

    public void setExitListener(OnExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public interface OnExitListener {
        void onExit();
        void onSure();
    }

}
