package com.company.demotest.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "mobiles" ,indices = @Index(value = {"id"},unique = true))
public class Product {


    @PrimaryKey(autoGenerate = true)
    private  Integer mId;

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    private Integer id;
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("description")
    @ColumnInfo(name = "description")
    @Expose
    private String description;
    @SerializedName("price")
    @ColumnInfo(name = "price")
    @Expose
    private Integer price;
    @SerializedName("discountPercentage")
    @ColumnInfo(name = "discountPercentage")
    @Expose
    private Double discountPercentage;
    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    @Expose
    private Double rating;
    @SerializedName("stock")
    @ColumnInfo(name = "stock")
    @Expose
    private Integer stock;
    @SerializedName("brand")
    @ColumnInfo(name = "brand")
    @Expose
    private String brand;
    @SerializedName("category")
    @ColumnInfo(name = "category")
    @Expose
    private String category;
    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("qty")
    @ColumnInfo(name = "qty")
    @Expose
    private String qty;
   /* @SerializedName("images")
    @Expose
    private List<String> images;*/


    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mId=" + mId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", rating=" + rating +
                ", stock=" + stock +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }

    /*
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
*/

}
