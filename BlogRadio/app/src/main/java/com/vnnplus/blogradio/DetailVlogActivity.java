package com.vnnplus.blogradio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.audioplayer.Controls;
import com.vnnplus.blogradio.base.BaseActivity;
import com.vnnplus.blogradio.fragment.FragmentCmt;
import com.vnnplus.blogradio.fragment.FragmentSameAuthor;
import com.vnnplus.blogradio.fragment.FragmentSameType;
import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.DetailPresenter;
import com.vnnplus.blogradio.presenterIplm.DetailPresenterIplm;
import com.vnnplus.blogradio.util.Dialog;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Detailview;
import com.vnnplus.blogradio.view.updateView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Giangdv on 5/11/2017.
 */

public class DetailVlogActivity extends BaseActivity implements Detailview, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, View.OnClickListener {
    @Bind(R.id.SurfaceView)
    TextureView surfaceView;
    private MediaPlayer mp;
//    private SurfaceHolder holder;
    Radio radio;

    @Bind(R.id.tv_Tittle)
    TextView tv_Tittle;
    @Bind(R.id.tv_author)
    TextView tv_author;
    @Bind(R.id.tv_singler)
    TextView tv_singler;
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.tv_time)
    TextView tv_Duracation;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.imgBtnPlay)
    ImageView imgBtnPlay;
    @Bind(R.id.Player)
    ImageView imgBtnPlay1;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.img_favorite)
    ImageView img_Favorite;
    @Bind(R.id.imv_repead)
    ImageView imv_repead;
    @Bind(R.id.containe)
    LinearLayout container;
    @Bind(R.id.rlt_view)
    RelativeLayout rlt_view;
    @Bind(R.id.tv_view)
    TextView tv_view;
    @Bind(R.id.scrollview)
    NestedScrollView scrollView;
    @Bind(R.id.img_download)
    ImageView imgDownload;
    @Bind(R.id.ll_bottom)
    LinearLayout bottom;
    @Bind(R.id.img_volume)
    ImageView volume;
    @Bind(R.id.ll_top)
    LinearLayout ll_top;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.tvCurProgessRest)
    TextView tvCurProgessRest;
    @Bind(R.id.sb_process)
    SeekBar sb_process;
    @Bind(R.id.vgControlBottom)
    RelativeLayout vgControlBottom;
    ViewGroup viewGroup;
    boolean traking = false ;
    int idBlog;


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


    DetailPresenter presenter;
    boolean isPlaying;
    Timer timer;
    Handler handler;
    boolean repead = false;
    int timeListen;
    MypagerAdapter adapter;
    public static DetailVlogActivity istance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);
        table = (ViewGroup) findViewById(R.id.table);
        Util.tracking = false;
        Util.isShow = true;

        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        viewGroup = (ViewGroup) findViewById(R.id.container);
        getExtrarData();
        istance = this;
        init();
        createPresenter();
        loadData();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    void init(){
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(100);
        sb_process.setMax(100);
        sb_process.setOnSeekBarChangeListener(this);

        rvDownload.setOnClickListener(this);
        rvFavorite.setOnClickListener(this);
        rvRepead.setOnClickListener(this);
        rvVolume.setOnClickListener(this);
        rvShare.setOnClickListener(this);

        mp = new MediaPlayer();
        mp.reset();
        mp.setOnCompletionListener(this);


//        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                Util.showToast(MyApplication.singleton , getString(R.string.errorvlog));
//                return false;
//            }
//        });
        surfaceView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                Surface s = new Surface(surfaceTexture);
                if (mp != null) mp.setSurface(s);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                Surface s = new Surface(surfaceTexture);
                if (mp != null) mp.setSurface(s);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                if (mp != null) mp.setSurface(null);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                Surface s = new Surface(surfaceTexture);
                if (mp != null) mp.setSurface(s);
            }
        });

        volume.setOnClickListener(this);
        img_Favorite.setOnClickListener(this);
        imgDownload.setOnClickListener(this);
        surfaceView.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Configuration configuration = getResources().getConfiguration();
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    vgControlBottom.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }
                else
                    finish();
            }
        });
        imv_repead.setOnClickListener(this);
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            bottom.setVisibility(View.GONE);
        else
            bottom.setVisibility(View.VISIBLE);


        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,  (Util.getHeightScreen(this)*3/4));
        container.setLayoutParams(layoutParams1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mp == null)
                    mp = new MediaPlayer();
                int duration = mp.getDuration();
                int curPosition = mp.getCurrentPosition();
                int progress = 0;
                if (duration != 0)
                    progress = (curPosition * 100) / duration;

                seekBar.setProgress(progress);
                sb_process.setProgress(progress);
                tv_Duracation.setText(Util.getDuration(duration - curPosition));
                tvCurProgessRest.setText(Util.getDuration(duration - curPosition));

                if (curPosition/1000>=60 && !traking){
                    if (Util.isTest)
                        presenter.Tracking(idBlog , "01658708738","video");
                    else
                        presenter.Tracking(idBlog , Util.msisdn , "video");
                }
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (isPlaying)
                    handler.sendMessage(handler.obtainMessage());

            }
        }, 0, 1000);

    }

    @Override
    public void getExtrarData() {
        showLoading();
        transitionActivityAnimation(true);
        Intent intent = getIntent();
        idBlog = intent.getIntExtra("IDBLOG",0);
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
    protected void onDestroy() {
        if (mp != null) {
            mp.pause();
            mp.release();
            mp = null;
        }
        Util.isShow = true;
        super.onDestroy();
    }

    public void onBtnFullCreenClick(View view){
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeRotation(newConfig);
    }
    private void changeRotation(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottom.setVisibility(View.VISIBLE);

            exitVideoFullScreen();
        } else {
            makeVideoFullScreen();
        }
    }
    private void makeVideoFullScreen() {
        hideSystemUI();
        defaultScreenOrientationMode = getResources().getConfiguration().orientation;
        defaultVideoViewParams = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        bottom.setVisibility(View.GONE);
        final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        surfaceView.postDelayed(new Runnable() {

            @Override
            public void run() {

                RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(screenWidth, screenHeight);

                surfaceView.setLayoutParams(params);
                surfaceView.setPadding(0,0,0,0);
            }
        }, 200);
    }
    private RelativeLayout.LayoutParams defaultVideoViewParams;
    private int defaultScreenOrientationMode;
    void playVideo(Radio radio){
        this.radio = radio;
        int videoHeight = (int) getResources().getDimension(R.dimen.dp_230);
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            videoHeight = RelativeLayout.LayoutParams.MATCH_PARENT;
        defaultVideoViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, videoHeight);
        surfaceView.setLayoutParams(defaultVideoViewParams);
        tv_Tittle.setText(radio.getName());

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
            author+="Chưa cập nhật";
        tv_author.setText(author);

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
        tv_date.setText(radio.getPublistdate());
        tv_view.setText(radio.getView() + " lượt xem");
        imgBtnPlay.setImageResource(R.drawable.bt_pause);
        imgBtnPlay1.setImageResource(R.drawable.bt_pause);

        mp.reset();
//        mp.setDisplay(holder);


        try {
            mp.setDataSource(radio.getMediaurl());
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    isPlaying = true;
                    imgBtnPlay.setImageResource(R.drawable.bt_pause);
                    imgBtnPlay1.setImageResource(R.drawable.bt_pause);
                    mediaPlayer.start();
                }
            });


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hideLoading();

    }

    @Override
    public void showDetail(Radio radio) {
        this.radio = radio;
        playVideo(radio);
        initFmMain();
    }
    public void onBtnPlayClick(View v){
        String tag = v.getTag()+"";
        if (isPlaying){
            mp.pause();
            imgBtnPlay.setImageResource(R.drawable.bt_play);
            imgBtnPlay1.setImageResource(R.drawable.bt_play);

            if (Util.isTest)
                presenter.saveListent("01658708738", String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");
            else
                presenter.saveListent(Util.msisdn, String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");
            timeListen = mp.getCurrentPosition()/1000;
        }
        else {
            mp.start();
            imgBtnPlay.setImageResource(R.drawable.bt_pause);
            imgBtnPlay1.setImageResource(R.drawable.bt_pause);

        }
        isPlaying = !isPlaying;
    }

    @Override
    public void showComment(ArrayList<Comment> lComment) {

    }

    @Override
    public void onBackPressed() {
        Fragment curFragment = MainActivity.currentFragment(getSupportFragmentManager());
        getSupportFragmentManager().popBackStack();
        if (curFragment != null)
            getSupportFragmentManager().beginTransaction().remove(curFragment).commit();
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            vgControlBottom.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        finish();
    } 

    @Override
    public void trackking(int code, final String msg) {
        if (code == 0){
            traking = true;
        }
        else if (code == -4){
            Controls.pauseControl(this);
            Dialog.showDialogExtends(this , msg , "Thông Báo");
        }
        else {
            mp.pause();
            imgBtnPlay.setImageResource(R.drawable.bt_play);

            if (Util.isTest)
                presenter.saveListent("01658708738", String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");
            else
                presenter.saveListent(Util.msisdn, String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");
            timeListen = mp.getCurrentPosition()/1000;
            isPlaying = !isPlaying;
            Configuration configuration = getResources().getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            if (Util.isShow)
                Dialog.DialogRegister(DetailVlogActivity.istance,viewGroup , getSupportFragmentManager(),msg);
        }
    }

    @Override
    public void successaddFavorite(int code, String msg) {
        if (code == 0){
            img_Favorite.setTag(1);
            img_Favorite.setImageResource(R.drawable.ic_heart);
            Util.showToast(getBaseContext() , msg);
        }
        else {
            Util.showToast(getBaseContext() , msg);
        }
    }

    @Override
    public void successremoveFavorite(int code, String msg) {
        if (code == 0){
            img_Favorite.setTag(0);
            img_Favorite.setImageResource(R.drawable.bt_like);
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
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int time = (int) ((seekBar.getProgress() * mp.getDuration()) / 100);
        mp.seekTo(time);
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

            adapter = new MypagerAdapter(getSupportFragmentManager());
            pager.setAdapter(adapter);
            tabs.setupWithViewPager(pager);
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.setTabsFromPagerAdapter(adapter);

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (Util.isTest)
            presenter.saveListent("01658708738", String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");
        else
            presenter.saveListent(Util.msisdn, String.valueOf(mp.getCurrentPosition()/1000-timeListen), idBlog ,"view");

        timeListen = 0;
        if (repead){
            repead = false;
            playVideo(radio);
            imv_repead.setTag(0);
            imv_repead.setImageResource(R.drawable.bt_repeat);
        }
        else {
            Configuration configuration = getResources().getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            imv_repead.setImageResource(R.drawable.bt_un_repeat);
            imgBtnPlay.setImageResource(R.drawable.bt_play);
            imgBtnPlay1.setImageResource(R.drawable.bt_play);
            isPlaying = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_favorite:
                String tag = img_Favorite.getTag()+"";
                if (tag.equals("0")){
                    if (Util.isTest)
                        presenter.addFavorite(idBlog , "video" , "01658708738");
                    else
                        presenter.addFavorite(idBlog , "video" , Util.msisdn);

                }
                else {
                    if (Util.isTest)
                        presenter.removeFavorite(idBlog , "01658708738");
                    else
                        presenter.removeFavorite(idBlog , Util.msisdn);
                }
                break;
            case R.id.rv_volume:
                AudioManager audioManager =
                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            case R.id.SurfaceView:
                Configuration configution = getResources().getConfiguration();
                if (configution.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    vgControlBottom.setVisibility(View.VISIBLE);
                    vgControlBottom.postDelayed(new Runnable() {
                        public void run() {
                            vgControlBottom.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
                break;
            case R.id.rv_repead:
                String tag1 = imv_repead.getTag() + "";
                if (tag1.equals("0")){
                    repead = true;
                    imv_repead.setTag(1);
                    imv_repead.setImageResource(R.drawable.bt_repeat);
                    Util.showToast(this , "Lặp một");
                }
                else {
                    repead = false;
                    imv_repead.setTag(0);
                    imv_repead.setImageResource(R.drawable.bt_un_repeat);
                    Util.showToast(this , "không lặp");
                }
                break;
            case R.id.rv_download:
                if (!Util.lAllReg.contains("RADIO") && Util.lPackage.contains("RADIO")){
                    Util.showToast(getBaseContext() ,"Gói dịch vụ VLOG của bạn đã hết hạn sử dụng.");
                }
                else {
                    if (!Util.msisdn.equals("null") && !Util.msisdn.equals("") && Util.lAllReg.contains("VLOG")){
                        boolean isExist = false;
                        for (int i = 0; i < Util.listRadio.size(); i++) {
                            if (Util.listRadio.get(i).getId() == radio.getId())
                                isExist = true;
                        }
                        if (isExist)
                            Util.showToast(getBaseContext(), "Bạn đã tải Vlog này rồi .");
                        else {
                            Util.listRadio.add(0,radio);
                            radio.setVlog(true);
                            SharePreference.setList("download",Util.listRadio,getBaseContext());
                            Util.showToast(getBaseContext() , "Đang tải...");
                            Util.addToTrackDownload(radio , this , true);
                        }

                    }
                    else {
                        Util.showToast(getBaseContext() ,getString(R.string.downloadvlog));
                    }
                }

                break;
            case R.id.rv_share:
                Util.shareFacebook(radio,this);
                break;
        }
    }

        private void exitVideoFullScreen() {
        setRequestedOrientation(defaultScreenOrientationMode);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        surfaceView.postDelayed(new Runnable() {
            @Override
            public void run() {
                surfaceView.setLayoutParams(defaultVideoViewParams);
                surfaceView.layout(10, 10, 10, 10);
            }
        }, 200);
    }

    public class MypagerAdapter extends FragmentPagerAdapter {

    public MypagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentSameType(radio.getId(),radio.getSigners().getId(), true, "");
                break;
            case 1:
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < radio.getlSingler().size(); i++) {
                    list.add(radio.getlSingler().get(i).getId());
                }
                fragment = new FragmentSameAuthor(list, radio.getId(), true, "");
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
        switch (position) {
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
