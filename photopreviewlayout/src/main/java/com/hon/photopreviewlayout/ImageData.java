package com.hon.photopreviewlayout;

import java.util.List;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageData<E> {

    public static final int RES_ID=0x01;
    public static final int URL=0x02;

    private int dataType;

    private List<E> data;

    public ImageData(int dataType, List<E> data) {
        this.dataType = dataType;
        this.data = data;
    }

    public int getDataType() {
        return dataType;
    }

    public List<E> getData() {
        return data;
    }
}
