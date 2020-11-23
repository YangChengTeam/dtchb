package com.yc.redevenlopes.homeModule.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseDialogFragment;

/**
 * Created by suns  on 2020/11/18 16:19.
 */
public class ExitTintFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exit_tint;
    }

    @Override
    public void initViews() {
        ImageView ivClose = rootView.findViewById(R.id.iv_close);
        TextView tvExit = rootView.findViewById(R.id.tv_exit);
        TextView tvGame = rootView.findViewById(R.id.tv_continue_game);

        ivClose.setOnClickListener(v -> dismiss());
        tvExit.setOnClickListener(v -> {
            if (exitListener != null) {
                exitListener.onExit();
            }
            dismiss();
        });
        tvGame.setOnClickListener(v -> dismiss());
    }

    private OnExitListener exitListener;

    public void setExitListener(OnExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public interface OnExitListener {
        void onExit();
    }

}
