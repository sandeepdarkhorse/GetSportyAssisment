package main.darkhorse.com.getsportyassisment.custom_classes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import main.darkhorse.com.getsportyassisment.R;


public class TooltipWindow {

//    private static final int MSG_DISMISS_TOOLTIP = 300;
//    private Context ctx;
//    private PopupWindow tipWindow;
//    private View contentView;
//    private LayoutInflater inflater;
//
//    public TooltipWindow(Context ctx) {
//        this.ctx = ctx;
//        tipWindow = new PopupWindow(ctx);
//
//        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        contentView = inflater.inflate(R.layout.create_class_gst_detail, null);
//    }
//
//   public void showToolTip(View anchor) {
//
//        tipWindow.setHeight(Toolbar.LayoutParams.WRAP_CONTENT);
//        tipWindow.setWidth(Toolbar.LayoutParams.WRAP_CONTENT);
//
//        tipWindow.setOutsideTouchable(true);
//        tipWindow.setTouchable(true);
//        tipWindow.setFocusable(true);
//        tipWindow.setBackgroundDrawable(new BitmapDrawable());
//
//        tipWindow.setContentView(contentView);
//
//        int screen_pos[] = new int[2];
//        // Get location of anchor view on screen
//        anchor.getLocationOnScreen(screen_pos);
//
//        // Get rect for anchor view
//        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
//                + anchor.getWidth(), screen_pos[1] + anchor.getHeight());
//
//        // Call view measure to calculate how big your view should be.
//        contentView.measure(Toolbar.LayoutParams.WRAP_CONTENT,
//                Toolbar.LayoutParams.WRAP_CONTENT);
//
//        int contentViewHeight = contentView.getMeasuredHeight();
//        int contentViewWidth = contentView.getMeasuredWidth();
//        // In this case , i dont need much calculation for x and y position of
//        // tooltip
//        // For cases if anchor is near screen border, you need to take care of
//        // direction as well
//        // to show left, right, above or below of anchor view
//        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
//        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);
//
//        tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x, position_y);
//
//        // send message to handler to dismiss tipWindow after X milliseconds
//        handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000);
//
//       final TextView tv_weblink = (TextView) contentView.findViewById(R.id.weblink);
//
//       tv_weblink.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gstindia.com/goods-and-service-tax-a-detailed-explanation-with-examples-2/"));
//               ctx.startActivity(browserIntent);
//           }
//       });
//
//
//
//    }
//
// public   boolean isTooltipShown() {
//        if (tipWindow != null && tipWindow.isShowing())
//            return true;
//        return false;
//    }
//
//    void dismissTooltip() {
//        if (tipWindow != null && tipWindow.isShowing())
//            tipWindow.dismiss();
//    }
//
//    Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case MSG_DISMISS_TOOLTIP:
//                    if (tipWindow != null && tipWindow.isShowing())
//                        tipWindow.dismiss();
//                    break;
//            }
//        }
//
//        ;
//    };

}