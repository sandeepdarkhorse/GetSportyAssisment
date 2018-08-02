package main.darkhorse.com.getsportyassisment.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;


import fr.ganfra.materialspinner.MaterialSpinner;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.CommonUtils;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.activity.ActivityDashboardDetail;
import main.darkhorse.com.getsportyassisment.activity.ActivityInstituteDetail;
import main.darkhorse.com.getsportyassisment.activity.ActivityLoginAdmin;
import main.darkhorse.com.getsportyassisment.activity.ActivityVideoLink;
import main.darkhorse.com.getsportyassisment.activity.MainActivity;
import main.darkhorse.com.getsportyassisment.activity.UserProfile;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.compressor.Compressor;
import main.darkhorse.com.getsportyassisment.compressor.FileUtil;
import main.darkhorse.com.getsportyassisment.cropper.CropImage;
import main.darkhorse.com.getsportyassisment.cropper.CropImageView;
import main.darkhorse.com.getsportyassisment.custom_classes.AutoFitGridLayoutManager;
import main.darkhorse.com.getsportyassisment.custom_classes.AutoFitGridRecyclerView;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentModle;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentResponse;
import main.darkhorse.com.getsportyassisment.model_classes.GooglePlaceApiResponse;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojo;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojoApi;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteResponse;
import main.darkhorse.com.getsportyassisment.model_classes.PlacesSportsdetail;
import main.darkhorse.com.getsportyassisment.model_classes.Predictions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


public class FragmentInstList extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public FragmentInstList newInstance(String param1, String param2) {
        FragmentInstList fragment = new FragmentInstList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private int[] validation = new int[8];
    View rootView;
    AutoFitGridRecyclerView recycleview_eventListing;
    RecyclerView.LayoutManager myLayoutManager;

    CustomProgress customProgress;
    FloatingActionButton add_institute;
    private NetworkStatus network_status;
    Boolean imageuploadgalary = false;
    Uri imagefromgalary;
    private File actualImage;
    private File compressedImage;
    String encoded = "sdlksjlksjl";

    private String[] typeArray = {"School", "Institution", "Club", "Academy"};


    Button button_submit;
    MaterialSpinner inst_type;

    ScrollView scrollview;

    TextInputLayout tl_name;
    TextInputLayout layout_tl_email;
    TextInputLayout layout_tl_mobile_no;
    TextInputLayout layout_tl_institute_regno;
    TextInputLayout layout_tl_institute_address;


    TextInputEditText  editText_name;
    TextInputEditText editText_institute_address;
    TextInputEditText editText_institute_regno, editText_institute_mobileno;
    TextInputEditText editText_institute_email;
    TextView textimage;

    ArrayList<InstituteDataPojoApi> arrylistinstitute;

    TextView filename;
    ImageView tick;
    ArrayAdapter<String> adapter;
    String browserKey = "AIzaSyDfdIdeA96qORreYWTCGto85nz0_ZSx_dc";
    String url;
    AutoCompleteTextView adress_location;
    ArrayList<String> names;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_per_assist, container, false);


        recycleview_eventListing = (AutoFitGridRecyclerView) rootView.findViewById(R.id.recyclerview_assist);

//        myLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recycleview_eventListing.setLayoutManager(myLayoutManager);

//        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
//        recycleview_eventListing.setLayoutManager(mLayoutManager);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.recycleview_space);
//        recycleview_eventListing.addItemDecoration(itemDecoration);


        customProgress = CustomProgress.getInstance();
        add_institute = (FloatingActionButton) rootView.findViewById(R.id.add_institute);
        add_institute.setVisibility(View.VISIBLE);

        add_institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiag();
            }
        });
        network_status = new NetworkStatus(getActivity());

        Retrofit_listdata();
        return rootView;

    }

    private void showDiag() {

        final View dialogView = View.inflate(getActivity(), R.layout.add_institute_dialog, null);

//        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(dialogView);

        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(dialogView);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        Toolbar toolbar_dissmiss = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialogView, false, dialog);
            }
        });


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        scrollview = (ScrollView) dialog.findViewById(R.id.scrollview);
        inst_type = (MaterialSpinner) dialog.findViewById(R.id.inst_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        inst_type.setAdapter(adapter);


        tl_name = (TextInputLayout) dialog.findViewById(R.id.tl_name);
        editText_name = (TextInputEditText) dialog.findViewById(R.id.name);


        layout_tl_email = (TextInputLayout) dialog.findViewById(R.id.tl_email);
        editText_institute_email = (TextInputEditText) dialog.findViewById(R.id.institute_email);


        layout_tl_mobile_no = (TextInputLayout) dialog.findViewById(R.id.tl_mobile_no);
        editText_institute_mobileno = (TextInputEditText) dialog.findViewById(R.id.institute_mobile_no);


        layout_tl_institute_regno = (TextInputLayout) dialog.findViewById(R.id.tl_institute_regno);
        editText_institute_regno = (TextInputEditText) dialog.findViewById(R.id.institute_regno);


        layout_tl_institute_address = (TextInputLayout) dialog.findViewById(R.id.tl_institute_address);
        editText_institute_address = (TextInputEditText) dialog.findViewById(R.id.institute_address);

        textimage = (TextView) dialog.findViewById(R.id.text_image);
        button_submit = (Button) dialog.findViewById(R.id.submit_details);


        filename = (TextView) dialog.findViewById(R.id.file_name);
        tick = (ImageView) dialog.findViewById(R.id.tick);

        TextView addimage = (TextView) dialog.findViewById(R.id.addimage);

        adress_location = (AutoCompleteTextView) dialog.findViewById(R.id.institute_location);
        adress_location.setThreshold(0);
        names = new ArrayList<String>();
        adress_location.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().length() <= 3) {
                    names = new ArrayList<String>();
                    updateList(s.toString());
                }

            }
        });



        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity())) {
                    onSelectImageClick();
                } else {
                    CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity());
                }


            }
        });


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {


                String name = editText_name.getText().toString();
                String instituteemail = editText_institute_email.getText().toString();
                String institutemobileno = editText_institute_mobileno.getText().toString();
                String institutelocation = adress_location.getText().toString();
                String instituteaddress = editText_institute_address.getText().toString();
                String instituteregno = editText_institute_regno.getText().toString();
                String insttype = inst_type.getSelectedItem().toString();
                if (name.equals("")) {
                    validation[0] = 1;
                    tl_name.setErrorEnabled(true);
                    tl_name.setError("You need to enter a name");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[0] = 0;

                    tl_name.setError(null);
                }

                if (instituteemail.equals("")) {
                    validation[1] = 1;
                    layout_tl_email.setErrorEnabled(true);
                    layout_tl_email.setError("You need to enter a email");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[1] = 0;

                    layout_tl_email.setError(null);
                }
                if (institutemobileno.equals("")) {
                    validation[2] = 1;
                    layout_tl_mobile_no.setErrorEnabled(true);
                    layout_tl_mobile_no.setError("You need to enter a number");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[2] = 0;
                    layout_tl_mobile_no.setError(null);
                }
                if (institutelocation.equals("")) {
                    validation[3] = 1;

                    adress_location.setError("You need to enter a location");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);

                } else {
                    validation[3] = 0;
                    adress_location.setError(null);
                }
                if (instituteaddress.equals("")) {
                    validation[4] = 1;
                    layout_tl_institute_address.setError("You need to enter a address");
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);

                } else {
                    validation[4] = 0;
                    layout_tl_institute_address.setError(null);
                }

                if (instituteregno.equals("")) {

                    validation[5] = 1;
                    layout_tl_institute_regno.setError("You need to enter a registration no");
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);

                } else {
                    validation[5] = 0;

                    layout_tl_institute_regno.setError(null);
                }
                if (insttype.equals("Type")) {
                    validation[6] = 1;
                    inst_type.setError("You need to enter institute type");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else if (insttype.equals("")) {
                    validation[6] = 1;
                    inst_type.setError("You need to enter institute type");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[6] = 0;

                    inst_type.setError(null);
                }

                if (encoded.equals("")) {
                    validation[7] = 1;
                    textimage.setError("");
                    textimage.setTextColor(getResources().getColor(R.color.red));
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[7] = 0;
                    textimage.setError(null);
                }


                int sum = validation[0] + validation[1] + validation[2] + validation[3] + validation[4] + validation[5] + validation[6] + validation[7];
                if (sum == 0) {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (instituteemail.trim().matches(emailPattern)) {
                        layout_tl_email.setError(null);

                        try {

                            if (network_status.isConnectingToInternet()) {

                                try {
                                    Retrofit retrofit = ApiClient.getClient();
                                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);

                                    InstituteDataPojo datapojo = new InstituteDataPojo(insttype, name, instituteemail
                                            , institutemobileno, instituteregno, instituteaddress, institutelocation, encoded);


                                    Call<JsonElement> checklogin = apiCall.Addinstitute("add_institute", datapojo);
                                    Log.e("institute listing url:", checklogin.request().url().toString());

                                    customProgress.showProgress(getActivity(), false);
                                    checklogin.enqueue(new Callback<JsonElement>() {
                                        @Override
                                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                            customProgress.hideProgress();
                                            JsonElement jsonElement = response.body();
                                            Log.e("JSON RESPONSE", jsonElement.toString());
                                            try {
                                                JSONObject jsonobj = new JSONObject(jsonElement.toString());
                                                String status = jsonobj.getString("status");
                                                if (status.equals("1")) {

                                                    revealShow(dialogView, false, dialog);
                                                    Retrofit_listdata();

                                                } else {

                                                    Toast.makeText(getActivity(), (getString(R.string.server_error_text)), Toast.LENGTH_LONG).show();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();

                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<JsonElement> call, Throwable t) {
                                            customProgress.hideProgress();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    customProgress.hideProgress();
                                }


                            } else {
                                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        layout_tl_email.setErrorEnabled(true);
                        layout_tl_email.setError("Invalid email address");

                    }


                } else {
                    Toast.makeText(getActivity(), "Please enter all fields.", Toast.LENGTH_SHORT).show();
                }


            }


        });

        dialog.show();
    }


    @SuppressLint("NewApi")
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (add_institute.getX() + (add_institute.getWidth() / 2));
        int cy = (int) (add_institute.getY()) + add_institute.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }

    public void Retrofit_listdata() {
        arrylistinstitute = new ArrayList<>();
        arrylistinstitute.clear();

        final int network = CommonUtils.getConnectivityStatus(getActivity());

        if (network_status.isConnectingToInternet()) {
            try {
                String performanceUrl = "http://testingapp.getsporty.in/add_property_controller.php?";
                SharedPreferences sharedPreferences_user = getActivity().getSharedPreferences("LoginCredentials", 0);
                String user_id = sharedPreferences_user.getString("userid", "");
                String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("institute_list", "UTF-8");
                Retrofit retrofit = ApiClient.getClient();
                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
                Call<InstituteResponse> checklogin = apiCall.getinstitutelist(performanceUrl + data);
                Log.d("Resourse listing url:::", checklogin.request().url().toString());
                customProgress.showProgress(getActivity(), false);

                checklogin.enqueue(new Callback<InstituteResponse>() {
                    @Override
                    public void onResponse(Call<InstituteResponse> call, Response<InstituteResponse> response) {
                        customProgress.hideProgress();
                        arrylistinstitute = response.body().getData();
                        InstituteListingAdapter adapter = new InstituteListingAdapter(arrylistinstitute);
                        recycleview_eventListing.setAdapter(adapter);


                    }

                    @Override
                    public void onFailure(Call<InstituteResponse> call, Throwable t) {
                        customProgress.hideProgress();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection_error, Toast.LENGTH_LONG).show();

        }


    }

    @SuppressLint("NewApi")
    public class InstituteListingAdapter extends RecyclerView.Adapter<InstituteListingAdapter.ViewHolder> {


        private ArrayList<InstituteDataPojoApi> DataItems = new ArrayList<InstituteDataPojoApi>();
        private View rootview;

        InstituteListingAdapter(ArrayList<InstituteDataPojoApi> DietDataItems) {
            this.DataItems = DietDataItems;

        }

        @Override
        public InstituteListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.institute_detail_item, parent, false);


            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(InstituteListingAdapter.ViewHolder holder, int position) {

            holder.setItem(DataItems.get(position));
//            holder.setItem(DietDataItems.get(position).getMy_diet_plan());
        }

        @Override
        public int getItemCount() {
            return DataItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            private ImageView athleteimage;
            private TextView ageTextview;


            private Button assistment;

            private TextView name;
            private TextView address;
            private TextView location;

            public ViewHolder(final View itemView) {
                super(itemView);
                athleteimage = (ImageView) itemView.findViewById(R.id.athlete_image);

                name = (TextView) itemView.findViewById(R.id.name);
                address = (TextView) itemView.findViewById(R.id.address);

                ageTextview = (TextView) itemView.findViewById(R.id.age);
                location = (TextView) itemView.findViewById(R.id.location);


                assistment = (Button) itemView.findViewById(R.id.assist);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();

                        InstituteDataPojoApi institutedata = DataItems.get(position);
                        Bundle userinfo = new Bundle();
                        userinfo.putSerializable("institutedetail", (Serializable) institutedata);
                        Intent i = new Intent(new Intent(getActivity(), ActivityInstituteDetail.class));
                        i.putExtras(userinfo);

                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(getActivity(),
                                        Pair.create(itemView.findViewById(R.id.athlete_image), "image_transition"),
                                        Pair.create(itemView.findViewById(R.id.name), "name_transition"),
                                        Pair.create(itemView.findViewById(R.id.location), "location_transition"));

                        startActivity(i, options.toBundle());


                    }
                });

            }

            public void setItem(InstituteDataPojoApi DataItem) {
                name.setText(DataItem.getCollege_name());
                address.setText(DataItem.getAddress());
                location.setText(DataItem.getLocation());

                //  String imageurl = DataItem.getUser_image();

//                try {
//                    Date date = DateConversion.StringtoDate(DataItem.getDob());
//                    String age = String.valueOf(DateConversion.calculateAge(date));
//                    ageTextview.setText(age + " Year");
//                } catch (Exception e) {
//                }


//                if (imageurl.isEmpty()) {
//                    athleteimage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.resource_back));
//
//                } else {
//                    Picasso.with(getActivity())
//                            .load(DataItem.getUser_image())
//                            .error(R.drawable.resource_back)
//                            .into(athleteimage);
//                }


            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                imageuploadgalary = true;
                imagefromgalary = result.getUri();


                filename.setText(imagefromgalary.toString());
                tick.setVisibility(View.VISIBLE);


                Log.e("Image path", imagefromgalary.toString());
                try {
                    actualImage = FileUtil.from(getContext(), imagefromgalary);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    compressedImage = new Compressor(getActivity())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(actualImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bm;
                try {
                    bm = (BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onSelectImageClick() {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getContext(), this);
    }


    public void updateList(String place) {
        String input = "";
        try {
            input = "input=" + URLEncoder.encode(place, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String output = "json";
        String parameter = input + "&types=geocode&sensor=true&key="
                + browserKey;
        url = output + "?" + parameter;
        Retrofit retrofit = ApiClient.getGooglePlaceClient();
        ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
        Call<GooglePlaceApiResponse> jsonObjReq = apiCall.GoogleApiCall(url);
        Log.e("well", jsonObjReq.request().url().toString());
        jsonObjReq.enqueue(new Callback<GooglePlaceApiResponse>() {
            @Override
            public void onResponse(Call<GooglePlaceApiResponse> call, Response<GooglePlaceApiResponse> response) {

                ArrayList<Predictions> predictions = new ArrayList<Predictions>();
                predictions = response.body().getPredictions();

                try {
                    for (int i = 0; i < predictions.size(); i++) {
                        String description = predictions.get(i).getDescription();
                        Log.d("description", description);
                        names.add(description);
                    }
                } catch (Exception e) {
                }

                adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1, names) {
                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        View view = super.getView(position,
                                convertView, parent);
                        TextView text = (TextView) view
                                .findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                adress_location.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<GooglePlaceApiResponse> call, Throwable t) {


            }
        });


    }

}
