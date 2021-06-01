package cn.itcast.travel.Dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    public User findByUsername(String username);

    public void save(User user);

    User findbyCode(String code);

    void updataStatus(User user);


    User findByUsernameAndPassword(String username, String password);
}
