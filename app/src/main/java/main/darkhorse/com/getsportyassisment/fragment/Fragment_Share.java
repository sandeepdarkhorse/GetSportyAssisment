package main.darkhorse.com.getsportyassisment.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import main.darkhorse.com.getsportyassisment.R;


public class Fragment_Share extends DialogFragment implements View.OnClickListener {


    static String linkToShare;
    static Uri imageuri;
    View view;
    ShareDialog shareDialog;

    public Fragment_Share() {

    }

    public static Fragment_Share newInstance(String link,Uri uri) {
        linkToShare =  "GetSporty \nA trailblazer for aspiring \nathletes and sports personal.Download the app.\n"+link;
        imageuri =uri;
        Fragment_Share fragment_share = new Fragment_Share();
        return fragment_share;
    }

    public static DialogFragment newInstance() {

        Fragment_Share fragment_share = new Fragment_Share();
        return fragment_share;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareDialog = new ShareDialog(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);


            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.dialog_alert_background)));


        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.dialog_alert_background)));
//
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        view = inflater.inflate(R.layout.fragment_share, container, false);


//        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        initViews();
        return view;
    }

    private void initViews() {

        ImageView gmailShare = (ImageView) view.findViewById(R.id.gmail_share);
        gmailShare.setOnClickListener(this);
        ImageView facebookShare = (ImageView) view.findViewById(R.id.facebook_share);
        facebookShare.setOnClickListener(this);

        ImageView whatsAppShare = (ImageView) view.findViewById(R.id.whatsapp_share);
        whatsAppShare.setOnClickListener(this);

        ImageView tweetShare = (ImageView) view.findViewById(R.id.tweet_share);
        tweetShare.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.gmail_share:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //emailIntent.putExtra(Intent.EXTRA_EMAIL, "as");
                // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ASda");

                emailIntent.putExtra(Intent.EXTRA_TEXT, linkToShare);
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageuri);
                emailIntent.setType("*/*");
                final PackageManager pma = getActivity().getPackageManager();
                final List<ResolveInfo> matches = pma.queryIntentActivities(emailIntent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                if (best != null)
                    emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                startActivity(emailIntent);
                break;

            case R.id.whatsapp_share:
                PackageManager pm = getActivity().getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);

                  //  String text = "GetSporty \nA trailblazer for aspiring \nathletes and sports personal.Download the app\nand get information on latest sports jobs ,\ntournament or events.\n"+linkToShare;

                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    waIntent.putExtra(Intent.EXTRA_TEXT, linkToShare);
                    waIntent.putExtra(Intent.EXTRA_STREAM, imageuri);
                    waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    waIntent.setType("*/*");
                    startActivity(waIntent);

                    getDialog().dismiss();
                } catch (Exception e)
                {

                    Log.e("MYAPP", "exception: " + e.getMessage());
                    Log.e("MYAPP", "exception: " + e.toString());
                    Toast.makeText(getActivity(), R.string.no_whatsapp_message, Toast.LENGTH_SHORT)
                            .show();


                }
                break;

            case R.id.facebook_share:

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(linkToShare))
                        .build();
                shareDialog.show(content);
                getDialog().dismiss();
                break;

            case R.id.tweet_share:
                Intent tweetIntent = new Intent(Intent.ACTION_SEND);
                tweetIntent.putExtra(Intent.EXTRA_TEXT, linkToShare);
                tweetIntent.putExtra(Intent.EXTRA_STREAM, imageuri);
                tweetIntent.setType("*/*");

                PackageManager packManager = getActivity().getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

                boolean resolved = false;
                for (ResolveInfo resolveInfo : resolvedInfoList) {
                    if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                        tweetIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name);
                        resolved = true;
                        break;
                    }
                }
                if (resolved) {
                    startActivity(tweetIntent);
                    getDialog().dismiss();
                } else {
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, linkToShare);
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(linkToShare)));
                    startActivity(i);
                    Toast.makeText(getActivity(), R.string.no_twitter_message, Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("nikhil", "UTF-8 should always be supported", e);
            return "";
        }
    }
}
