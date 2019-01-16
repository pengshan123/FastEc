package com.pengshan.latte.ecc.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.widget.StarLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderCommentDelegate extends LatteDelegate {
    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout=null;

    @BindView(R2.id.top_tv_comment_commit)
    AppCompatTextView mTvSumbit=null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit(){
        Toast.makeText(getContext(),"评分: "+mStarLayout.getStarCount(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
