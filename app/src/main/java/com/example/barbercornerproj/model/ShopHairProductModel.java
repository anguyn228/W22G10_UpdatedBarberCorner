package com.example.barbercornerproj.model;

public class ShopHairProductModel {

    String productName;
    String productDetail;
    int productPrice;
    int productPic;

    public ShopHairProductModel(String productName, String productDetail, int productPrice, int productPic) {
        this.productName = productName;
        this.productDetail = productDetail;
        this.productPrice = productPrice;
        this.productPic = productPic;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public
    ShopHairProductModel(String productName, String productDetail, int productPic) {
        this.productName = productName;
        this.productDetail = productDetail;
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public int getProductPic() {
        return productPic;
    }

    public void setProductPic(int productPic) {
        this.productPic = productPic;
    }
}
