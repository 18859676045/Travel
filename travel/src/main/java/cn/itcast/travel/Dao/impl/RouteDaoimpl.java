package cn.itcast.travel.Dao.impl;

import cn.itcast.travel.Dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoimpl implements RouteDao{
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(int cid,String rname) {
       String sql="SELECT COUNT(*) from tab_route where 1=1 ";

       StringBuilder stringBuilder=new StringBuilder(sql);
       List params=new ArrayList();

       if (cid!=0){
           stringBuilder.append(" and cid = ? ");
           params.add(cid);//添加问号的值
       }

       if(rname!=null&&rname.length()>0){
           stringBuilder.append(" and rname like ? ");
           params.add("%"+rname+"%");//添加问号的值
       }
       sql=stringBuilder.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int star, int pageSize,String rname) {
     //String sql="select * from tab_route where cid=? limit ? , ?";
        String sql="SELECT * from tab_route where 1=1 ";

        StringBuilder stringBuilder=new StringBuilder(sql);
        List params=new ArrayList();

        if (cid!=0){
            stringBuilder.append(" and cid = ? ");
            params.add(cid);//添加问号的值
        }

        if(rname!=null&&rname.length()>0){
            stringBuilder .append(" and rname like ? ");
            params.add("%"+rname+"%");//添加问号的值
        }
        stringBuilder.append(" limit ? , ? ");
        params.add(star);
        params.add(pageSize);

        sql=stringBuilder.toString();



        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid = ? ";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
