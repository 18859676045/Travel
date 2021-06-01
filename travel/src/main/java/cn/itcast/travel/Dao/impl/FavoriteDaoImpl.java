package cn.itcast.travel.Dao.impl;

import cn.itcast.travel.Dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao{
    JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());



    @Override
    public Favorite findByUidAndBid(int rid, int uid) {
        Favorite favorite=null;


        try {
            String sql="select * from tab_favorite where rid=? and uid = ? ";

            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorite;
    }

    public int favoriteCount(int rid){
        String sql="select count(*) from tab_favorite where rid=?";

       return jdbcTemplate.queryForObject(sql,Integer.class,rid);


    }

    @Override
    public void add(int i, int uid) {
        String sql= "insert into tab_favorite values(?,?,?)";

        jdbcTemplate.update(sql,i,new Date(),uid);
    }

    @Override
    public void deleteFavorite(int i, int uid) {
        String sql="DELETE FROM tab_favorite WHERE rid=? AND uid=? ";

        jdbcTemplate.update(sql,i,uid);
    }


}
