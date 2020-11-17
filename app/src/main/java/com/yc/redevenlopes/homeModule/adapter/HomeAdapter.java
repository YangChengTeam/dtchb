package com.yc.redevenlopes.homeModule.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.homeModule.module.bean.HomeBeans;
import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBeans, BaseViewHolder> {

    public HomeAdapter(List<HomeBeans> data) {
        super(data);
        addItemType(Constant.TYPE_ONE, R.layout.home_item);
        addItemType(Constant.TYPE_TWO, R.layout.home_item_two);
        addItemType(Constant.TYPE_THREE, R.layout.home_item_three);
        addItemType(Constant.TYPE_FOUR, R.layout.home_item_four);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeans item) {
        switch (helper.getItemViewType()) {
            case Constant.TYPE_ONE:

                break;
            case Constant.TYPE_TWO:

                break;
            case Constant.TYPE_THREE:

                break;
            case Constant.TYPE_FOUR:

                break;
       }
    }
}
