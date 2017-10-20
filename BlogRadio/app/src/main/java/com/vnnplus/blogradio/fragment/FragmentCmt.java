package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.CommentAdapter;
import com.vnnplus.blogradio.audioplayer.PlayerConstants;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.DetailPresenter;
import com.vnnplus.blogradio.presenterIplm.DetailPresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Detailview;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 3/15/2017.
 */

public class FragmentCmt extends BaseFragment implements Detailview {
    CommentAdapter adapter;
    DetailPresenter presenter;
    ArrayList<Comment> lComment = new ArrayList<>();
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    int blog_id;
    @Bind(R.id.send_Cmt)
    ImageView send_Cmt;
    @Bind(R.id.edt_Cmt)
    EditText edt_Cmt;
    @Bind(R.id.tv_Comment)
    TextView tv_Comment;
    @Bind(R.id.imgAvatar)
    ImageView imgAvatar;



    public FragmentCmt(int blog_id){
        this.blog_id = blog_id;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        if (!Util.AvatarUser.equals(""))
            Util.setImage(imgAvatar , getActivity() , Util.AvatarUser);
        edt_Cmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                send_Cmt.setBackgroundResource(R.drawable.bt_comment);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        send_Cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_Cmt.setBackgroundResource(R.drawable.bt_comment1);
                if (Util.lPackage.size() == 0)
                    showToastMs("Bạn phải đăng nhập để gửi bình luận");
                else {
                    if (edt_Cmt.getText().toString().length() < 10)
                        showToastMs(getString(R.string.sendcmt));
                    else {
                        if (Util.isTest){
                            presenter.addComment("01658708738",blog_id,edt_Cmt.getText().toString());
                        }
                        else {
                            presenter.addComment(Util.msisdn,blog_id,edt_Cmt.getText().toString());
                        }
                        edt_Cmt.setText("");
                        InputMethodManager inputManager =
                                (InputMethodManager) getActivity().
                                        getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(
                                getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        edt_Cmt.requestFocus();
                    }
                }

            }
        });
    }


    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cmt;
    }

    @Override
    public void createAdapter() {
        adapter = new CommentAdapter(lComment, getActivity());
    }

    @Override
    public void configRecyclerview() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .build());
    }

    @Override
    public void initPresenter() {
        presenter = new DetailPresenterIplm(this , getActivity());
    }

    @Override
    public void loadData() {
        presenter.getComment(blog_id);
    }

    @Override
    public void showDetail(Radio radio) {

    }

    @Override
    public void showComment(ArrayList<Comment> lComment) {
        this.lComment.clear();
        this.lComment.addAll(lComment);
        adapter.setlComment(this.lComment);
        adapter.notifyDataSetChanged();
        tv_Comment.setText(lComment.size() + " bình luận");
    }

    @Override
    public void trackking(int code, String msg) {

    }

    @Override
    public void successaddFavorite(int code, String msg) {

    }

    @Override
    public void successremoveFavorite(int code, String msg) {

    }

    @Override
    public void addCmtSuccess() {
        presenter.getComment(blog_id);
    }

    @Override
    public void checkFavorite(int code) {

    }
}
