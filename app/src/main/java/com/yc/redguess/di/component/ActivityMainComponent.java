package com.yc.redguess.di.component;

import android.app.Activity;

import com.yc.redguess.di.ActivityScope;
import com.yc.redguess.di.module.ActivityMainModule;
import com.yc.redguess.homeModule.activity.AllLeaderBoarderActivity;
import com.yc.redguess.homeModule.activity.AnswerActivity;
import com.yc.redguess.homeModule.activity.AnswerDetailsActivity;
import com.yc.redguess.homeModule.activity.GrabRedEvenlopesActivity;
import com.yc.redguess.homeModule.activity.GuessingActivity;
import com.yc.redguess.homeModule.activity.GuessingDetailsActivity;
import com.yc.redguess.homeModule.activity.GuessingResultActivity;
import com.yc.redguess.homeModule.activity.LoginActivity;
import com.yc.redguess.homeModule.activity.MainActivity;
import com.yc.redguess.homeModule.activity.MemberActivity;
import com.yc.redguess.homeModule.activity.MemberCenterActivity;
import com.yc.redguess.homeModule.activity.MemberLevelRewardActivity;
import com.yc.redguess.homeModule.activity.RedCountActivity;
import com.yc.redguess.homeModule.activity.RedRainActivity;
import com.yc.redguess.homeModule.activity.RobRedEvenlopesActivity;
import com.yc.redguess.homeModule.activity.SmokeHbActivity;
import com.yc.redguess.homeModule.activity.SnatchTreasureActivity;
import com.yc.redguess.homeModule.activity.SnatchTreasureDetailsActivity;
import com.yc.redguess.homeModule.activity.SnatchTreasureHistoryActivity;
import com.yc.redguess.homeModule.activity.SnatchTreasureRuleActivity;
import com.yc.redguess.homeModule.activity.TurnTableActivity;
import com.yc.redguess.homeModule.activity.WalletDetailActivity;
import com.yc.redguess.homeModule.activity.WithdrawActivity;
import com.yc.redguess.homeModule.activity.WithdrawLeadborderActivity;
import com.yc.redguess.homeModule.activity.WithdrawRecordActivity;

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

}
