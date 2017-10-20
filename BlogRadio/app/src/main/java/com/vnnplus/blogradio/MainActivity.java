package com.vnnplus.blogradio;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.plus.blogradio.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.vnnplus.blogradio.adapter.SlideAdapter;
import com.vnnplus.blogradio.audioplayer.Controls;
import com.vnnplus.blogradio.audioplayer.PlayerConstants;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.base.BaseActivity;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.fragment.FragmentDownload;
import com.vnnplus.blogradio.fragment.FragmentInfo;
import com.vnnplus.blogradio.fragment.FragmentLogin;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.fragment.FragmentRegister;
import com.vnnplus.blogradio.fragment.FragmentFavorite;
import com.vnnplus.blogradio.fragment.FragmentPageinfo;
import com.vnnplus.blogradio.fragment.FragmentCateVlog;
import com.vnnplus.blogradio.fragment.FragmentHome;
import com.vnnplus.blogradio.fragment.FragmentCateRadio;
import com.vnnplus.blogradio.model.InfoUser;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Slider;
import com.vnnplus.blogradio.presenter.HomePresenter;
import com.vnnplus.blogradio.presenterIplm.HomePresenterIplm;
import com.vnnplus.blogradio.util.AppKey;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Homeview;
import com.vnnplus.blogradio.widget.ViewMenuLeft;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends BaseActivity implements View.OnClickListener, Homeview {

    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.imv_menuleft)
    FancyButton imv_menu_left;
    @Bind(R.id.ll_container)
    LinearLayout ll_container;
    @Bind(R.id.rlt_header)
    RelativeLayout header;
    @Bind(R.id.imv_Search)
    ImageView img_search;
    public static RelativeLayout rlt_Mediaplayer;
    static TextView tv_TittleBlog;
    static TextView tv_Author;
    static ImageView imvPlay;
    static CircleImageView imvRadio;


    public static ViewGroup container;
    public static FragmentManager fmm;
    SlidingMenu menu;
    HomePresenter presenter;
    SlideAdapter adapter;
    ArrayList<Slider> sliders = new ArrayList<>();
    ViewMenuLeft layoutMenu;
    Timer timer;
    int currentPage = 0;
    View element;
    int idBlog;
    public static Activity activity;

    public static RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        table = (ViewGroup) findViewById(R.id.table);
        super.onCreate(savedInstanceState);
        if (SharePreference.getDefaults("UNLOCKED", this, "").equals(""))
            SharePreference.setDefaults("UNLOCKED", "", this);
        ButterKnife.bind(this);
        activity = this;
        tv_TittleBlog = (TextView) findViewById(R.id.txtPlayingTitle);
        tv_Author = (TextView) findViewById(R.id.txtPlayingArtist);
        imvPlay = (ImageView) findViewById(R.id.imvPlay);
        container = (ViewGroup) findViewById(R.id.container);
        imvRadio = (CircleImageView) findViewById(R.id.imvRadio);
        rlt_Mediaplayer = (RelativeLayout) findViewById(R.id.rl_mediaPlayer);
        rlt_Mediaplayer.setOnClickListener(this);
        fmm = getSupportFragmentManager();
        tv_TittleBlog.setText("BLOGRADIO");
        tv_Author.setText("Unknown");
        imvPlay.setOnClickListener(this);
        tv_TittleBlog.setSelected(true);

        initViewPager();
        initMenuLeft();
        fillDataSlide();
        createPresenter();
        loadData();
        checkNetwork();
        getHashkey();
        getExtrarData();

    }

    void getHashkey() {
        ApplicationInfo ai;
        PackageManager pm = getApplicationContext().getPackageManager();
        String mypack = "";
        try {
            ai = pm.getApplicationInfo(this.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        if (ai != null)
            mypack = ai.packageName;
        AppKey.getKey(this, mypack);
    }

    private void checkNetwork() {
        try {
            boolean isConnect = Util.isNetworkOnline(MyApplication.getInstance());
            if (!isConnect) {
                com.vnnplus.blogradio.util.Dialog.dialogConfirm(this, "Thông báo", "Vui lòng kết nối mạng để có thể nghe nhạc online nhé ?", "Kết nối", "Đóng", new com.vnnplus.blogradio.util.Dialog.PopupConfirmCallback() {
                    @Override
                    public void callback(Dialog dialog, int btnId) {
                        switch (btnId) {
                            case R.id.btn2:
                                dialog.dismiss();
                                finish();
                                break;
                            case R.id.btn1:
                                boolean isConnect = Util.isNetworkOnline(MyApplication.getInstance());
                                if (isConnect) {
                                    dialog.dismiss();
                                    presenter.getMsisdn();
                                    presenter.getSlide();
                                    presenter.getMenuItem();
                                } else {
                                    Util.showToast(getBaseContext(), "không có kết nối mạng .");
                                }
                                break;
                        }
                    }
                });
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    void fillDataSlide() {
        element = LayoutInflater.from(this).inflate(R.layout.item_slide, ll_container, false);
        adapter = new SlideAdapter(sliders, this);
        final ViewPager viewPager = (ViewPager) element.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(adapter.pageSelected);
        CirclePageIndicator mIndicator = (CirclePageIndicator) element.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage >= sliders.size() - 1) {
                    viewPager.setCurrentItem(4, true);
                    currentPage = 0;
                } else
                    viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, 3000);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                adapter.pageSelected = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        ll_container.addView(element);
    }

    public void onContainerClick(View v) {

    }

    @Override
    public void getExtrarData() {
        try {
            //getlist Download
            Util.listRadio.addAll(SharePreference.getListRadio("download", getBaseContext()));
            //open deeplink
            Uri data = getIntent().getData();
            if (data != null) {
                hideLoading();
                //blogradio://video:id
                String scheme = data.toString();
                if (scheme.contains("blogradio")) {
                    String[] tag = scheme.split(":");
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("type", tag[1] + "");
                    intent.putExtra("id", Integer.parseInt(tag[2] + ""));
                    intent.setData(Uri.parse("http://xnxx.com"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        int id;
                        String type;
                        type = extras.getString("type") + "";
                        id = extras.getInt("id");
                        if (type.contains("video")) {
                            boolean isServicerunning = Util.isServiceRunning(RadioService.class.getName(), this);
                            if (isServicerunning)
                                activity.stopService(new Intent(activity, RadioService.class));
                            Intent i = new Intent(this, DetailVlogActivity.class);
                            i.putExtra("IDBLOG", id);
                            startActivity(i);
                        } else {
                            //
                            if (presenter == null)
                                presenter = new HomePresenterIplm(this, this);
                            presenter.getMsisdn();
                            presenter.getSlide();
                            presenter.getMenuItem();
                            presenter.getDetail(id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        presenter.getMsisdn();
        presenter.onOffpayment();
        presenter.getSlide();
        presenter.getMenuItem();
    }

    @Override
    public void createPresenter() {
        presenter = new HomePresenterIplm(this, this);
    }

    @Override
    public void createAdapter() {

    }

    MyPagerAdapter creatAdapter() {
        MyPagerAdapter adapter = new MyPagerAdapter() {

            @Override
            public void updateFragmentItem(int position, Fragment fragment) {
                // TODO Auto-generated method stub
            }

            @Override
            public Fragment getFragmentItem(int position) {
                // TODO Auto-generated method stub
                if (position == 0)
                    return new FragmentHome(MainActivity.this, fmm);
                else if (position == 1)
                    return new FragmentCateRadio(MainActivity.this, fmm);
                else if (position == 2)
                    return new FragmentCateVlog(MainActivity.this, fmm);
//                else if (position == 3)
//                    return new FragmentFavorite(MainActivity.this, fmm);
//                else if (position == 4)
//                    return new FragmentDownload(MainActivity.this, fmm);
                return null;
            }
        };
        return adapter;
    }

    @Override
    public void onBackPressed() {
        hideLoading();
        int numFm = fmm.getBackStackEntryCount();
        if (numFm > 0) {
            if (numFm > 1) {
                Fragment curFragment = currentFragment(fmm);
                fmm.popBackStack();
                fmm.beginTransaction().remove(curFragment).commit();
            } else {
                Fragment curFragment = currentFragment(fmm);
                fmm.popBackStack();
                if (curFragment != null)
                    fmm.beginTransaction().remove(curFragment).commit();
                container.setVisibility(View.GONE);
                if (Util.isServiceRunning(RadioService.class.getName(), activity))
                    rlt_Mediaplayer.setVisibility(View.VISIBLE);
                else
                    rlt_Mediaplayer.setVisibility(View.GONE);
                presenter.checkStatus(Util.msisdn);
                View view1 = getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
            }
        } else {
            super.onBackPressed();
        }


    }

    public static void UpdateMediaPlayer() {
        if (PlayerConstants.SONG_PAUSED) {
            imvPlay.setTag("PAUSE");
            imvPlay.setImageResource(R.drawable.bt_play);
        } else {
            imvPlay.setImageResource(R.drawable.bt_pause);
            imvPlay.setTag("PLAY");
        }
        boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(), MainActivity.activity);
        if (isServiceRunning)
            rlt_Mediaplayer.setVisibility(View.VISIBLE);
        tv_TittleBlog.setText(Util.radioService.getName());
        imvRadio.setImageBitmap(RadioService.albumArt);
        String singler = "";
        if (Util.radioService.getlSingler().size() == 1)
            singler = Util.radioService.getlSingler().get(0).getName();
        else {
            for (int i = 0; i < Util.radioService.getlSingler().size(); i++) {
                if (i == Util.radioService.getlSingler().size() - 1)
                    singler += Util.radioService.getlSingler().get(i).getName();
                else
                    singler += Util.radioService.getlSingler().get(i).getName() + ",";
            }
        }
        if (singler.equals(""))
            tv_Author.setText("Chưa cập nhật");
        else
            tv_Author.setText(singler);
    }

    void initViewPager() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setIndicatorColorResource(R.color.bg_tabtrip);
        tabs.setTextColorResource(R.color.black);
        tabs.setTextColorActiveResource(R.color.white);
        tabs.setDividerColorResource(R.color.transparent);
        tabs.setTextSize((int) getResources().getDimension(R.dimen.dp_15));
        tabs.setIndicatorHeight((int) getResources().getDimension(R.dimen.dp_42));

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        pager.setAdapter(creatAdapter());
        tabs.setViewPager(pager);
        imv_menu_left.setOnClickListener(this);
        img_search.setOnClickListener(this);

    }

    void initMenuLeft() {
        // =============MENU============= //
        menu = new SlidingMenu(MyApplication.singleton);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.dp_10);
        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                layoutMenu.updateView();
            }
        });
        menu.setBehindOffsetRes(R.dimen.dp_100);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        layoutMenu = new ViewMenuLeft(this, this);
        layoutMenu.setWillNotDraw(false);
        layoutMenu.findViewById(R.id.image_signin).setOnClickListener(this);
        layoutMenu.findViewById(R.id.rdo_doctruyen).setOnClickListener(this);
        layoutMenu.findViewById(R.id.rdo_Giaitri).setOnClickListener(this);
        layoutMenu.findViewById(R.id.rdo_tamsu).setOnClickListener(this);
        layoutMenu.findViewById(R.id.rdo_tintuc).setOnClickListener(this);
        layoutMenu.findViewById(R.id.vlog_bongmat).setOnClickListener(this);
        layoutMenu.findViewById(R.id.vlog_cafe).setOnClickListener(this);
        layoutMenu.findViewById(R.id.vlog_chiemtinh).setOnClickListener(this);
        layoutMenu.findViewById(R.id.vlog_tintuc).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_tgn).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_Taikhoan).setOnClickListener(this);
        layoutMenu.findViewById(R.id.image_signup).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_info).setOnClickListener(this);
        layoutMenu.findViewById(R.id.image_signup1).setOnClickListener(this);
        layoutMenu.findViewById(R.id.favorite).setOnClickListener(this);
        layoutMenu.findViewById(R.id.download).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_signout).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_Radio).setOnClickListener(this);
        layoutMenu.findViewById(R.id.tv_Vlog).setOnClickListener(this);


        menu.setMenu(layoutMenu);
        //
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_signin:
                if (menu.isMenuShowing())
                    menu.toggle(true);
                switchFragment(container, MainActivity.fmm, new FragmentLogin(this, getSupportFragmentManager()), false);
                rlt_Mediaplayer.setVisibility(View.GONE);
                break;
            case R.id.tv_Radio:
                menu.toggle(true);
                pager.setCurrentItem(1);
                break;
            case R.id.tv_Vlog:
                menu.toggle(true);
                pager.setCurrentItem(2);
                break;
            case R.id.rl_mediaPlayer:
                if (Util.radioService.getSigners() != null)
                    FragmentRadio.switchToPlayerSong(Util.radioService, this, Util.radioService.getSigners().getName());
                else
                    FragmentRadio.switchToPlayerSong(Util.radioService, this, "");
                break;
            case R.id.imv_Search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_signout:
                Util.msisdn = "";
                Util.lPackage.clear();
                SharePreference.setDefaults("msisdn", "", getBaseContext());
                presenter.getMsisdn();
                menu.toggle();
                Util.showToast(getBaseContext(), "Đăng xuất thành công");
                break;
            case R.id.imvPlay:
                String tag = view.getTag().toString();
                if (Util.radioService != null) {
                    if (tag != null && tag.equals("PLAY")) {
                        Controls.pauseControl(MyApplication.getInstance());
                        break;
                    } else if (tag != null && tag.equals("PAUSE")) {
                        Controls.playControl(getApplicationContext());
                    }
                }

                break;
            case R.id.tv_info:
                if (menu.isMenuShowing())
                    menu.toggle(true);
                switchFragment(container, MainActivity.fmm, new FragmentPageinfo(true), false);
                rlt_Mediaplayer.setVisibility(View.GONE);
                break;
            case R.id.favorite:
                if (Util.lPackage.size() == 0)
                    Util.showToast(getBaseContext(), getString(R.string.dangky));
                else {
                    if (menu.isMenuShowing())
                        menu.toggle(true);
                    switchFragment(container, MainActivity.fmm, new FragmentFavorite(), false);
                }
                break;
            case R.id.download:
                if (Util.lPackage.size() == 0)
                    Util.showToast(getBaseContext(), getString(R.string.dangky));
                else {
                    if (menu.isMenuShowing())
                        menu.toggle(true);
                    switchFragment(container, MainActivity.fmm, new FragmentDownload(), false);
                }

                break;
            case R.id.image_signup1:
            case R.id.image_signup:
                if (!Util.offPayment.equals("FALSE")){
                    if (menu.isMenuShowing())
                        menu.toggle(true);
                    switchFragment(container, MainActivity.fmm, new FragmentRegister(getBaseContext(), fmm), false);
                    rlt_Mediaplayer.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_tgn:
                if (menu.isMenuShowing())
                    menu.toggle(true);
                switchFragment(container, MainActivity.fmm, new FragmentPageinfo(false), false);
                rlt_Mediaplayer.setVisibility(View.GONE);
                break;
            case R.id.rdo_tamsu:
                startRadioFragment(Util.lRadio.get(0).getId(), Util.lRadio.get(0).getName(), false);
                break;
            case R.id.rdo_Giaitri:
                startRadioFragment(Util.lRadio.get(1).getId(), Util.lRadio.get(1).getName(), false);
                break;
            case R.id.rdo_tintuc:
                startRadioFragment(Util.lRadio.get(2).getId(), Util.lRadio.get(2).getName(), false);
                break;
            case R.id.rdo_doctruyen:
                startRadioFragment(Util.lRadio.get(3).getId(), Util.lRadio.get(3).getName(), false);
                break;
            case R.id.vlog_cafe:
                startRadioFragment(Util.lVlog.get(0).getId(), Util.lVlog.get(0).getName(), true);
                break;
            case R.id.vlog_tintuc:
                startRadioFragment(Util.lVlog.get(1).getId(), Util.lVlog.get(1).getName(), true);
                break;
            case R.id.vlog_chiemtinh:
                startRadioFragment(Util.lVlog.get(2).getId(), Util.lVlog.get(2).getName(), true);
                break;
            case R.id.vlog_bongmat:
                startRadioFragment(Util.lVlog.get(3).getId(), Util.lVlog.get(3).getName(), true);
                break;
            case R.id.imv_menuleft:
                menu.toggle(true);
                break;
            case R.id.tv_Taikhoan:
                if (Util.lPackage.size() > 0) {
                    if (menu.isMenuShowing())
                        menu.toggle(true);
                    switchFragment(container, MainActivity.fmm, new FragmentInfo(getBaseContext(), fmm), false);
                    rlt_Mediaplayer.setVisibility(View.GONE);
                } else {

                    Util.showToast(getBaseContext(), getString(R.string.dangky));
                }
                break;
        }
    }

    public void startRadioFragment(int id, String nameCate, boolean isVlog) {
        if (menu.isMenuShowing())
            menu.toggle(true);
        switchFragment(container, MainActivity.fmm, new FragmentRadio(id, nameCate, isVlog), false);
    }


    @Override
    public void showSlide(final ArrayList<Slider> sliderArrayList) {
        sliders.clear();
        sliders.addAll(sliderArrayList);
        adapter.setList(sliderArrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlayerConstants.CHANGE_CONNECTION = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                presenter.getMsisdn();
            }
        };
        boolean isServicerunning = Util.isServiceRunning(RadioService.class.getName(), this);
        if (isServicerunning) {
            UpdateMediaPlayer();
            if (!PlayerConstants.SONG_PAUSED)
                startAnimation();
        } else {
            rlt_Mediaplayer.setVisibility(View.GONE);
            stopAnimation();
        }
    }

    public static void startAnimation() {
        rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        imvRadio.startAnimation(rotateAnimation);
    }

    public static void stopAnimation() {
        if (rotateAnimation != null)
            rotateAnimation.cancel();
    }

    @Override
    public void showlRadio(ArrayList<Radio> lRadio) {

    }

    @Override
    public void showlVlog(ArrayList<Radio> lVlog) {

    }

    @Override
    public void getMenuItem() {
        layoutMenu.updateView();
    }

    @Override
    public void showmsisdn(String msisdn) {
        if (msisdn == null || msisdn.equals("") || msisdn.equals("null")) {
            msisdn = SharePreference.getDefaults("msisdn", getBaseContext(), "");
            Util.msisdn = SharePreference.getDefaults("msisdn", getBaseContext(), "");
        }
        SharePreference.setDefaults("msisdn", Util.msisdn, getBaseContext());
        if (msisdn != null && !msisdn.equals("")) {
            presenter.checkStatus(msisdn);
        } else
            presenter.checkStatus(msisdn);
    }

    @Override
    public void showStatus() {
        if (Util.lPackage.size() > 0) {
            presenter.getUserProfile(Util.msisdn);
        } else {
            layoutMenu.UpdateAvatar("", "", false);
        }
    }

    @Override
    public void showInfoUser(InfoUser infoUser) {
        Util.AvatarUser = infoUser.getAvatar();
        layoutMenu.UpdateAvatar(infoUser.getNickName(), infoUser.getAvatar(), true);
    }

    @Override
    public void showError() {
//        Util.showToast(getBaseContext() , "Đã xảy ra lỗi , vui lòng thử lại sau");
    }

    @Override
    public void showDetail(Radio radio) {
        FragmentRadio.switchToPlayerSong(radio, this, radio.getSigners().getName());
    }

    public static Fragment currentFragment(FragmentManager manager) {
        Fragment tag = manager.findFragmentById(R.id.container);
        return tag;
    }

    public static void switchFragment(ViewGroup viewGroup, FragmentManager manager, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = manager.beginTransaction();
        viewGroup.setVisibility(View.VISIBLE);
        //
        Fragment curFm = currentFragment(manager);
//        if (curFm!= null && curFm.getClass().getName().equals(FragmentRegister.class.getName()))
//            fmm.popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.addToBackStack(null);
        ft.replace(R.id.container, fragment, fragment.getClass().getName());
        ft.setCustomAnimations(R.anim.fly_right_to_center, R.anim.fly_center_to_left);
        ft.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    public abstract class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Trang chủ", "Radio", "Vlog"};
        private WeakHashMap<Integer, Fragment> mFragments;


        public MyPagerAdapter() {
            super(getSupportFragmentManager());
            mFragments = new WeakHashMap<Integer, Fragment>();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }


        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment item = getFragmentItem(position);
            mFragments.put(Integer.valueOf(position), item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            Integer key = Integer.valueOf(position);
            if (mFragments.containsKey(key)) {
                mFragments.remove(key);
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            for (Integer position : mFragments.keySet()) {
                // Make sure we only update fragment s that should be seen
                if (position != null && mFragments.get(position) != null && position.intValue() < getCount()) {
                    updateFragmentItem(position, mFragments.get(position));
                }
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_UNCHANGED;
        }

        protected int findFragmentPositionHashMap(Fragment object) {
            for (Integer position : mFragments.keySet()) {
                if (position != null && mFragments.get(position) != null && mFragments.get(position) == object) {
                    return position;
                }
            }

            return -1;
        }

        public abstract Fragment getFragmentItem(int position);

        public abstract void updateFragmentItem(int position, Fragment fragment);
    }

}
