package cn.itcast.travel.service.impl;

import cn.itcast.travel.Dao.FavoriteDao;
import cn.itcast.travel.Dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;
import org.omg.PortableInterceptor.INACTIVE;

public class FavoriteServiceImpl implements FavoriteService{
    FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite is = favoriteDao.findByUidAndBid(Integer.parseInt(rid), uid);

        if (is!=null){
            return true;
        }
        return false;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public void deleteFavorite(String rid, int uid) {

        favoriteDao.deleteFavorite(Integer.parseInt(rid),uid);
    }


}
