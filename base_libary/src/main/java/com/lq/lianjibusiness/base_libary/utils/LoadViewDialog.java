package com.lq.lianjibusiness.base_libary.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lq.lianjibusiness.base_libary.R;


public class LoadViewDialog extends Dialog {

    private ImageView ivLoadview;
    public LoadViewDialog(@NonNull Context context) {
        super(context, R.style.CommentDialog);
        View view = View.inflate(context, R.layout.loadviewdialog_item, null);
        ivLoadview=view.findViewById(R.id.iv_loadview);
        init(view);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(R.drawable.loadview_gif).apply(options).into(ivLoadview);
    }

    private void init(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        super.show();
    }


}
