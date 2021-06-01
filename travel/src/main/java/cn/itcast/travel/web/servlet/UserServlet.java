package cn.itcast.travel.web.servlet;

import cn.itcast.travel.Dao.UserDao;
import cn.itcast.travel.Dao.impl.UserDaoimpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sound.sampled.Line;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceimpl();

    /**
     * 注册页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void registUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        Map<String, String[]> map = request.getParameterMap();

        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {

            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

            return;
        }


        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

       // UserService service = new UserServiceimpl();

        boolean flag = service.regist(user);

        ResultInfo info = new ResultInfo();
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("已有用户，注册失败 ");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);


    }

    /**
     * 登陆页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Map<String, String[]> map = request.getParameterMap();
        String check = request.getParameter("check");
        String checkbox = request.getParameter("checkbox");
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // UserService service = new UserServiceimpl();
        User u = service.findLogin(user);

        request.getSession().setAttribute("user", u);

        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        ResultInfo info = new ResultInfo();


//        String username = "";
//        String password = "";
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                System.out.println(c.getName());
//                if ("username".equals(c.getName())) {
//                    username = URLDecoder.decode(c.getValue(), "utf-8");
//                }
//                if ("password".equals(c.getName())) {
//                    password = URLDecoder.decode(c.getValue(), "utf-8");
//                }
//                UserDao userDao = new UserDaoimpl();
//                User flag = userDao.findByUsernameAndPassword(username, password);
//                if (flag != null) {
//                    info.setFlag(true);
//                }
//            }
//        }


            if (!check.equalsIgnoreCase(checkcode_server)) {
                info.setFlag(false);
                info.setErrorMsg("验证码错误");
            }
            if (u != null && "Y".equals(u.getStatus()) && checkcode_server.equalsIgnoreCase(check)) {

                if ("true".equals(checkbox)) {
                    Cookie cookie = new Cookie("autoLogin", u.getUsername()+"#"+u.getPassword());
               //     Cookie passwordCoookie = new Cookie("password", URLEncoder.encode(u.getPassword(), "utf-8"));
                   cookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(cookie);
                }
                info.setFlag(true);
            }

            if (u == null) {
                info.setFlag(false);
                info.setErrorMsg("密码或者用户名错误");
            }
            if (u != null && !"Y".equals(u.getStatus())) {
                info.setFlag(false);
                info.setErrorMsg("您尚未激活，请先激活再登陆");
            }
            ObjectMapper mapper = new ObjectMapper();

            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), info);

        }
    /**
     * 自动登陆
     */
    public void autoLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        { ResultInfo info = new ResultInfo();
            Cookie[] cookies = request.getCookies();

            if (cookies!=null){
                for (Cookie cookie : cookies) {
                    if ("autoLogin".equals(cookie.getName())){
                        boolean flag=false;
                        String value = cookie.getValue();
                        String username = value.split("#")[0];
                        String password = value.split("#")[1];
                        User user=new User();
                        user.setPassword(password);
                        user.setUsername(username);
                        User loginUser = service.findLogin(user);
                        if (loginUser!=null){
                            request.getSession().setAttribute("user",loginUser);

                            info.setFlag(true);
                            writeValue(info,response);
                            return;
                        }

                    }
                }
            }
            info.setErrorMsg("自动登陆过期");
            info.setFlag(false);
            writeValue(info,response);




        }}



    /**
     * 查找user
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {

            Object user = request.getSession().getAttribute("user");

            ObjectMapper mapper = new ObjectMapper();

            response.setContentType("application/json,charset=utf-8");
            mapper.writeValue(response.getOutputStream(), user);
        }
    }


    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {

            request.getSession().invalidate();

            Cookie[] cookies = request.getCookies();
            for (Cookie c : cookies) {
                if ("autoLogin".equals(c)){
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }

//            Cookie cookie = new Cookie("autoLogin","");
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
                    //跳转页面
                     ResultInfo info = new ResultInfo();
                    info.setFlag(false);

            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {

            String code = request.getParameter("code");
            if (code!=null){
               // UserService service=new UserServiceimpl();
                service.active(code);

                boolean flag= service.active(code);

                String msg=null;
                if (flag){
                    msg="激活成功,请点击<a href='http://localhost:/travel/login.html'>登陆</a>";
                }
                else {
                    msg="激活失败，联系管理员QQ：1743225606";
                }
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(msg);
            }
        }
    }
}

