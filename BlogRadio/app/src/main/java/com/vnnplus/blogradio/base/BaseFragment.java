package com.vnnplus.blogradio.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;

import butterknife.ButterKnife;

/**
 * Created by Giangdv on 12/27/2016.
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentManager fmm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this, v);
        getExtraData();
        initView(inflater,container);
        initPresenter();
        createAdapter();
        configRecyclerview();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }
    public void showToastMs(String ms) {
        Toast.makeText(getActivity(), ms, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void switchFragment(BaseFragment fragment) {
        FragmentTransaction ft = fmm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialogLoading();
    }

    public abstract void initView(LayoutInflater inflater, ViewGroup viewGroup);
    public abstract void getExtraData();
    public abstract int getLayoutId();
    public abstract void createAdapter();
    public abstract void configRecyclerview();
    public abstract void initPresenter();
    public abstract void loadData();

    public void showDialogLoading() {
        BaseActivity.table.setVisibility(View.VISIBLE);
    }

    public void hideDialogLoading() {
        BaseActivity.table.setVisibility(View.GONE);

    }


}
