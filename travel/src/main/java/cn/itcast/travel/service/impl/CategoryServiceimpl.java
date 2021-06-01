package cn.itcast.travel.service.impl;

import cn.itcast.travel.Dao.CategoryDao;
import cn.itcast.travel.Dao.impl.CategoryDaoimpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.swing.text.html.CSS;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceimpl implements CategoryService {
    CategoryDao categoryDao=new CategoryDaoimpl();

    @Override
    public List<Category> findAll() {
        //redis中获取
        List<Category> cs=null;
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorys=jedis.zrange("category",0,-1);

        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if (categorys==null||categorys.size()==0){
            System.out.println("数据库查找");

             cs = categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {

                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }
        else {

            System.out.println("内存");
            cs=new ArrayList<Category>();
            for (Tuple name : categorys) {
             Category category=new Category();
             category.setCname(name.getElement());
             category.setCid((int) name.getScore());
             cs.add(category);
            }

        }

        return cs;
    }
}
