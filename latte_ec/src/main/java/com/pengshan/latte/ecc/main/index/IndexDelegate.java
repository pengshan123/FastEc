package com.pengshan.latte.ecc.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte.ecc.main.EcBottomDelegate;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.BaseDescoration;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.ui.refresh.RefreshHandler;

import java.util.ArrayList;

import butterknife.BindView;

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView;

    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R2.id.tb_index)
    Toolbar mToolbar;

    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan;

    @BindView(R2.id.icon_index_message)
    IconTextView mIconMessage;

    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView;

    private RefreshHandler mRefreshHandler;


    private void initRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwipeRefreshLayout.setProgressViewOffset(true,120,300);

    }

    private void initRecyclerView(){
        final GridLayoutManager manager=new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(
                BaseDescoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
        final EcBottomDelegate ecBottomDelegate=getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("http://127.0.0.1/shouye");

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler=RefreshHandler.create(mSwipeRefreshLayout,mRecyclerView,new IndexDataConverter());
    }
}
















