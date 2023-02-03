package com.example.meituanshoppingcart.entity;

public class EventBusShoppingEntity {

    private ProductListEntity.ProductEntity entity;
    private String key;

    public EventBusShoppingEntity(ProductListEntity.ProductEntity entity, String key) {
        this.entity = entity;
        this.key = key;
    }

    public ProductListEntity.ProductEntity getEntity() {
        return entity;
    }

    public void setEntity(ProductListEntity.ProductEntity entity) {
        this.entity = entity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
