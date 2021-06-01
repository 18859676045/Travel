package cn.itcast.travel.service.impl;

import cn.itcast.travel.Dao.UserDao;
import cn.itcast.travel.Dao.impl.UserDaoimpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceimpl implements UserService{
private UserDao userDao=new UserDaoimpl();

    //注册用户
    @Override
    public boolean regist(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if (u!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);

        String content="尊敬的用户："+user.getName()+
                "<br><a href='http://localhost:/travel/user/active?code="+user.getCode()+"'>" + "点击激活用户【yg旅游网】</a>" +
                "<br>congratulation!!!";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findbyCode(code);
        if (user != null) {

            userDao.updataStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findLogin(User user) {


        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
