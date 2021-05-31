package com.yc.redkingguess.di.component;

import android.app.Activity;

import com.yc.redkingguess.di.ActivityScope;
import com.yc.redkingguess.di.module.ActivityMainModule;
import com.yc.redkingguess.homeModule.activity.AllLeaderBoarderActivity;
import com.yc.redkingguess.homeModule.activity.AnswerActivity;
import com.yc.redkingguess.homeModule.activity.AnswerDetailsActivity;
import com.yc.redkingguess.homeModule.activity.GrabRedEvenlopesActivity;
import com.yc.redkingguess.homeModule.activity.GuessingActivity;
import com.yc.redkingguess.homeModule.activity.GuessingDetailsActivity;
import com.yc.redkingguess.homeModule.activity.GuessingResultActivity;
import com.yc.redkingguess.homeModule.activity.HelpQuestionActivity;
import com.yc.redkingguess.homeModule.activity.InvationActivity;
import com.yc.redkingguess.homeModule.activity.LoginActivity;
import com.yc.redkingguess.homeModule.activity.MainActivity;
import com.yc.redkingguess.homeModule.activity.MemberActivity;
import com.yc.redkingguess.homeModule.activity.MemberCenterActivity;
import com.yc.redkingguess.homeModule.activity.MemberLevelRewardActivity;
import com.yc.redkingguess.homeModule.activity.RedCountActivity;
import com.yc.redkingguess.homeModule.activity.RedRainActivity;
import com.yc.redkingguess.homeModule.activity.RobRedEvenlopesActivity;
import com.yc.redkingguess.homeModule.activity.ShareActivity;
import com.yc.redkingguess.homeModule.activity.ShareWithDrawActivity;
import com.yc.redkingguess.homeModule.activity.SmokeHbActivity;
import com.yc.redkingguess.homeModule.activity.SnatchTreasureActivity;
import com.yc.redkingguess.homeModule.activity.SnatchTreasureDetailsActivity;
import com.yc.redkingguess.homeModule.activity.SnatchTreasureHistoryActivity;
import com.yc.redkingguess.homeModule.activity.SnatchTreasureRuleActivity;
import com.yc.redkingguess.homeModule.activity.TurnTableActivity;
import com.yc.redkingguess.homeModule.activity.WalletDetailActivity;
import com.yc.redkingguess.homeModule.activity.WithdrawActivity;
import com.yc.redkingguess.homeModule.activity.WithdrawLeadborderActivity;
import com.yc.redkingguess.homeModule.activity.WithdrawRecordActivity;

import dagger.Component;


/**
 * Created by ccc
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityMainModule.class)
public interface ActivityMainComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(MemberActivity memberActivity);

    void inject(WithdrawActivity withdrawActivity);

    void inject(WithdrawRecordActivity withdrawRecordActivity);

    void inject(MemberCenterActivity memberCenterActivity);

    void inject(TurnTableActivity turnTableActivity);

    void inject(RobRedEvenlopesActivity robRedEvenlopesActivity);

    void inject(WithdrawLeadborderActivity withdrawLeadborderActivity);

    void inject(AnswerActivity answerActivity);

    void inject(AnswerDetailsActivity answerDetailsActivity);

    void inject(SnatchTreasureActivity snatchTreasureActivity);

    void inject(SnatchTreasureRuleActivity snatchTreasureRuleActivity);

    void inject(SnatchTreasureDetailsActivity snatchTreasureDetailsActivity);

    void inject(GuessingActivity guessingActivity);

    void inject(GuessingDetailsActivity guessingDetailsActivity);

    void inject(AllLeaderBoarderActivity allLeaderBoarderActivity);

    void inject(GuessingResultActivity guessingResultActivity);

    void inject(MemberLevelRewardActivity memberLevelRewardActivity);

    void inject(WalletDetailActivity walletDetailActivity);

    void inject(SnatchTreasureHistoryActivity snatchTreasureHistoryActivity);

    void inject(LoginActivity loginActivity);

    void inject(GrabRedEvenlopesActivity grabRedEvenlopesActivity);

    void inject(RedCountActivity redCountActivity);

    void inject(SmokeHbActivity smokeHbActivity);

    void inject(RedRainActivity RedRainActivity);

    void inject(HelpQuestionActivity HelpQuestionActivity);

    void inject(ShareActivity shareActivity);

    void inject(InvationActivity invationActivity);

    void inject(ShareWithDrawActivity invationActivity);

}
