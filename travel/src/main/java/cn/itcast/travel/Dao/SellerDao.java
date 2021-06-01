package cn.itcast.travel.Dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    public Seller findBySid(int sid);
}
