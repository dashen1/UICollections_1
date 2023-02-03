package com.example.tiktolkcommentdialog.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CommentMoreBean implements MultiItemEntity {

    private long totalCount;
    private long position;
    private long positionCount;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(long positionCount) {
        this.positionCount = positionCount;
    }

    @Override
    public int getItemType() {
        return CommentEntity.TYPE_COMMENT_MORE;
    }
}
