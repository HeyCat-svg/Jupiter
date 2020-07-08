package com.se128.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.se128.jupiter.util.msgutils.Msg;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "goods")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class Goods {

    @Id
    @Column(name = "goods_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsId;

    private String name;

    private Date startTime;
    private Date endTime;

    private String address;
    private String website;

    private Integer goodsType;

    @Transient
    private String image;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private List<GoodsDetail> goodsDetails;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public List<GoodsDetail> getGoodsDetails() {
        return goodsDetails;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public void setGoodsDetails(List<GoodsDetail> goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}