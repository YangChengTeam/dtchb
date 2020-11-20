package com.yc.redevenlopes.homeModule.fragment;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseDialogFragment;
import com.yc.redevenlopes.updata.ScreenUtil;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

/**
 * Created by suns  on 2020/11/19 10:09.
 */
public class UserPolicyFragment extends BaseDialogFragment {


    private TextView tvUserPolicyContent;
    private NestedScrollView nestedScrollView;
    private TextView tvKnowBtn;

    private int contentHeight;
    private Rect rectF = new Rect();
    private boolean isCanClick = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_policy;
    }

    @Override
    public void initViews() {
        nestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        tvKnowBtn = rootView.findViewById(R.id.tv_know_btn);
        tvUserPolicyContent = rootView.findViewById(R.id.tv_user_policy_content);
        tvUserPolicyContent.post(() -> {
            contentHeight = tvUserPolicyContent.getHeight();

            tvUserPolicyContent.getLocalVisibleRect(rectF);
        });

        initListener();
    }

    private void initListener() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {


            if (rectF.bottom + scrollY >= contentHeight) {
                tvKnowBtn.setClickable(true);
                isCanClick = true;
                tvKnowBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_AB5B0F));
            }
//            Log.e(TAG, "run: " + "-- rect =" + rectF);

        });


        tvKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanClick)
                    dismiss();
            }
        });


    }



    @Override
    public float getWidthRatio() {
        return 0.9f;
    }

    @Override
    public int getHeight() {
        return ScreenUtil.getHeight(getActivity()) * 4 / 5;
    }


}
