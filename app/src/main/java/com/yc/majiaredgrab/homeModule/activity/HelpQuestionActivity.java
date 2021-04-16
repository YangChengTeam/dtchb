package com.yc.majiaredgrab.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.contact.HelpQuestionContact;
import com.yc.majiaredgrab.homeModule.present.HelpQuestionPresenter;
import com.yc.majiaredgrab.homeModule.widget.selectrecyclerview.HotelEntity;
import com.yc.majiaredgrab.homeModule.widget.selectrecyclerview.HotelEntityAdapter;

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
        tagInfo2.setTagName("完成当天所有任务之后，并领取任务奖励，完成后一定要记得领取哦！这样才能升级！");
        HotelEntity.TagsEntity.TagInfo tagInfo3=new HotelEntity.TagsEntity.TagInfo();
        tagInfo3.setTitle("3：");
        tagInfo3.setTagName("每天只能升一级，第二天登录即可再次升级！");

        tagsEntityTwo.setTagsName("怎么升级？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoListtwo=new ArrayList<>();
        tagInfoListtwo.add(tagInfotwo);
        tagInfoListtwo.add(tagInfo2);
        tagInfoListtwo.add(tagInfo3);
        tagsEntityTwo.setTagInfoList(tagInfoListtwo);
        allTagsList.add(tagsEntityTwo);


        HotelEntity.TagsEntity tagsEntity9=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo9=new HotelEntity.TagsEntity.TagInfo();
        tagInfo9.setTagName("目前只能一天升一级，多看视频多玩游戏会有隐藏升级卷哦！");
        tagInfo9.setTitle("");
        tagsEntity9.setTagsName("怎么快速升级？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList9=new ArrayList<>();
        tagInfoList9.add(tagInfo9);
        tagsEntity9.setTagInfoList(tagInfoList9);
        allTagsList.add(tagsEntity9);



        HotelEntity.TagsEntity tagsEntityThree=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfoThree=new HotelEntity.TagsEntity.TagInfo();
        tagInfoThree.setTagName("0.3元需要2级");
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




        HotelEntity.TagsEntity tagsEntity10=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo10=new HotelEntity.TagsEntity.TagInfo();
        tagInfo10.setTagName("红包金额大小和系统推荐的视频以及您观看视频的行为有关，请看完和下载试玩。");
        tagInfo10.setTitle("");
        tagsEntity10.setTagsName("为什么红包金额越来越少？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList10=new ArrayList<>();
        tagInfoList10.add(tagInfo10);
        tagsEntity10.setTagInfoList(tagInfoList10);
        allTagsList.add(tagsEntity10);



        HotelEntity.TagsEntity tagsEntity14=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo14=new HotelEntity.TagsEntity.TagInfo();
        tagInfo14.setTagName("有可能是网络不好的原因，可以多尝试几次，或者退出当前页面再次进入,如果还无法解决，可以重新启动APP哦！");
        tagInfo14.setTitle("");
        tagsEntity14.setTagsName("为什么有些红包和按钮点击没反应？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList14=new ArrayList<>();
        tagInfoList14.add(tagInfo14);
        tagsEntity14.setTagInfoList(tagInfoList14);
        allTagsList.add(tagsEntity14);

        HotelEntity.TagsEntity tagsEntity15=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo15=new HotelEntity.TagsEntity.TagInfo();
        tagInfo15.setTagName("打开发红包的首页，我们可以看到在右上角有一个宝箱，那就是在线红包了");
        tagInfo15.setTitle("");
        tagsEntity15.setTagsName(" 在线红包在哪里？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList15=new ArrayList<>();
        tagInfoList15.add(tagInfo15);
        tagsEntity15.setTagInfoList(tagInfoList15);
        allTagsList.add(tagsEntity15);


//        HotelEntity.TagsEntity tagsEntity12=new HotelEntity.TagsEntity();
//        HotelEntity.TagsEntity.TagInfo tagInfo12=new HotelEntity.TagsEntity.TagInfo();
//        tagInfo12.setTagName("红包金额大小和系统推荐的视频以及您观看视频的行为有关，请看完和下载试玩。");
//        tagInfo12.setTitle("");
//        tagsEntity12.setTagsName("为什么红包金额越来越少？");
//        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList12=new ArrayList<>();
//        tagInfoList12.add(tagInfo12);
//        tagsEntity12.setTagInfoList(tagInfoList12);
//        allTagsList.add(tagsEntity12);

        HotelEntity.TagsEntity tagsEntity13=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo13=new HotelEntity.TagsEntity.TagInfo();
        tagInfo13.setTagName("升到20级后，您可以享受平台会员收益分红，即使不做任务也有钱拿。至于多久能升到20级，需要看每个人的在app上投入的时间长短");
        tagInfo13.setTitle("");
        tagsEntity13.setTagsName("升到20级是不是就不用做任务？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList13=new ArrayList<>();
        tagInfoList13.add(tagInfo13);
        tagsEntity13.setTagInfoList(tagInfoList13);
        allTagsList.add(tagsEntity13);

        HotelEntity.TagsEntity tagsEntity16=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo16=new HotelEntity.TagsEntity.TagInfo();
        tagInfo16.setTagName("没有任何影响，可以正常提现以及使用app。");
        tagInfo16.setTitle("");
        tagsEntity16.setTagsName("游客登录模式有什么影响吗？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList16=new ArrayList<>();
        tagInfoList16.add(tagInfo16);
        tagsEntity16.setTagInfoList(tagInfoList16);
        allTagsList.add(tagsEntity16);

        HotelEntity.TagsEntity tagsEntity17=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo17=new HotelEntity.TagsEntity.TagInfo();
        tagInfo17.setTagName("如果当天任务没有完成，已完成任务依旧有效，不会被清零，可以第二天接着完成！");
        tagInfo17.setTitle("");
        tagsEntity17.setTagsName("如果当天任务没完成第二天会清零吗？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList17=new ArrayList<>();
        tagInfoList17.add(tagInfo17);
        tagsEntity17.setTagInfoList(tagInfoList17);
        allTagsList.add(tagsEntity17);


        HotelEntity.TagsEntity tagsEntity20=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo20=new HotelEntity.TagsEntity.TagInfo();
        tagInfo20.setTagName("中奖的用户请等待系统自动派发奖金，中奖会自动累计到您的个人钱包余额！");
        tagInfo20.setTitle("");
        tagsEntity20.setTagsName("夺宝竞猜中奖提现问题：");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList20=new ArrayList<>();
        tagInfoList20.add(tagInfo20);
        tagsEntity20.setTagInfoList(tagInfoList20);
        allTagsList.add(tagsEntity20);





        HotelEntity.TagsEntity tagsEntity18=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo18=new HotelEntity.TagsEntity.TagInfo();
        tagInfo18.setTagName("目前仅支持微信提现。");
        tagInfo18.setTitle("");
        tagsEntity18.setTagsName("可以提现到支付宝吗？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList18=new ArrayList<>();
        tagInfoList18.add(tagInfo18);
        tagsEntity18.setTagInfoList(tagInfoList18);
        allTagsList.add(tagsEntity18);


        HotelEntity.TagsEntity tagsEntity19=new HotelEntity.TagsEntity();
        HotelEntity.TagsEntity.TagInfo tagInfo19=new HotelEntity.TagsEntity.TagInfo();
        tagInfo19.setTagName("连续30天未登录app，账户会自动注销！");
        tagInfo19.setTitle("");
        tagsEntity19.setTagsName("怎么注销账户？");
        ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList19=new ArrayList<>();
        tagInfoList19.add(tagInfo19);
        tagsEntity19.setTagInfoList(tagInfoList19);
        allTagsList.add(tagsEntity19);
//        怎么快速升级？
//        目前只能一天升一级，多看视频多玩游戏会有隐藏升级卷哦！
//        为什么红包金额越来越少？
//        红包金额大小和系统推荐的视频以及您观看视频的行为有关，请看完和下载试玩。
//        升到20级是不是就不用做任务？
//        升到20级后，您可以享受平台会员收益分红，即使不做任务也有钱拿。至于多久能升到20级，需要看每个人的在app上投入的时间长短。




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