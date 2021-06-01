package cn.itcast.travel.service;

public interface FavoriteService {

    public boolean isFavorite(String rid,int uid);

    void addFavorite(String rid, int uid);

    void deleteFavorite(String rid, int uid);
}
