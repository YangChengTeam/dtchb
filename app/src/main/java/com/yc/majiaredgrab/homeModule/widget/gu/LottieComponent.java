package com.yc.majiaredgrab.homeModule.widget.gu;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.majiaredgrab.R;


/**
 * Created by binIoter on 16/6/17.
 */
public class LottieComponent implements Component {

  @Override public View getView(LayoutInflater inflater) {

    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_lottie, null);
    ll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
                if (lottieComListen!=null){
                     lottieComListen.lottie();
                }
      }
    });
    return ll;
  }

  @Override public int getAnchor() {
    return Component.ANCHOR_TOP;
  }

  @Override public int getFitPosition() {
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
    return -30;
  }

  public  LottieComListen lottieComListen;
  public interface LottieComListen{
         void lottie();
  }
  public void setLottieComListen(LottieComListen lottieComListen){
      this.lottieComListen=lottieComListen;
  }


}
