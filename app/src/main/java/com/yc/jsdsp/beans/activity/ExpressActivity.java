package com.yc.jsdsp.beans.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.jsdsp.R;
import com.yc.jsdsp.base.BaseActivity;
import com.yc.jsdsp.beans.adapter.ExpressAdapter;
import com.yc.jsdsp.beans.contact.ExpressContract;
import com.yc.jsdsp.beans.module.beans.ExpressBeans;
import com.yc.jsdsp.beans.present.ExpressPresenter;
import com.yc.jsdsp.utils.CacheDataUtils;
import com.yc.jsdsp.utils.DisplayUtil;
import com.yc.jsdsp.widget.DividerItemDecorations;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExpressActivity extends BaseActivity<ExpressPresenter> implements ExpressContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.line_layouts)
    LinearLayout lineLayouts;
    PopupWindow popupWindow;
    @BindView(R.id.tv_contents)
    TextView tvContents;
    @BindView(R.id.tv_titles)
    TextView tvTitles;


    private RecyclerView recyclerView;
    private  ExpressAdapter expressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_express;
    }

    @Override
    public void initEventAndData() {
        toolbarTitle.setText("快递查询");
        showPopupWindow();
        mPresenter.getExpressData(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void expressJump(Context context){
        Intent intent=new Intent(context,ExpressActivity.class);
        context.startActivity(intent);
    }
    private   String content;
    private void showPopupWindow() {
        popupWindow = new PopupWindow(this);
   /*     popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*/
        View inflate = LayoutInflater.from(this).inflate(R.layout.express_select, null);
        recyclerView = inflate.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecorations(this, R.drawable.devider_grey_1_1dp));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        expressAdapter=new ExpressAdapter(null);
        recyclerView.setAdapter(expressAdapter);
        expressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ExpressBeans> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (position==i){
                        data.get(position).setSelect(true);
                        content = data.get(position).getContent();
                        tvTitles.setText(data.get(i).getName());
                        RichText.from(content).into(tvContents);
                    }else {
                        data.get(position).setSelect(false);
                    }
                }
                expressAdapter.notifyDataSetChanged();
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });

        popupWindow.setContentView(inflate);
        popupWindow.setFocusable(true);
        // 设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
    }

    private void initShow(){
       if (popupWindow!=null){
           if (popupWindow.isShowing()){
               popupWindow.dismiss();
           }else {
               int wh = DisplayUtil.dip2px(this, 13);
               popupWindow.showAsDropDown(lineLayouts,wh,0);
              // popupWindow.showAtLocation(lineLayouts, Gravity.TOP, -wh, 0);
               popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                   @Override
                   public void onDismiss() {

                   }
               });
           }
       }
    }


    @OnClick({R.id.toolbar_back,R.id.line_layouts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.line_layouts:
                initShow();
                break;
        }
    }

    @Override
    public void getExpressDataSuccess(List<ExpressBeans> data) {
         if (expressAdapter!=null&&data!=null){
             if (data!=null){
                 for (int i = 0; i < data.size(); i++) {
                     if (i==0){
                         data.get(i).setSelect(true);
                         content=data.get(i).getContent();
                         tvTitles.setText(data.get(i).getName());
                         RichText.from(content).into(tvContents);
                     }
                 }
             }
             expressAdapter.setNewData(data);
             expressAdapter.notifyDataSetChanged();
         }
    }
}