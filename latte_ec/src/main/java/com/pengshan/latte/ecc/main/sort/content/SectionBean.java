package com.pengshan.latte.ecc.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    private boolean mIsMore=false;

    private int mId=-1;

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public boolean isIsMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
