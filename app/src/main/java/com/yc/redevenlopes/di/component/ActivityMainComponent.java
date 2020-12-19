package com.yc.redevenlopes.di.component;

import android.app.Activity;

import com.yc.redevenlopes.di.ActivityScope;
import com.yc.redevenlopes.di.module.ActivityMainModule;
import com.yc.redevenlopes.homeModule.activity.AllLeaderBoarderActivity;
import com.yc.redevenlopes.homeModule.activity.AnswerActivity;
import com.yc.redevenlopes.homeModule.activity.AnswerDetailsActivity;
import com.yc.redevenlopes.homeModule.activity.GrabRedEvenlopesActivity;
import com.yc.redevenlopes.homeModule.activity.GuessingActivity;
import com.yc.redevenlopes.homeModule.activity.GuessingDetailsActivity;
import com.yc.redevenlopes.homeModule.activity.GuessingResultActivity;
import com.yc.redevenlopes.homeModule.activity.LoginActivity;
import com.yc.redevenlopes.homeModule.activity.MainActivity;
import com.yc.redevenlopes.homeModule.activity.MemberActivity;
import com.yc.redevenlopes.homeModule.activity.MemberCenterActivity;
import com.yc.redevenlopes.homeModule.activity.MemberLevelRewardActivity;
import com.yc.redevenlopes.homeModule.activity.RobRedEvenlopesActivity;
import com.yc.redevenlopes.homeModule.activity.SnatchTreasureActivity;
import com.yc.redevenlopes.homeModule.activity.SnatchTreasureDetailsActivity;
import com.yc.redevenlopes.homeModule.activity.SnatchTreasureHistoryActivity;
import com.yc.redevenlopes.homeModule.activity.SnatchTreasureRuleActivity;
import com.yc.redevenlopes.homeModule.activity.TurnTableActivity;
import com.yc.redevenlopes.homeModule.activity.WalletDetailActivity;
import com.yc.redevenlopes.homeModule.activity.WithdrawActivity;
import com.yc.redevenlopes.homeModule.activity.WithdrawLeadborderActivity;
import com.yc.redevenlopes.homeModule.activity.WithdrawRecordActivity;

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

}
