package com.vnnplus.blogradio.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.GenderAdapter;
import com.vnnplus.blogradio.adapter.ListAvatarAdapter;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.InfoUser;
import com.vnnplus.blogradio.presenter.UserPresenter;
import com.vnnplus.blogradio.presenterIplm.UserPresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.UserInfoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by Giangdv on 3/27/2017.
 */

public class FragmentInfo extends BaseFragment implements UserInfoView,GenderAdapter.IMethodCaller {
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.img_Avatar)
    ImageView avatar;
    @Bind(R.id.tv_Name)
    TextView tv_Name;
    @Bind(R.id.img_Edit)
    ImageView img_Edit;
    @Bind(R.id.bgr_candle)
    ImageView img_candle;
    @Bind(R.id.tv_Edit)
    TextView tv_Edit;
    @Bind(R.id.rlt_Edit)
    RelativeLayout Edit;
    @Bind(R.id.tv_Save)
    TextView Save;
    @Bind(R.id.tv_Cancel)
    TextView Cancel;
    @Bind(R.id.edt_Name)
            EditText edt_Name;
    @Bind(R.id.tv_Nameuser)
            TextView tv_Nameuser;
    @Bind(R.id.tv_Ngaysinh)
            TextView tv_Ngaysinh;
    @Bind(R.id.edt_Ngaysinh)
            EditText edt_Ngaysinh;
    @Bind(R.id.tv_Sdt)
            TextView tv_Sdt;
    @Bind(R.id.tv_Mail)
            TextView tv_Mail;
    @Bind(R.id.edt_Mail)
            EditText edt_Mail;
    @Bind(R.id.rltChangegender)
    RelativeLayout chanGender;
    @Bind(R.id.rlt_goicuoc)
    RelativeLayout rlt_goicuoc;
    @Bind(R.id.tv_goicuoc)
    TextView tvGoicuoc;

    @Bind(R.id.tv_gender)
    TextView tv_gender;
    @Bind(R.id.rlt_header)
    RelativeLayout header;

    int gender;
    Calendar myCalendar;
    ArrayList<String> lAvatar = new ArrayList<>();
    public static int posAvatar = 1;
    public static String urlImage = "";
    PopupWindow popupWindow;



    UserPresenter presenter;

    InfoUser infoUser = new InfoUser();

    public FragmentInfo(Context context, FragmentManager ft) {
        this.fmm = ft;
    }
    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        edt_Name.setNextFocusForwardId(R.id.edt_Mail);
        tv_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_Edit.setVisibility(View.GONE);
                Edit.setVisibility(View.VISIBLE);
                chanGender.setVisibility(View.VISIBLE);
                visibility(true);
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }

                if (edt_Name.getText().toString().length()<5)
                    showToastMs("Tên phải có từ 5 ký tự trở lên");
                else if (edt_Mail.getText().toString().equals("Chưa cập nhật")|| isEmailValid(edt_Mail.getText().toString())){
                    tv_Mail.setText("Chưa cập nhật");
                    tv_Edit.setVisibility(View.VISIBLE);
                    Edit.setVisibility(View.GONE);
                    chanGender.setVisibility(View.GONE);
                    if (Util.isTest)
                        presenter.updateProfile("01658708738",edt_Name.getText().toString() , edt_Mail.getText().toString(),"",edt_Ngaysinh.getText().toString(),gender);
                    else
                        presenter.updateProfile(Util.msisdn,edt_Name.getText().toString() , edt_Mail.getText().toString(),"",edt_Ngaysinh.getText().toString(),gender);

                    visibility(false);
                }
                else if (!isEmailValid(edt_Mail.getText().toString()))
                    Util.showToast(getContext(),"Email không hợp lệ , Vui lòng nhập lại");

            }
        });
        chanGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                showpopupGender();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                tv_Edit.setVisibility(View.VISIBLE);
                Edit.setVisibility(View.GONE);
                chanGender.setVisibility(View.GONE);
                if (Util.isTest)
                    presenter.getUserProfile("01658708738");
                else
                    presenter.getUserProfile(Util.msisdn);
            }
        });
        img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListAvarar(lAvatar);
            }
        });
        rlt_goicuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Util.offPayment.equals("FALSE")){
                    switchFragment(new FragmentRegister(getContext(),fmm));
                }

            }
        });
        createDate();
    }

    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void configRecyclerview() {

    }

    @Override
    public void initPresenter() {
        presenter = new UserPresenterIplm(this , getActivity());
    }

    @Override
    public void loadData() {
        if (Util.isTest)
            presenter.getUserProfile("01658708738");
        else
            presenter.getUserProfile(Util.msisdn);
        presenter.getAllAvatar();
    }


    @Override
    public void showInfoUser(InfoUser infoUser) {
        this.infoUser = infoUser;
        tv_Sdt.setText(infoUser.getMsisdn());
        tv_Ngaysinh.setText(infoUser.getDate());
        tv_Name.setText(infoUser.getNickName());
        tv_Nameuser.setText(infoUser.getNickName());
        tv_Mail.setText(infoUser.getEmail());

//        edt_Sdt.setText(infoUser.getMsisdn());
        edt_Ngaysinh.setText(infoUser.getDate());
        edt_Name.setText(infoUser.getNickName());
        if (infoUser.getEmail().equals(""))
            edt_Mail.setText("Chưa cập nhật");
        else
            edt_Mail.setText(infoUser.getEmail());
        if (infoUser.getGenDer() == 1)
            tv_gender.setText("Nam");
        else if (infoUser.getGenDer() == 2)
            tv_gender.setText("Nữ");
        else
            tv_gender.setText("Không Xác Định");

        visibility(false);
        getGoicuoc();
        Util.setImage(img_candle , getActivity() , infoUser.getAvatar());
        Util.setImage(avatar , getActivity() , infoUser.getAvatar());
    }
    void getGoicuoc(){
        String goicuoc = "";
        if (Util.lPackage.size() == 1)
            goicuoc = Util.lPackage.get(0);
        else {
            for (int i = 0; i<Util.lPackage.size();i++){
                if (i < Util.lPackage.size() -1)
                    goicuoc += Util.lPackage.get(i)+" , ";
                else
                    goicuoc+= Util.lPackage.get(i);
            }
        }
        tvGoicuoc.setText(goicuoc);

    }

    @Override
    public void ListAvatar(ArrayList<String> lAvatar) {
        this.lAvatar = lAvatar;
    }

    void visibility(boolean edt){
        if (edt){
            edt_Mail.setVisibility(View.VISIBLE);
            edt_Name.setVisibility(View.VISIBLE);
            edt_Ngaysinh.setVisibility(View.VISIBLE);
//            edt_Sdt.setVisibility(View.VISIBLE);
            tv_Nameuser.setVisibility(View.GONE);
            tv_Mail.setVisibility(View.GONE);
//            tv_Sdt.setVisibility(View.GONE);
            tv_Ngaysinh.setVisibility(View.GONE);
//            tv_gender.setVisibility(View.GONE);
        }
        else {
            edt_Mail.setVisibility(View.GONE);
            edt_Name.setVisibility(View.GONE);
            edt_Ngaysinh.setVisibility(View.GONE);
//            edt_Sdt.setVisibility(View.GONE);
            tv_Nameuser.setVisibility(View.VISIBLE);
            tv_Mail.setVisibility(View.VISIBLE);
//            tv_Sdt.setVisibility(View.VISIBLE);
            tv_Ngaysinh.setVisibility(View.VISIBLE);
            tv_gender.setVisibility(View.VISIBLE);

            tv_Name.setText(edt_Name.getText().toString());
            tv_Nameuser.setText(edt_Name.getText().toString());
            tv_Ngaysinh.setText(edt_Ngaysinh.getText().toString());
            tv_Mail.setText(edt_Mail.getText().toString());
//            tv_Sdt.setText(edt_Sdt.getText().toString());

        }
    }
    void createDate(){
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        edt_Ngaysinh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });
    }
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_Ngaysinh.setText(sdf.format(myCalendar.getTime()));
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "[a-zA-Z0-9._-]+@[a-z]+(\\.+[a-z]+)+";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private  void showListAvarar(final ArrayList<String> lAvatar){
        final Dialog mDialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setContentView(R.layout.list_avatar);
        RecyclerView recyclerView;
        TextView btn_ok;
        ListAvatarAdapter adapter;
        recyclerView = (RecyclerView) mDialog.findViewById(R.id.recycleview);
        btn_ok = (TextView) mDialog.findViewById(R.id.btn_ok);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new ListAvatarAdapter(lAvatar , getActivity());
        recyclerView.setAdapter(adapter);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_Name.getText().toString().length()<5)
                    showToastMs("Tên phải có từ 5 ký tự trở lên");
                else if (edt_Mail.getText().toString().equals("Chưa cập nhật")|| isEmailValid(edt_Mail.getText().toString())){
                    tv_Mail.setText("Chưa cập nhật");
                    tv_Edit.setVisibility(View.VISIBLE);
                    Edit.setVisibility(View.GONE);
                    chanGender.setVisibility(View.GONE);
                    if (urlImage.equals(""))
                        urlImage = lAvatar.get(0);
                    Util.setImage(avatar , getActivity() , urlImage);
                    Util.setImage(img_candle , getActivity() , urlImage);
                    mDialog.cancel();
                    if (Util.isTest)
                        presenter.updateProfile("01658708738",edt_Name.getText().toString() , edt_Mail.getText().toString(), String.valueOf(posAvatar),edt_Ngaysinh.getText().toString(),gender);
                    else
                        presenter.updateProfile(Util.msisdn,edt_Name.getText().toString() , edt_Mail.getText().toString(), String.valueOf(posAvatar),edt_Ngaysinh.getText().toString(),gender);

                    visibility(false);
                }
                else if (!isEmailValid(edt_Mail.getText().toString()))
                    Util.showToast(getContext(),"Email không hợp lệ , Vui lòng nhập lại");



            }
        });
        mDialog.show();
    }
    ArrayList<String> getlist(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Nam");
        list.add("Nữ");
        list.add("Không Xác Định");
        return list;
    }
    private void showpopupGender(){
        ListView lv = new ListView(getContext());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        lv.setLayoutParams(param);
        lv.setBackgroundResource(android.R.drawable.alert_light_frame);

        lv.setAdapter(new GenderAdapter(getContext(),getlist(),this));
        popupWindow = new PopupWindow(lv, (int) getResources().getDimension(R.dimen.dp_200), RelativeLayout.LayoutParams.WRAP_CONTENT);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(tv_gender);
//        popupWindow.showAsDropDown(tv_gender , 10 ,10 , Gravity.BOTTOM);

    }


    @Override
    public void yourDesiredMethod(String gender, int pos) {
        popupWindow.dismiss();
        tv_gender.setText(gender);
        this.gender = pos ;
    }
}
