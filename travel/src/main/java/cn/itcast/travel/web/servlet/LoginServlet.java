package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        Map<String, String[]> map = request.getParameterMap();
        String check = request.getParameter("check");
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService service=new UserServiceimpl();
        User u=service.findLogin(user);

        request.getSession().setAttribute("user",u);

        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        ResultInfo info=new ResultInfo();

        if (!check.equalsIgnoreCase(checkcode_server)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }
        if (u!=null && "Y".equals(u.getStatus())&&checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(true);

        }
        if (u==null){
            info.setFlag(false);
            info.setErrorMsg("密码或者用户名错误");
        }
        if (u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请先激活再登陆");
        }
        ObjectMapper mapper=new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        this.doPost(request, response);
    }
}
