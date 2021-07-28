package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.dialog.PrizeDialog;
import com.yc.qqzz.dialog.RedDialogThree;
import com.yc.qqzz.dialog.RedDialogTwo;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.activity.CashTaskActivity;
import com.yc.qqzz.homeModule.activity.DayUpgradeActivity;
import com.yc.qqzz.homeModule.activity.MainActivity;
import com.yc.qqzz.homeModule.adapter.AnswerFgAdapter;
import com.yc.qqzz.homeModule.adapter.LineRedAdapter;
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
import com.yc.qqzz.widget.ScrollSpeedLinearLayoutManger;

import java.util.ArrayList;
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


    public void redRewardDialog() {
        redDialogjiang = new RedDialogTwo(getActivity());
        View builder = redDialogjiang.builder(R.layout.redreward_dialog_item);


        redDialogjiang.setShow();
    }

    public void redRewardContinueDialog() {
        redRewardDialogjiang = new RedDialogTwo(getActivity());
        View builder = redRewardDialogjiang.builder(R.layout.redrewardcontiune_dialog_item);
        redRewardDialogjiang.setShow();
    }

    public void redPrizeDialog() {
        redPrizeDialogjiang = new SnatchDialog(getActivity());
        View builder = redPrizeDialogjiang.builder(R.layout.redprize_dialog_item);
        redPrizeDialogjiang.setShow();
    }


    public void redPrizetwoDialog() {
        redPrizetwoDialog = new PrizeDialog(getActivity());
        View builder = redPrizetwoDialog.builder(R.layout.redprizetwo_dialog_item);
        redPrizetwoDialog.setShow();
    }

    private SnatchDialog redDialog;

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

    @OnClick({R.id.line_lineRed, R.id.iv_cashMoney, R.id.iv_dayMoneys, R.id.iv_dayUp})
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
            case R.id.iv_dayMoneys:
                DayUpgradeActivity.DayUpgradeActivityJump(getActivity(), "2");
                break;
            case R.id.iv_dayUp:
                DayUpgradeActivity.DayUpgradeActivityJump(getActivity(), "1");
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
                UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (isSure) {
                    righNums += 1;
                    continueNums += 1;
                    mPresenter.questionAdd(userInfo.getImei(), userInfo.getGroup_id(), 0, continueNums);
                } else {
                    continueNums=0;
                    mPresenter.questionAdd(userInfo.getImei(), userInfo.getGroup_id(), 1, continueNums);
                }
                tvContinueAnserNums.setText(String.valueOf(continueNums));
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


    @Override
    public void getAnswerListSuccess(AnswerFgBeans data) {
        List<AnswerFgBeans.QuestionListBean> question_list = data.getQuestion_list();
        AnswerFgBeans.QuesionUserBean quesion_user = data.getQuesion_user();
        List<AnswerFgBeans.QuestionConfigBean> question_config = data.getQuestion_config();
        if (page == 1) {
            answerFgAdapter.setNewData(question_list);
            if (quesion_user != null) {
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
            tvAnswerSureNums.setText(data.getRight_num() + "");
            tvAnswerNums.setText(data.getAnswer_num()+"");
        }
    }

    @Override
    public void gethbonlineSuccess(GetHomeLineRedBeans data) {
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
                        mPresenter.gethbonline(CacheDataUtils.getInstance().getUserInfo().getImei(),CacheDataUtils.getInstance().getUserInfo().getGroup_id());
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
}