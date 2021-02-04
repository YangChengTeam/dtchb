package com.yc.redguess.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.contact.HelpQuestionContact;
import com.yc.redguess.homeModule.present.HelpQuestionPresenter;
import com.yc.redguess.homeModule.widget.selectrecyclerview.HotelEntity;
import com.yc.redguess.homeModule.widget.selectrecyclerview.HotelEntityAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class HelpQuestionActivity extends BaseActivity<HelpQuestionPresenter> implements HelpQuestionContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HotelEntityAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_help_question;
    }
    public static void helpJump(Context context){
        Intent intent=new Intent(context,HelpQuestionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initEventAndData() {
        setTitle("玩法攻略");
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new HotelEntityAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        //设置header
//        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        HotelEntity entity=new HotelEntity();
        ArrayList<HotelEntity.TagsEntity> allTagsList=new ArrayList<>();


        HotelEntity.TagsEntity tagsEntity=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo=new HotelEntity.TagsEntity.TagInfo();
        tagInfo.setTagName("完全真实有效，当天提现当天到账，无需人工审核！");
        tagInfo.setTitle("");
        tagsEntity.setTagsName("真的能提现吗？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList=new ArrayList<>();
        tagInfoList.add(tagInfo);
        tagsEntity.setTagInfoList(tagInfoList);
        allTagsList.add(tagsEntity);


        HotelEntity.TagsEntity tagsEntityTwo=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfotwo=new HotelEntity.TagsEntity.TagInfo();
        tagInfotwo.setTagName("打开app，点击左下角的升级，进入后即可看到升级要求");
        tagInfotwo.setTitle("1：");
        HotelEntity.TagsEntity.TagInfo tagInfo2=new HotelEntity.TagsEntity.TagInfo();
        tagInfo2.setTitle("2：");
        tagInfo2.setTagName("完成当天所有任务之后，并领取任务奖励，即可升级！");
        HotelEntity.TagsEntity.TagInfo tagInfo3=new HotelEntity.TagsEntity.TagInfo();
        tagInfo3.setTitle("3：");
        tagInfo3.setTagName("每天只能升一级，第二天登录即可再次升级");

        tagsEntityTwo.setTagsName("怎么升级？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoListtwo=new ArrayList<>();
        tagInfoListtwo.add(tagInfotwo);
        tagInfoListtwo.add(tagInfo2);
        tagInfoListtwo.add(tagInfo3);
        tagsEntityTwo.setTagInfoList(tagInfoListtwo);
        allTagsList.add(tagsEntityTwo);




        HotelEntity.TagsEntity tagsEntityThree=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfoThree=new HotelEntity.TagsEntity.TagInfo();
        tagInfoThree.setTagName("0.3元提现：0.3元需要2级");
        tagInfoThree.setTitle("0.3元提现：");

        HotelEntity.TagsEntity.TagInfo tagInfo4=new HotelEntity.TagsEntity.TagInfo();
        tagInfo4.setTagName("10元提现需要5级");
        tagInfo4.setTitle("10元提现：");

        HotelEntity.TagsEntity.TagInfo tagInfo5=new HotelEntity.TagsEntity.TagInfo();
        tagInfo5.setTagName("完成签到7天的任务，等级达到要求即可");
        tagInfo5.setTitle("50元提现：");

        HotelEntity.TagsEntity.TagInfo tagInfo6=new HotelEntity.TagsEntity.TagInfo();
        tagInfo6.setTagName("等级到20级可提现");
        tagInfo6.setTitle("100元提现：");

        HotelEntity.TagsEntity.TagInfo tagInfo7=new HotelEntity.TagsEntity.TagInfo();
        tagInfo7.setTagName("成功提现100元，即可提现300元");
        tagInfo7.setTitle("300元提现：");

        HotelEntity.TagsEntity.TagInfo tagInfo8=new HotelEntity.TagsEntity.TagInfo();
        tagInfo8.setTagName("成功提现300元，即可提现500元");
        tagInfo8.setTitle("500元提现：");




        tagsEntityThree.setTagsName("提现等级要求？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoListthree=new ArrayList<>();
        tagInfoListthree.add(tagInfoThree);
        tagInfoListthree.add(tagInfo4);
        tagInfoListthree.add(tagInfo5);
        tagInfoListthree.add(tagInfo6);
        tagInfoListthree.add(tagInfo7);
        tagInfoListthree.add(tagInfo8);
        tagsEntityThree.setTagInfoList(tagInfoListthree);
        allTagsList.add(tagsEntityThree);

//        HotelEntity.TagsEntity tagsEntityFour=new HotelEntity.TagsEntity();
//        HotelEntity.TagsEntity.TagInfo tagInfoFour=new HotelEntity.TagsEntity.TagInfo();
//        tagInfoFour.setTagName("完全真实有效，当天提现当天到账，无需人工审核！");
//        tagsEntityFour.setTagsName("真的能提现吗？");
//        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoListFour=new ArrayList<>();
//        tagInfoListFour.add(tagInfoFour);
//        tagsEntityFour.setTagInfoList(tagInfoListFour);
//        allTagsList.add(tagsEntityFour);


        entity.setAllTagsList(allTagsList);
        mAdapter.setData(entity.getAllTagsList());
        mAdapter.setOnItemClick(new HotelEntityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int section, int position) {
             //   Toast.makeText(HelpQuestionActivity.this,section+"=="+position,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}