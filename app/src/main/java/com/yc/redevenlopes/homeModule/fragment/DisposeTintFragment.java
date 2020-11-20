package com.yc.redevenlopes.homeModule.fragment;

import android.view.View;
import android.widget.ImageView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseDialogFragment;

/**
 * Created by suns  on 2020/11/18 16:19.
 */
public class DisposeTintFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dispose_tint;
    }

    @Override
    public void initViews() {
        ImageView ivClose = rootView.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


}
