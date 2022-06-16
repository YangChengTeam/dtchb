package com.yc.jsdsp.beans.adapter;


import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdsp.R;
import com.yc.jsdsp.beans.module.beans.ExpressBeans;
import java.util.List;


public class ExpressAdapter extends BaseQuickAdapter<ExpressBeans, BaseViewHolder> {
    public ExpressAdapter(@Nullable List<ExpressBeans> data) {
        super(R.layout.express_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressBeans item) {
      ((TextView) helper.getView(R.id.tv_name)).setText(item.getName());
    }
}
