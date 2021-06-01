package cn.itcast.travel.Dao.impl;

import cn.itcast.travel.Dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class CategoryDaoimpl implements CategoryDao{
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category";
        List<Category> category = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

        return category;
    }
}
