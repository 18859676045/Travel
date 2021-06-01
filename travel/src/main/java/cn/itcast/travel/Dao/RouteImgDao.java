package cn.itcast.travel.Dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

        public List<RouteImg> findByRid(int rid);
}
