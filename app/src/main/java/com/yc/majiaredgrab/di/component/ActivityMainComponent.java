package com.yc.majiaredgrab.di.component;

import android.app.Activity;

import com.yc.majiaredgrab.di.ActivityScope;
import com.yc.majiaredgrab.di.module.ActivityMainModule;
import com.yc.majiaredgrab.homeModule.activity.AllLeaderBoarderActivity;
import com.yc.majiaredgrab.homeModule.activity.AnswerActivity;
import com.yc.majiaredgrab.homeModule.activity.AnswerDetailsActivity;
import com.yc.majiaredgrab.homeModule.activity.GrabRedEvenlopesActivity;
import com.yc.majiaredgrab.homeModule.activity.GuessingActivity;
import com.yc.majiaredgrab.homeModule.activity.GuessingDetailsActivity;
import com.yc.majiaredgrab.homeModule.activity.GuessingResultActivity;
import com.yc.majiaredgrab.homeModule.activity.HelpQuestionActivity;
import com.yc.majiaredgrab.homeModule.activity.InvationActivity;
import com.yc.majiaredgrab.homeModule.activity.LoginActivity;
import com.yc.majiaredgrab.homeModule.activity.MainActivity;
import com.yc.majiaredgrab.homeModule.activity.MemberActivity;
import com.yc.majiaredgrab.homeModule.activity.MemberCenterActivity;
import com.yc.majiaredgrab.homeModule.activity.MemberLevelRewardActivity;
import com.yc.majiaredgrab.homeModule.activity.RedCountActivity;
import com.yc.majiaredgrab.homeModule.activity.RedRainActivity;
import com.yc.majiaredgrab.homeModule.activity.RobRedEvenlopesActivity;
import com.yc.majiaredgrab.homeModule.activity.ShareActivity;
import com.yc.majiaredgrab.homeModule.activity.ShareWithDrawActivity;
import com.yc.majiaredgrab.homeModule.activity.SmokeHbActivity;
import com.yc.majiaredgrab.homeModule.activity.SnatchTreasureActivity;
import com.yc.majiaredgrab.homeModule.activity.SnatchTreasureDetailsActivity;
import com.yc.majiaredgrab.homeModule.activity.SnatchTreasureHistoryActivity;
import com.yc.majiaredgrab.homeModule.activity.SnatchTreasureRuleActivity;
import com.yc.majiaredgrab.homeModule.activity.TurnTableActivity;
import com.yc.majiaredgrab.homeModule.activity.WalletDetailActivity;
import com.yc.majiaredgrab.homeModule.activity.WithdrawActivity;
import com.yc.majiaredgrab.homeModule.activity.WithdrawLeadborderActivity;
import com.yc.majiaredgrab.homeModule.activity.WithdrawRecordActivity;

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
