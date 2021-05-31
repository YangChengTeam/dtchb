package com.yc.redkingguess.homeModule.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yc.redkingguess.R;


import java.util.List;

/**
 * Created by suns  on 2020/11/17 13:56.
 */
public class DisposeNoticeAdapter extends RollingTextAdapter {


    private List<String> noticeInfos;


    public DisposeNoticeAdapter(List<String> noticeInfos) {
        this.noticeInfos = noticeInfos;
    }

    @Override
    public int getCount() {
        return noticeInfos.size();
    }

    @Override
    public View getView(Context context, View contentView, int position) {
        View view = View.inflate(context, R.layout.item_dispose_notice_view, null);
        ((TextView) view.findViewById(R.id.tv_dispose_notice1)).setText(noticeInfos.get(position));

        ((TextView) view.findViewById(R.id.tv_dispose_notice2)).setText(noticeInfos.get((position + 1) % noticeInfos.size()));
        ((TextView) view.findViewById(R.id.tv_dispose_notice3)).setText(noticeInfos.get((position + 2) % noticeInfos.size()));

        return view;
    }
}
