package com.example.meituanshoppingcart.entity;

import java.util.List;

public class ProductListEntity {
    private String typeId;
    private String typeName;
    private List<ProductEntity> productEntities;
    private int typeCount;

    public ProductListEntity(String typeId, String typeName, List<ProductEntity> productEntities, int typeCount) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.productEntities = productEntities;
        this.typeCount = typeCount;
    }

    public ProductListEntity(String typeId, String typeName, List<ProductEntity> productEntities) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.productEntities = productEntities;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public static class ProductEntity{
        private String productImg;
        private String productId;
        private String productName;
        private String productMonth;
        private Double productMoney;
        private int productCount;

        private Double productCartMoney;//方便购物车计算的价格
        private String parentId;//父ID，用来更新左侧列表使用

        public ProductEntity(String productImg, String productName, String productMonth, Double productMoney, int productCount,String productId, String parentId) {
            this.productImg = productImg;
            this.productId = productId;
            this.productName = productName;
            this.productMonth = productMonth;
            this.productMoney = productMoney;
            this.productCount = productCount;
            this.productCartMoney = Double.valueOf(productCount);
            this.parentId = parentId;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductMonth() {
            return productMonth;
        }

        public void setProductMonth(String productMonth) {
            this.productMonth = productMonth;
        }

        public Double getProductMoney() {
            return productMoney;
        }

        public void setProductMoney(Double productMoney) {
            this.productMoney = productMoney;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public Double getProductCartMoney() {
            return productCartMoney;
        }

        public void setProductCartMoney(Double productCartMoney) {
            this.productCartMoney = productCartMoney;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
