package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{


    CategoryService service=new CategoryServiceimpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> list = service.findAll();
        System.out.println(list);

//        ObjectMapper mapper=new ObjectMapper();
//        response.setContentType("application/json;charset:utf-8");
//        mapper.writeValue(response.getOutputStream(),list);
            writeValue(list,response);

    }
}
