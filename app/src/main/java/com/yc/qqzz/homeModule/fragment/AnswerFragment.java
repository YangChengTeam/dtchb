package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.qqzz.R;
import com.yc.qqzz.application.MyApplication;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.dialog.PrizeDialog;
import com.yc.qqzz.dialog.RedDialogThree;
import com.yc.qqzz.dialog.RedDialogTwo;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.activity.CashTaskActivity;
import com.yc.qqzz.homeModule.activity.DayUpgradeActivity;
import com.yc.qqzz.homeModule.activity.InvationfriendActivity;
import com.yc.qqzz.homeModule.activity.MainActivity;
import com.yc.qqzz.homeModule.adapter.AnswerFgAdapter;
import com.yc.qqzz.homeModule.adapter.LineRedAdapter;
import com.yc.qqzz.homeModule.bean.AnswerFanBeiBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgQuestionBeans;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;
import com.yc.qqzz.homeModule.contact.GrabRedFgContract;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.GrabRedFgPresenter;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.VUiKit;
import com.yc.qqzz.widget.MyPagerSnapHelper;
import com.yc.qqzz.widget.MyRelativeLayou;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFragment extends BaseLazyFragment<GrabRedFgPresenter> implements GrabRedFgContract.View {
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.tv_redTimes)
    TextView tvRedTimes;
    @BindView(R.id.line_lineRed)
    LinearLayout lineLineRed;
    @BindView(R.id.tv_answerNums)
    TextView tvAnswerNums;
    @BindView(R.id.tv_answerSureNums)
    TextView tvAnswerSureNums;
    @BindView(R.id.tv_answerTitle)
    TextView tvAnswerTitle;
    @BindView(R.id.tv_continueAnserNums)
    TextView tvContinueAnserNums;
    @BindView(R.id.progressbar_reward)
    ProgressBar progressbarReward;
    @BindView(R.id.tv_withDraw_continueAnserNums)
    TextView tvWithDrawContinueAnserNums;
    @BindView(R.id.tv_redNums)
    TextView tvRedNums;
    @BindView(R.id.tv_redAllNums)
    TextView tvRedAllNums;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.myRelativeLayou)
    MyRelativeLayou myRelativeLayou;
    private AnswerFgAdapter answerFgAdapter;
    private int page = 1;
    private int pagesize = 4;
    public int answerPositions;//答题的数量
    public int continueNums;//连对的数量
    public int righNums;//答对的数量
    private int videoType;// 1 答题  2 答题翻倍  3 在线红包  4 复活
    private int info_id;//看视频翻倍
    private boolean isAnswerSure;//答题是否正确
    private boolean isRedClick;//红包是否可以点击领取
    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grab_red, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
        activity= (MainActivity) getActivity();
        initRecyclerView();
        lineRedDialog();
        initDatas();
    }
    private MainActivity activity;
    private SignDialog withDrawDialog;
    private RedDialogTwo redDialogjiang;
    private RedDialogTwo redRewardDialogjiang;
    private SnatchDialog redPrizeDialogjiang;
    private PrizeDialog redPrizetwoDialog;


    public void initSignDialog() {
        withDrawDialog = new SignDialog(getActivity());
        View builder = withDrawDialog.builder(R.layout.withdraw_dialog);
        TextView tv_withDraws = builder.findViewById(R.id.tv_withDraws);
        TextView tv_withDraws_num = builder.findViewById(R.id.tv_withDraws_num);
        TextView tv_withDraws_allNum = builder.findViewById(R.id.tv_withDraws_allNum);
        TextView tv_withDraws_status = builder.findViewById(R.id.tv_withDraws_status);
        TextView tv_withDrawsTwo = builder.findViewById(R.id.tv_withDrawsTwo);
        TextView tv_withDraw = builder.findViewById(R.id.tv_withDraws);
        TextView tv_withDrawsTwo_num = builder.findViewById(R.id.tv_withDrawsTwo_num);
        TextView tv_withDrawsTwo_allNum = builder.findViewById(R.id.tv_withDrawsTwo_allNum);
        TextView tv_withDrawsTwo_status = builder.findViewById(R.id.tv_withDrawsTwo_status);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawDialog.setDismiss();
            }
        });
        withDrawDialog.setShow();
    }


    public void redRewardDialog(int type, String red_money, String other_money, String other_percent,int continue_num) {
        redDialogjiang = new RedDialogTwo(getActivity());
        View builder = redDialogjiang.builder(R.layout.redreward_dialog_item);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        LinearLayout line_sure=builder.findViewById(R.id.line_sure);
        TextView tv_liandui=builder.findViewById(R.id.tv_liandui);
        LinearLayout line_liandui=builder.findViewById(R.id.line_liandui);
        TextView tv_continue_nums=builder.findViewById(R.id.tv_continue_nums);
        if (type==1){
            line_liandui.setVisibility(View.GONE);
        }else {
            if (!TextUtils.isEmpty(other_money)){
                tv_moneys.setText("+"+red_money+"元");
                tv_liandui.setText(other_percent+"%("+other_money+"元)");
                tv_continue_nums.setText("x"+continue_num);
            }
            line_liandui.setVisibility(View.VISIBLE);
        }

        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==1){
                    videoType=1;
                }else {
                    videoType=2;
                }
                activity.showjiliAd(1,"answer_red");
            }
        });
        redDialogjiang.setShow();
    }



    public void redPrizeDialog(String money) {
        redPrizeDialogjiang = new SnatchDialog(getActivity());
        View builder = redPrizeDialogjiang.builder(R.layout.redprize_dialog_item);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        TextView tv_sure=builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redPrizeDialogjiang.setDismiss();
            }
        });
        tv_moneys.setText("+"+money+"元");
        redPrizeDialogjiang.setShow();
    }


    public void redPrizetwoDialog(String moneys) {
        redPrizetwoDialog = new PrizeDialog(getActivity());
        View builder = redPrizetwoDialog.builder(R.layout.redprizetwo_dialog_item);
        TextView tv_mone=builder.findViewById(R.id.tv_mone);
        tv_mone.setText("奖励您"+moneys+"元");
        redPrizetwoDialog.setShow();
    }

    private SnatchDialog redDialog;



    public void redRewardContinueDialog() {
        redRewardDialogjiang = new RedDialogTwo(getActivity());
        View builder = redRewardDialogjiang.builder(R.layout.redrewardcontiune_dialog_item);
        LinearLayout line_sure=builder.findViewById(R.id.line_sure);
        TextView tv_continueAn=builder.findViewById(R.id.tv_continueAn);
        TextView tv_next=builder.findViewById(R.id.tv_next);
        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoType=3;
                activity.showjiliAd(1,"answer_fuhuo");
                redRewardDialogjiang.setDismiss();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueNums=0;
                tvContinueAnserNums.setText(String.valueOf(continueNums));
                UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                mPresenter.questionAdd(userInfo.getImei(), userInfo.getGroup_id(), 1, continueNums);
                redRewardDialogjiang.setDismiss();
            }
        });
        redRewardDialogjiang.setOutCancle(false);
        redRewardDialogjiang.setShow();
    }



    public void redDialog() {
        redDialog = new SnatchDialog(getActivity());
        View builder = redDialog.builder(R.layout.reds_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        redDialog.setShow();
    }

    @OnClick({R.id.line_lineRed, R.id.iv_cashMoney, R.id.iv_dayMoneys, R.id.iv_dayUp,R.id.iv_invations,R.id.line_click_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_lineRed:
                if (activity.lineRedList!=null&&activity.lineRedList.size()>0){
                    if(lineRedAdapter!=null){
                        lineRedAdapter.setNewData(activity.lineRedList);
                        lineRedAdapter.notifyDataSetChanged();
                    }
                    if (!TextUtils.isEmpty(timesCount)&&tv_hour!=null&&line_times!=null){
                        tv_hour.setText(timesCount);
                        line_times.setVisibility(View.VISIBLE);
                    }else {
                        if (line_times!=null){
                            line_times.setVisibility(View.GONE);
                        }
                    }
                    if (lineRedDialog!=null){
                        lineRedDialog.setShow();
                    }
                }
                break;
            case R.id.iv_cashMoney:
                CashTaskActivity.CashTaskJump(getActivity());
                break;
            case R.id.iv_invations:
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
            case R.id.iv_dayMoneys:
                DayUpgradeActivity.DayUpgradeActivityJump(getActivity(), "2");
                break;
            case R.id.iv_dayUp:
                DayUpgradeActivity.DayUpgradeActivityJump(getActivity(), "1");
                break;
            case R.id.line_click_red:
                if (isRedClick){

                }else {
                    ToastUtil.showToast("再答对"+(next_reward_num-righNums)+"题就可以领红包啦！");
                }
                break;
        }
    }



    private int position;
    private ItemTouchHelper itemTouchHelper;

    private void initRecyclerView() {
        position = 0;;
    //    ScrollSpeedLinearLayoutManger linearLayoutManager=new ScrollSpeedLinearLayoutManger(getActivity(),LinearLayoutManager.HORIZONTAL,false);
      //  linearLayoutManager.setSpeedSlow();

//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };

       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                // true: consume touch event
//                // false: dispatch touch event
//                return true;
//            }
//        });
        Log.d("ccc", "-----55--------attachToRecyclerView: ");
        MyPagerSnapHelper snapHelper = new MyPagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);//初始化数据


        answerFgAdapter = new AnswerFgAdapter(null, getActivity());
        recyclerView.setNestedScrollingEnabled(false);
        answerFgAdapter.setOnClickListens(new AnswerFgAdapter.OnClickListens() {
            @Override
            public void clicks(int position, int answerPosition, boolean isSure) {
                answerPositions = answerPosition;
                isAnswerSure=isSure;
                UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (isSure) {//答对
                    righNums += 1;
                    continueNums += 1;
                    tvContinueAnserNums.setText(String.valueOf(continueNums));
                    mPresenter.questionAdd(userInfo.getImei(), userInfo.getGroup_id(), 0, continueNums);
                } else {//答错
                    redRewardContinueDialog();
                }
                List<AnswerFgBeans.QuestionListBean> data = answerFgAdapter.getData();
                if (answerPositions + 1 <= data.size() - 1) {
                    AnswerFgBeans.QuestionListBean questionListBean = data.get(answerPositions + 1);
                    if (questionListBean != null) {
                        nextAnswer(questionListBean.getQuestion());
                    }
                }
                if (answerPositions >= data.size() - 3) {
                    page += 1;
                    initDatas();
                }
            }
        });
        recyclerView.setAdapter(answerFgAdapter);
//        MyHelperCallback myCallBack=new MyHelperCallback();
//        itemTouchHelper = new ItemTouchHelper(myCallBack);
//        itemTouchHelper.attachToRecyclerView(recyclerView);//绑定RecyclerView
          myRelativeLayou.setCanScrollview(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                answerPositions = position;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    myRelativeLayou.setCanScrollview(true);
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    myRelativeLayou.setCanScrollview(false);
                    //滑动
                }
            }
        });
    }

    private void nextAnswer(String questionTitle) {
        VUiKit.postDelayed(600, () -> {
            recyclerView.smoothScrollToPosition(answerPositions + 1);
            tvAnswerTitle.setText(questionTitle);
        });
    }


    private void initDatas() {
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
        pagesize = 10;
        mPresenter.getAnswerList(userInfo.getImei(), userInfo.getGroup_id(), page, pagesize);
    }

    private int next_reward_num;
    private int reward_num;
    @Override
    public void getAnswerListSuccess(AnswerFgBeans data) {
        if (!TextUtils.isEmpty(data.getCash())){
            tvMoney.setText(data.getCash());
            ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        }
        AnswerFgBeans.QuestionConfigBean question_config = data.getQuestion_config();
        if (question_config!=null&&!TextUtils.isEmpty(question_config.getReward_num())){
             reward_num =Integer.parseInt(question_config.getReward_num());
        }
        next_reward_num = data.getNext_reward_num();
        isRedClick=false;
        tvRedAllNums.setText(next_reward_num+"");
        List<AnswerFgBeans.QuestionListBean> question_list = data.getQuestion_list();
        AnswerFgBeans.QuesionUserBean quesion_user = data.getQuesion_user();
        if (page == 1) {
            answerFgAdapter.setNewData(question_list);
            if (quesion_user != null) {
                tvRedNums.setText(quesion_user.getRight_num() + "");
                progressbarReward.setMax(next_reward_num * 10);
                progressbarReward.setProgress(quesion_user.getRight_num() * 10);
                tvWithDrawContinueAnserNums.setText((next_reward_num-quesion_user.getRight_num())+"");
                tvAnswerSureNums.setText(quesion_user.getRight_num() + "");
                tvContinueAnserNums.setText(quesion_user.getContinue_num() + "");
                tvAnswerNums.setText(quesion_user.getAnswer_num() + "");
            }
            if (question_list != null && question_list.size() > 0) {
                tvAnswerTitle.setText(question_list.get(0).getQuestion() + "");
            }
        } else {
            answerFgAdapter.addData(question_list);
        }
        answerFgAdapter.notifyDataSetChanged();
    }

    @Override
    public void questionAddSuccess(AnswerFgQuestionBeans data) {
        if (data != null) {
            info_id=data.getInfo_id();
            tvAnswerSureNums.setText(data.getRight_num() + "");
            righNums=data.getRight_num();
            tvAnswerNums.setText(data.getAnswer_num()+"");
            if (data.getRight_num()>=next_reward_num){
                next_reward_num=next_reward_num+reward_num;
                isRedClick=true;
                tvRedAllNums.setText(next_reward_num+"");
                startClickAnimontion();
            }
            progressbarReward.setMax(next_reward_num * 10);
            tvRedNums.setText(data.getRight_num() + "");
            progressbarReward.setProgress(data.getRight_num() * 10);
            tvWithDrawContinueAnserNums.setText((next_reward_num-data.getRight_num()+""));
            String  other_money=data.getOther_money();
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash =data.getCash();
                tvMoney.setText(data.getCash()+"元");
            }
            if (isAnswerSure){
                if (!TextUtils.isEmpty(other_money)&&!"0".equals(other_money)){//连对奖励
                    redRewardDialog(2,data.getRed_money(),data.getOther_money(),data.getOther_percent(),data.getContinue_num());
                }else {
                    redRewardDialog(1,"","","",data.getContinue_num());
                }
            }
        }
    }
   //开始动画
    private void startClickAnimontion() {


    }


    @Override
    public void gethbonlineSuccess(GetHomeLineRedBeans data) {
        if (!TextUtils.isEmpty(data.getCash())){
            ((MyApplication) MyApplication.getInstance()).cash =data.getCash();
            tvMoney.setText(data.getCash()+"元");
            ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        }
        long last_hb_time = data.getLast_hb_time();
        long sys_time = data.getSys_time();
        if (lineRedAdapter!=null){
            List<String> redLists = lineRedAdapter.getData();
            if (redLists!=null){
                redLists.set(redlinePosition,"1");
                lineRedAdapter.notifyDataSetChanged();
            }
        }
        activity.startCount(last_hb_time,sys_time,true);
        redPrizeDialog(data.getMoney());
    }

    @Override
    public void getDoubleVideoSuccess(AnswerFanBeiBeans data) {
           ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
           tvMoney.setText(""+data.getCash()+"元");
           redPrizetwoDialog(data.getMoney());
    }


    private int redlinePosition;
    private RedDialogThree lineRedDialog;
    private LineRedAdapter lineRedAdapter;
    public void lineRedDialog() {
        lineRedDialog = new RedDialogThree(getActivity());
        View builder = lineRedDialog.builder(R.layout.linered_dialog_item);
        View view = builder.findViewById(R.id.view);
        line_times=builder.findViewById(R.id.line_times);
        tv_hour=builder.findViewById(R.id.tv_hour);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineRedDialog.setDismiss();
            }
        });
        RecyclerView recyclerView = builder.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        lineRedAdapter = new LineRedAdapter(null);
        lineRedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isEnd){
                    redlinePosition=position;
                    List<String> data = adapter.getData();
                    if ("2".equals(data.get(position))){
                        videoType=3;
                        activity.showjiliAd(1,"answer_zaixian");
                    }else {
                        ToastUtil.showToast("该红包已经被拆了");
                    }
                }else {
                    ToastUtil.showToast("等倒计时结束可领取红包");
                }
            }
        });
        recyclerView.setAdapter(lineRedAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    private TextView tv_hour;
    private String timesCount;
    private LinearLayout line_times;
    private boolean isEnd=true;
    public void setTimesData(String timesContents,boolean isEnd) {
        this.timesCount=timesContents;
        this.isEnd=isEnd;
        if (!isEnd){
            if (tv_hour!=null&& !TextUtils.isEmpty(timesCount)){
                tv_hour.setText(timesCount);
                if (line_times!=null){
                    line_times.setVisibility(View.VISIBLE);
                }
            }
        }else {
            if (line_times!=null){
                line_times.setVisibility(View.GONE);
            }
        }
    }

    public void setVideoCallBack(boolean isVideoClick) {
        if (videoType==3){
            mPresenter.gethbonline(CacheDataUtils.getInstance().getUserInfo().getImei(),CacheDataUtils.getInstance().getUserInfo().getGroup_id());
        }else if (videoType==1){
             mPresenter.getDoubleVideo(CacheDataUtils.getInstance().getUserInfo().getId(),info_id);
        }else if (videoType==2){
            mPresenter.getDoubleVideo(CacheDataUtils.getInstance().getUserInfo().getId(),info_id);
        }else if (videoType==4){
            UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
            continueNums += 1;
            tvContinueAnserNums.setText(String.valueOf(continueNums));
            mPresenter.questionAdd(userInfo.getImei(), userInfo.getGroup_id(), 0, continueNums);
        }
    }
}