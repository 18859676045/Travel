package cn.itcast.travel.Dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    public Favorite findByUidAndBid(int uid, int rid);
    public int favoriteCount(int rid);


    void add(int i, int uid);

    void deleteFavorite(int i, int uid);
}