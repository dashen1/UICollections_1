package com.example.meituanshoppingcart.impl;

import android.view.View;

import com.example.meituanshoppingcart.entity.ProductListEntity;

public interface ShopCartImp {

    void add(View view, int position, ProductListEntity.ProductEntity entity);

    void remove(View view, int position, ProductListEntity.ProductEntity entity);
}
