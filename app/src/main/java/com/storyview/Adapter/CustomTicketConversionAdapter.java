//package com.storyview.Adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//
//import com.storyview.R;
//import com.storyview.Utils.UIUtil;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by root on 8/8/17.
// */
//
//public class CustomTicketConversionAdapter extends BaseAdapter {
//    Context mContext;
//
//    LayoutInflater inflater;
//
//    String TAG = CustomTicketConversionAdapter.class.getSimpleName();
//
//    List<String> mConversationArrayList = new ArrayList<>();
//
//    public CustomTicketConversionAdapter(Context context, List<String>
//            mConversationArrayList) {
//        this.mContext = context;
//        this.mConversationArrayList = mConversationArrayList;
//    }
//
//
//    @Override
//    public int getCount() {
//        return mConversationArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mConversationArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public class Holder {
//        TextView txt_time, txt_my, txt_you, txt_time_me, txt_time_you;
//        LinearLayout ll_you, ll_me;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//
//        final Holder holder = new Holder();
//        View rowView;
//
//        if (convertView == null) {
//            inflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.row_layout_conversion, null);
//        }
//
//        holder.txt_my = (TextView) convertView.findViewById(R.id.txt_my);
//        holder.txt_you = (TextView) convertView.findViewById(R.id.txt_you);
//        holder.ll_you = (LinearLayout) convertView.findViewById(R.id.ll_you);
//        holder.ll_me = (LinearLayout) convertView.findViewById(R.id.ll_me);
//        holder.txt_time_me = (TextView) convertView.findViewById(R.id.txt_time_me);
//        holder.txt_time_you = (TextView) convertView.findViewById(R.id.txt_time_you);
//        //holder.txt_time = (TextView)
//
//        convertView.findViewById(R.id.txt_time);
//
//
//        String userId = SharedPreferenceUtil.getString("USERID", "");
//
//        if (mConversationArrayList.get(position).getTicketSenderID().equalsIgnoreCase(userId)) {
//            String Name = SharedPreferenceUtil.getString("NAME", "");
//            String Email = SharedPreferenceUtil.getString("EMAIL", "");
//            // holder.txt_name.setText(Name+"("+Email+")");
//
//
//            holder.txt_my.setText(mConversationArrayList.get(position).getTicketBody().trim());
//            holder.ll_you.setVisibility(View.GONE);
//            holder.ll_me.setVisibility(View.VISIBLE);
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String currentDateandTime = sdf.format(new Date());
//            String time = ConvertDate(mConversationArrayList.get(position).getTicketDateTime(), currentDateandTime);
//            holder.txt_time_me.setText(time);
//
//        } else {
//            //holder.txt_name.setText("Admin(admin@admin.com)");
//
//            holder.txt_you.setText(mConversationArrayList.get(position).getTicketBody().trim());
//            holder.ll_you.setVisibility(View.VISIBLE);
//            holder.ll_me.setVisibility(View.GONE);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String currentDateandTime = sdf.format(new Date());
//            String time = ConvertDate(mConversationArrayList.get(position).getTicketDateTime(), currentDateandTime);
//            holder.txt_time_you.setText(time);
//        }
//
//        return convertView;
//    }
//
//    private String ConvertDate(String cdate, String serverDateTime) {
//
//        String Newstime = "";
//
//        Log.d(TAG, "Cdata >> " + cdate);
//
//        //// Get Format
//        String fromFormat = "yyyy-MM-dd HH:mm:ss";
//
//        /// Only Date
//        String toFormat = "dd MMM yyyy";
//
//
//        /// Only Time
//        String toFormat1 = "hh:mm a";
//
//
//        Log.e("Date", "" + cdate);
//
//
//        if (cdate != null && cdate != "") {
//
//            String str_GetDate = UIUtil.convertDateTime(fromFormat, toFormat, cdate);
//
//            String str_GetTime = UIUtil.convertDateTime(fromFormat, toFormat1, cdate);
//
//
//            String str_GetServerDate = UIUtil.convertDateTime(fromFormat, toFormat, serverDateTime);
//
//            String str_GetServerTime = UIUtil.convertDateTime(fromFormat, toFormat1, serverDateTime);
//
//
//            Date date_Api = UIUtil.convertStringToDate(str_GetDate + " " + str_GetTime);
//
//
//            Date date_ServerApi = UIUtil.convertStringToDate(str_GetServerDate + " " + str_GetServerTime);
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
//            String string_Current = sdf.format(new Date());
//
//
//            if (date_Api != null && !date_Api.equals("") && date_ServerApi != null && !date_ServerApi.equals("")) {
//
//                long dfinal = date_ServerApi.getTime() - date_Api.getTime();
//
//                Log.e("Differnce", "" + dfinal);
//
//                // long milliseconds = dfinal.getTime();
//                long minuts = TimeUnit.MILLISECONDS.toMinutes(dfinal);
//
//                String time = UIUtil.convertToTrueTimeFormCustom(minuts);
//
//                if (time != null && !time.equals("")) {
//                    if (time.equals("DATE")) {
//                        //   txt_Date.setText(str_GetDate + " " + str_GetTime);
//                        Newstime = str_GetDate + " " + str_GetTime;
//                        // textViewDateTime.setText(str_GetDate + " " + str_GetTime);
//                    } else if (time.equals("yesterday")) {
//                        Newstime = time + " " + str_GetTime;
//                        //  txt_Date.setText(time + " " + str_GetTime);
//                        // textViewDateTime.setText(time + " " + str_GetTime);
//                    } else {
//                        if (minuts > 0) {
//                            Newstime = time;
//                            // txt_Date.setText(time);
//                            // textViewDateTime.setText(time);
//                        }
//
//                    }
//                }
//
//            }
//        }
//        return Newstime;
//    }
//
//
//}
