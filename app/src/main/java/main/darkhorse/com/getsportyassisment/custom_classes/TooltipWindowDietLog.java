//package main.darkhorse.com.getsportyassisment.custom_classes;
//
//import android.content.Context;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Handler;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Iterator;
//
////import darkhorsesports.getsporty.R;
////import darkhorsesports.getsporty.athleteprofilemodelclassess.ApiAtheliteCall;
////import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
////import darkhorsesports.getsporty.modelclasses.DietLogItems;
////import darkhorsesports.getsporty.modelclasses.DietLogResponse;
////import darkhorsesports.getsporty.modelclasses.WeekLogItems;
//import main.darkhorse.com.getsportyassisment.R;
//import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
//import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
//import main.darkhorse.com.getsportyassisment.model_classes.DietLogResponse;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//
//public class TooltipWindowDietLog {
//
//    private static final int MSG_DISMISS_TOOLTIP = 300;
//    private Context ctx;
//    private PopupWindow tipWindow;
//    private View contentView;
//    private LayoutInflater inflater;
//    private RecyclerView diet_logs;
//    String athlete_id;
//    TextView no_log;
//
//    public TooltipWindowDietLog(Context ctx, String athlete_id) {
//        this.ctx = ctx;
//        this.athlete_id = athlete_id;
//        tipWindow = new PopupWindow(ctx);
//
//        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        contentView = inflater.inflate(R.layout.tooltipwindow_diet_log, null);
//    }
//
//    public void showToolTip(View anchor) {
//
//        tipWindow.setHeight(Toolbar.LayoutParams.MATCH_PARENT);
//        tipWindow.setWidth(Toolbar.LayoutParams.MATCH_PARENT);
//
//        tipWindow.setOutsideTouchable(true);
//
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
//          handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000);
//         no_log = (TextView) contentView.findViewById(R.id.noLogLayout);
//        diet_logs = (RecyclerView) contentView.findViewById(R.id.diet_logs);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
//        diet_logs.setLayoutManager(layoutManager);
//        loadData();
//
//    }
//
//    public boolean isTooltipShown() {
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
//
//    private void loadData() {
//        final ArrayList<WeekLogItems> arrayList = new ArrayList<>();
//        Retrofit retrofit = ApiClient.getClient();
//        ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//        Call<DietLogResponse> dietLogResponseCall = apiCall.getDietLog("find_diet_log", athlete_id, "L");
//        dietLogResponseCall.enqueue(new Callback<DietLogResponse>() {
//            @Override
//            public void onResponse(Call<DietLogResponse> call, Response<DietLogResponse> response) {
//
//                Log.e("Tag", "status " + response.body().getStatus());
//
//
//                if (response.body().getStatus().equals("1")) {
//
//
////                    int j=0;
//                    WeekLogItems weekLogItems = new WeekLogItems();
//                    ArrayList<DietLogItems> dietLogItemses = new ArrayList<>();
////                    for (int i=0;i<response.body().getData().size();i++){
////
//                    String startDate = "";
//                    String previousDate = "";
////
//
//                    int previousWeek = 0;
//
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        try {
//                            DietLogItems dietLogItems = new DietLogItems();
//                            dietLogItems.setDate(response.body().getData().get(i).getAssign_date());
//                            dietLogItems.setId(response.body().getData().get(i).getId());
//                            dietLogItems.setDietFood(response.body().getData().get(i).getMy_diet_plan().getDiet_food().toString());
//                            dietLogItems.setName(response.body().getData().get(i).getMy_diet_plan().getName());
//                            JSONObject jsonObject = new JSONObject(response.body().getData().get(i).getMy_diet_plan().getDiet_food().toString());
//
//                            Iterator<?> keys = jsonObject.keys();
//                            while (keys.hasNext()) {
//                                String key = (String) keys.next();
//                                if (jsonObject.get(key) instanceof JSONObject) {
//                                    dietLogItems.setDay(key);
//                                    // do whatever you want with it
//                                }
//                            }
//
//
////                            DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" );
////                            LocalDate localDate = formatter.parseLocalDate( mParam1 );
////                            int dayOfWeek = localDate.getDayOfWeek();
//
//                            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(response.body().getData().get(i).getAssign_date());
//                            Calendar calendar = new GregorianCalendar();
//                            calendar.setTime(date1);
//                            if (previousWeek > 0) {
//                                if (calendar.get(Calendar.WEEK_OF_YEAR) < previousWeek) {
//                                    previousWeek = calendar.get(Calendar.WEEK_OF_YEAR);
//
//                                    weekLogItems.setDietLogItems(dietLogItemses);
//                                    weekLogItems.setDateRange(startDate + " - " + previousDate);
//                                    arrayList.add(weekLogItems);
//                                    weekLogItems = new WeekLogItems();
//                                    dietLogItemses.clear();
//                                    startDate = response.body().getData().get(i).getAssign_date();
//                                    dietLogItemses.add(dietLogItems);
//                                    previousDate = response.body().getData().get(i).getAssign_date();
//                                } else {
//
//                                    dietLogItemses.add(dietLogItems);
//                                    if (response.body().getData().size() == i + 1) {
//
//                                        weekLogItems.setDietLogItems(dietLogItemses);
//                                        weekLogItems.setDateRange(startDate + " - " + response.body().getData().get(i).getAssign_date());
//                                        arrayList.add(weekLogItems);
//                                    }
//                                }
//                            } else {
//                                startDate = response.body().getData().get(i).getAssign_date();
//                                previousDate = startDate;
//                                previousWeek = calendar.get(Calendar.WEEK_OF_YEAR);
//                                dietLogItemses.add(dietLogItems);
//
//                                if (response.body().getData().size() == 1) {
//                                    weekLogItems.setDietLogItems(dietLogItemses);
//                                    weekLogItems.setDateRange(startDate);
//                                    arrayList.add(weekLogItems);
//                                }
//
//                            }
//                            Log.e("Tag", "Week of year " + calendar.get(Calendar.WEEK_OF_YEAR));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                    //arrayList.add(dietLogItems);
//
//                    diet_logs.setAdapter(new DietLogAdapter(arrayList));
//                } else {
//                    no_log.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DietLogResponse> call, Throwable t) {
//                t.printStackTrace();
//                Log.e("Tag", "In failure " + t.getMessage());
//
//            }
//        });
//    }
//
//    class DietLogAdapter extends RecyclerView.Adapter<DietLogAdapter.DietViewHolder> {
//
//        private ArrayList<WeekLogItems> arrayList;
//
//        public DietLogAdapter(ArrayList<WeekLogItems> arrayList) {
//            this.arrayList = arrayList;
//        }
//
//        @Override
//        public DietViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_log_list_items, parent, false);
//            return new DietViewHolder(rootView);
//        }
//
//        @Override
//        public void onBindViewHolder(DietViewHolder holder, int position) {
//            Log.e("Tag", "position " + position);
//
//            WeekLogItems weekLogItems = arrayList.get(position);
//            holder.textViewDate.setText(weekLogItems.getDateRange());
//            holder.cardViewMonday.setVisibility(View.VISIBLE);
//            ArrayList<DietLogItems> arrayList1 = weekLogItems.getDietLogItems();
//
//            for (int i = 0; i < arrayList1.size(); i++) {
////                    holder.textViewDate.setText(arrayList.get(arrayList.size()-1).getDate()+" - "+arrayList.get(i).getDate());
//                switch (i) {
//                    case 0:
//                        holder.cardViewMonday.setVisibility(View.VISIBLE);
//                        holder.textViewMonday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanMonday.setText(arrayList1.get(i).getName());
////                            holder.textViewMonday.setText("Monday");
////                            holder.textViewDietPlanMonday.setText("Test");
//                        break;
//                    case 1:
//                        holder.textViewTuesday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanTuesday.setText(arrayList1.get(i).getName());
//                        holder.cardViewTuesday.setVisibility(View.VISIBLE);
//                        break;
//                    case 2:
//                        holder.cardViewWednesday.setVisibility(View.VISIBLE);
//                        holder.textViewWednesday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanWednesday.setText(arrayList1.get(i).getName());
//                        break;
//                    case 3:
//                        holder.cardViewThursday.setVisibility(View.VISIBLE);
//                        holder.textViewThursday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanThursday.setText(arrayList1.get(i).getName());
//                        break;
//                    case 4:
//                        holder.cardViewFriday.setVisibility(View.VISIBLE);
//                        holder.textViewFriday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanFriday.setText(arrayList1.get(i).getName());
//                        break;
//                    case 5:
//                        holder.cardViewSaturday.setVisibility(View.VISIBLE);
//                        holder.textViewSaturday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanSaturday.setText(arrayList1.get(i).getName());
//                        break;
//                    case 6:
//                        holder.cardViewSunday.setVisibility(View.VISIBLE);
//                        holder.textViewSunday.setText(arrayList1.get(i).getDay());
//                        holder.textViewDietPlanSunday.setText(arrayList1.get(i).getName());
//                        break;
//                }
//            }
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//
//            return arrayList.size();
//        }
//
//        class DietViewHolder extends RecyclerView.ViewHolder
//        {
//
//            private TextView textViewDate;
//            private CardView cardViewMonday;
//            private TextView textViewMonday;
//            private TextView textViewDietPlanMonday;
//            private CardView cardViewTuesday;
//            private TextView textViewTuesday;
//            private TextView textViewDietPlanTuesday;
//            private CardView cardViewWednesday;
//            private TextView textViewWednesday;
//            private TextView textViewDietPlanWednesday;
//            private CardView cardViewThursday;
//            private TextView textViewThursday;
//            private TextView textViewDietPlanThursday;
//            private CardView cardViewFriday;
//            private TextView textViewFriday;
//            private TextView textViewDietPlanFriday;
//            private CardView cardViewSaturday;
//            private TextView textViewSaturday;
//            private TextView textViewDietPlanSaturday;
//            private CardView cardViewSunday;
//            private TextView textViewSunday;
//            private TextView textViewDietPlanSunday;
//
//            public DietViewHolder(View itemView) {
//                super(itemView);
//
//                textViewDate = (TextView) itemView.findViewById(R.id.date);
//                cardViewMonday = (CardView) itemView.findViewById(R.id.monday_card);
//                textViewMonday = (TextView) itemView.findViewById(R.id.monday);
//                textViewDietPlanMonday = (TextView) itemView.findViewById(R.id.diet_plan_monday);
//                cardViewTuesday = (CardView) itemView.findViewById(R.id.tuesday_card);
//                textViewTuesday = (TextView) itemView.findViewById(R.id.tuesday);
//                textViewDietPlanTuesday = (TextView) itemView.findViewById(R.id.diet_plan_tuesday);
//                cardViewWednesday = (CardView) itemView.findViewById(R.id.wednesday_card);
//                textViewWednesday = (TextView) itemView.findViewById(R.id.wednesday);
//                textViewDietPlanWednesday = (TextView) itemView.findViewById(R.id.diet_plan_wednesday);
//                cardViewThursday = (CardView) itemView.findViewById(R.id.thursday_card);
//                textViewThursday = (TextView) itemView.findViewById(R.id.thursday);
//                textViewDietPlanThursday = (TextView) itemView.findViewById(R.id.diet_plan_thursday);
//                cardViewFriday = (CardView) itemView.findViewById(R.id.friday_card);
//                textViewFriday = (TextView) itemView.findViewById(R.id.friday);
//                textViewDietPlanFriday = (TextView) itemView.findViewById(R.id.diet_plan_friday);
//                cardViewSaturday = (CardView) itemView.findViewById(R.id.saturday_card);
//                textViewSaturday = (TextView) itemView.findViewById(R.id.saturday);
//                textViewDietPlanSaturday = (TextView) itemView.findViewById(R.id.diet_plan_saturday);
//                cardViewSunday = (CardView) itemView.findViewById(R.id.sunday_card);
//                textViewSunday = (TextView) itemView.findViewById(R.id.sunday);
//                textViewDietPlanSunday = (TextView) itemView.findViewById(R.id.diet_plan_sunday);
//                cardViewMonday.setVisibility(View.INVISIBLE);
//                cardViewTuesday.setVisibility(View.INVISIBLE);
//                cardViewWednesday.setVisibility(View.INVISIBLE);
//                cardViewThursday.setVisibility(View.INVISIBLE);
//                cardViewFriday.setVisibility(View.INVISIBLE);
//                cardViewSaturday.setVisibility(View.INVISIBLE);
//                cardViewSunday.setVisibility(View.INVISIBLE);
//            }
//
//
//        }
//    }
//
//}
