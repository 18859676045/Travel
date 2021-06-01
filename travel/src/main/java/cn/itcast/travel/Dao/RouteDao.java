package cn.itcast.travel.Dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    public int getTotalCount(int cid,String rname);
    public List<Route> findByPage(int cid,int star,int pageSize,String rname);

    public Route findOne(int rid);

}
