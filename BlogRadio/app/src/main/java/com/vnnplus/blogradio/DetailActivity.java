package com.vnnplus.blogradio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.SameAdapter;
import com.vnnplus.blogradio.audioplayer.Controls;
import com.vnnplus.blogradio.audioplayer.PlayerConstants;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.base.BaseActivity;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.fragment.FragmentSameAuthor;
import com.vnnplus.blogradio.fragment.FragmentSameType;
import com.vnnplus.blogradio.fragment.FragmentCmt;
import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.DetailPresenter;
import com.vnnplus.blogradio.presenterIplm.DetailPresenterIplm;
import com.vnnplus.blogradio.util.Dialog;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Detailview;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Giangdv on 3/17/2017.
 */

public class DetailActivity extends BaseActivity implements Detailview,SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    Radio radio;
    @Bind(R.id.tv_Tittle)
    TextView tv_Tittle;
    @Bind(R.id.tv_author)
    TextView tv_author;
    @Bind(R.id.tv_singler)
    TextView tv_singler;
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.tv_view)
    TextView tv_view;
    @Bind(R.id.wv_content)
    WebView webView;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.tv_time)
    TextView tv_Duracation;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.tv_category)
    TextView category;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.img_favorite)
    ImageView img_Favorite;
    @Bind(R.id.imv_repead)
    ImageView img_repead;
    @Bind(R.id.img_download)
    ImageView imv_download;
    @Bind(R.id.img_volume)
    ImageView volume;
    @Bind(R.id.header)
    RelativeLayout header;
    static ImageView imgBtnPlay;
    @Bind(R.id.scrollview)
    NestedScrollView scrollView;
    @Bind(R.id.img_share)
    ImageView share;
    @Bind(R.id.ll_top)
    LinearLayout ll_top;
    @Bind(R.id.imv_Search)
    ImageView imv_search;
    @Bind(R.id.rv_favorite)
    RippleView rvFavorite;
    @Bind(R.id.rv_repead)
    RippleView rvRepead;
    @Bind(R.id.rv_share)
    RippleView rvShare;
    @Bind(R.id.rv_volume)
    RippleView rvVolume;
    @Bind(R.id.rv_download)
    RippleView rvDownload;

    ViewGroup viewGroup;

    @Bind(R.id.containe)
    LinearLayout container;

    MypagerAdapter adapter;

    int idBlog;
    String nameCate = "";
    int timeListent = 0;

    DetailPresenter presenter;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_detail);
        table = (ViewGroup) findViewById(R.id.table);
        Util.tracking = false;
        Util.isShow = true;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        viewGroup = (ViewGroup) findViewById(R.id.container);
        activity = this;
        getExtrarData();
        init();
        createPresenter();
        createAdapter();
        loadData();
    }

    @Override
    public void getExtrarData() {
        showLoading();
        transitionActivityAnimation(true);
        if (PlayerConstants.reMode == PlayerConstants.REPEAT_MODE.OFF){
            img_repead.setTag(0);
            img_repead.setImageResource(R.drawable.bt_un_repeat);
        }
        else {
            img_repead.setTag(1);
            img_repead.setImageResource(R.drawable.bt_repeat);
        }

        //schema
        try{
            Intent intent = getIntent();
            Uri data = intent.getData();
            idBlog = intent.getIntExtra("IDBLOG",0);
            nameCate = intent.getStringExtra("NAMEBLOG");
            String target = intent.getStringExtra("target");
            if (target!=null && !target.equals("")){
                if (idBlog == 0)
                    idBlog = Integer.parseInt(target);
                if (Util.msisdn.equals("")){
                    Util.msisdn = SharePreference.getDefaults("msisdn",getBaseContext(),"");
                }
                if (presenter == null)
                    presenter = new DetailPresenterIplm(this , this);
                presenter.getDetail(Integer.parseInt(target));
                if (nameCate == null)
                    nameCate = intent.getStringExtra("nameCate");

            }
        }catch (Exception e){

        }
    }



    @Override
    public void loadData() {
        presenter.getDetail(idBlog);
        presenter.checkFavorite(idBlog , Util.msisdn);
    }

    @Override
    public void createPresenter() {
        presenter = new DetailPresenterIplm(this , this);
    }

    @Override
    public void createAdapter() {

    }
    void init(){
        imgBtnPlay = (ImageView) findViewById(R.id.imgBtnPlay);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(100);
        category.setText(nameCate.toUpperCase());
        imv_search.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        if (PlayerConstants.SONG_PAUSED)
            imgBtnPlay.setImageResource(R.drawable.bt_play);
        else
            imgBtnPlay.setImageResource(R.drawable.bt_pause);
        imgBtnPlay.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rvDownload.setOnClickListener(this);
        rvFavorite.setOnClickListener(this);
        rvRepead.setOnClickListener(this);
        rvVolume.setOnClickListener(this);

        rvShare.setOnClickListener(this);
        share.setOnClickListener(this);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,  (Util.getHeightScreen(this)*3/4));
        container.setLayoutParams(layoutParams1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        PlayerConstants.PROGRESSBAR_HANDLER = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Integer i[] = (Integer[]) msg.obj;
                tv_Duracation.setText("- "+Util.getDuration(i[1]-i[0]));
                seekBar.setProgress(i[2]);
                if (i[0]/1000 >= 60 && !Util.tracking){
                    if (Util.isTest)
                        presenter.Tracking(idBlog , "01658708738","audio");
                    else
                        presenter.Tracking(idBlog , Util.msisdn, "audio");
                }
            }
        };
        PlayerConstants.HANDLER_SAVE_LISTEN = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Integer i[] = (Integer[]) msg.obj;
                if (PlayerConstants.SONG_PAUSED){
                    if (Util.isTest)
                        presenter.saveListent("01658708738", String.valueOf(i[0]/1000-timeListent),idBlog,"listen");
                    else
                        presenter.saveListent(Util.msisdn, String.valueOf(i[0]/1000-timeListent),idBlog,"listen");

                    timeListent = i[0]/1000;
                }
            }
        };
//        PlayerConstants.HANDLER_UPDATE_DETAIL = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Radio radio = (Radio) msg.obj;
//                PlayerConstants.UPDATE_SAMETYPE.sendMessage(PlayerConstants.UPDATE_SAMETYPE.obtainMessage(0,radio));
//                PlayerConstants.UPDATE_SAMEVOICE.sendMessage(PlayerConstants.UPDATE_SAMEVOICE.obtainMessage(0,radio));
//                PlayerConstants.UPDATE_CMT.sendMessage(PlayerConstants.UPDATE_CMT.obtainMessage(0,radio));
//                nameCate = radio.getSigners().getName();
//                category.setText(nameCate.toUpperCase());
//                presenter.getDetail(radio.getId());
//                scrollView.scrollTo(0,0);
//            }
//        };
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            int progess = seekBar.getProgress();

            int time = (int) ((progess * PlayerConstants.DURATION) / 100);
            Log.e("seekto", "seekto" + time);
            Controls.seekTo(time);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void finish() {
        super.finish();
        transitionActivityAnimation(false);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

    }

    @Override
    public void showDetail(Radio radio) {
        tv_Tittle.setText(radio.getName());

        String singler = "";
        if (radio.getlSingler().size() == 1)
            singler = radio.getlSingler().get(0).getName();
        else {
            for (int i =0 ; i<radio.getlSingler().size() ; i++){
                if (i==radio.getlSingler().size()-1)
                    singler+=radio.getlSingler().get(i).getName();
                else
                    singler+= radio.getlSingler().get(i).getName()+",";
            }
        }
        if (singler.equals(""))
            singler+="Chưa cập nhật";
        tv_singler.setText(singler);

        String author = "";
        if (radio.getlMember().size() == 1)
            author = radio.getlMember().get(0).getName();
        else {
            for (int i =0 ; i<radio.getlMember().size() ; i++){
                if (i==radio.getlMember().size()-1)
                    author+=radio.getlMember().get(i).getName();
                else
                    author+= radio.getlMember().get(i).getName()+",";
            }
        }
        if (author.equals(""))
            author += "Chưa cập nhật";
        tv_author.setText(author);

        tv_date.setText(radio.getPublistdate());
        tv_view.setText(radio.getView()+" lượt xem");
        webView.loadDataWithBaseURL("", radio.getContent(), "text/html", "UTF-8", "");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                initFmMain();
            }
        });
        this.radio = radio;
        hideLoading();
    }

    @Override
    public void showComment(ArrayList<Comment> lComment) {

    }

    @Override
    public void trackking(int code, final String msg) {
        if (code == 0){
            Util.tracking = true;
        }
        else if (code == -4){
            Controls.pauseControl(this);
            Dialog.showDialogExtends(this , msg , "Thông Báo");
        }
        else {
            Controls.pauseControl(this);
            if (Util.isShow)
                Dialog.DialogRegister(DetailActivity.activity,viewGroup , getSupportFragmentManager(),msg);
        }
    }

    @Override
    public void successaddFavorite(int code, String msg) {
        if (code == 0){
            img_Favorite.setImageResource(R.drawable.ic_heart);
            img_Favorite.setTag(1);
            Util.showToast(getBaseContext() , msg);
        }
        else {
            Util.showToast(getBaseContext() , msg);
        }
    }

    @Override
    public void successremoveFavorite(int code, String msg) {
        if (code == 0){
            img_Favorite.setImageResource(R.drawable.bt_like);
            img_Favorite.setTag(0);
            Util.showToast(getBaseContext() , msg);
        }
        else {
            Util.showToast(getBaseContext() , msg);
        }
    }

    @Override
    public void addCmtSuccess() {

    }

    @Override
    public void checkFavorite(int code) {
        if (code == 0){
            img_Favorite.setImageResource(R.drawable.ic_heart);
            img_Favorite.setTag(1);
        }
        else {
            img_Favorite.setImageResource(R.drawable.bt_like);
            img_Favorite.setTag(0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment curFragment = MainActivity.currentFragment(getSupportFragmentManager());
        getSupportFragmentManager().popBackStack();
        if (curFragment != null)
            getSupportFragmentManager().beginTransaction().remove(curFragment).commit();
    }

    void initFmMain() {

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
        if (adapter == null){
            adapter = new MypagerAdapter(getSupportFragmentManager());
            pager.setAdapter(adapter);
            tabs.setupWithViewPager(pager);
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.setTabsFromPagerAdapter(adapter);
        }


    }
    public static void UpdateUI(){
        if (PlayerConstants.SONG_PAUSED)
            imgBtnPlay.setImageResource(R.drawable.bt_play);
        else
            imgBtnPlay.setImageResource(R.drawable.bt_pause);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.isShow = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtnPlay:
                if (!PlayerConstants.SONG_PAUSED) {
                    imgBtnPlay.setImageResource(R.drawable.bt_play);
                    Controls.pauseControl(this);
                    break;
                } else {
                    imgBtnPlay.setImageResource(R.drawable.bt_pause);
                    Controls.playControl(this);
                }
                break;
            case R.id.imv_Search:
                Intent intent = new Intent(this , SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.rv_favorite:
                String tag = img_Favorite.getTag()+"";
                if (tag.equals("0")){
                    if (Util.isTest)
                        presenter.addFavorite(idBlog , "audio" , "01658708738");
                    else
                        presenter.addFavorite(idBlog , "audio" , Util.msisdn);
                }
                else {
                    if (Util.isTest)
                        presenter.removeFavorite(idBlog , "01658708738");
                    else
                        presenter.removeFavorite(idBlog , Util.msisdn);
                }
                break;
            case R.id.rv_repead:
                String tag1 = img_repead.getTag()+"";
                if (tag1.equals("0")){
                    img_repead.setTag(1);
                    img_repead.setImageResource(R.drawable.bt_repeat);
                    Controls.changRepeatMode(this ,PlayerConstants.REPEAT_MODE.ONE );
                }
                else {
                    img_repead.setTag(0);
                    img_repead.setImageResource(R.drawable.bt_un_repeat);
                    Controls.changRepeatMode(this ,PlayerConstants.REPEAT_MODE.OFF );
                }
                break;
            case R.id.rv_download:
                if (!Util.lAllReg.contains("RADIO") && Util.lPackage.contains("RADIO")){
                    Util.showToast(getBaseContext() ,"Gói dịch vụ RADIO của bạn đã hết hạn sử dụng.");
                }
                else {
                    if (!Util.msisdn.equals("null") && !Util.msisdn.equals("") && Util.lAllReg.contains("RADIO")){
                        boolean isExist = false;
                        for (int i = 0; i < Util.listRadio.size(); i++) {
                            if (Util.listRadio.get(i).getId() == radio.getId())
                                isExist = true;
                        }
                        if (isExist)
                            Util.showToast(getBaseContext(), "Bạn đã tải Radio này rồi .");
                        else {
                            Util.listRadio.add(0 , radio);
                            SharePreference.setList("download",Util.listRadio,getBaseContext());
                            radio.setVlog(false);
                            Util.showToast(getBaseContext() , "Đang tải...");
                            Util.addToTrackDownload(radio , this , false);
                        }

                    }
                    else {
                        Util.showToast(getBaseContext() , getString(R.string.download));
                    }
                }


                break;
            case R.id.rv_share:
                Util.shareFacebook(radio,this);
                break;
            case R.id.rv_volume:
                AudioManager audioManager =
                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
        }
    }


    public class MypagerAdapter extends FragmentPagerAdapter {
        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new FragmentSameType(radio.getId(),radio.getSigners().getId(),false,nameCate);
                    break;
                case 1:
                    ArrayList<Integer> list = new ArrayList<>();
                    for (int i = 0 ; i<radio.getlSingler().size() ; i++){
                        list.add(radio.getlSingler().get(i).getId());
                    }
                    fragment = new FragmentSameAuthor(list , radio.getId(),false,nameCate);
                    break;
                case 2:
                    fragment = new FragmentCmt(radio.getId());
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tittle = "";
            switch (position){
                case 0:
                    tittle = "Cùng thể loại";
                    break;
                case 1:
                    tittle = "Cùng giọng đọc";
                    break;
                case 2:
                    tittle = "Bình luận";
                    break;
            }
            return tittle;
        }
    }
}
