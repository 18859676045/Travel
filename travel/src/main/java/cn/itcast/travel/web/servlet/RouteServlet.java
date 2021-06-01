package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceimpl;

import javax.print.attribute.standard.PDLOverrideSupported;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService= new RouteServiceimpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");

        String pageSizeStr = request.getParameter("pageSize");

        String cidStr = request.getParameter("cid");

        String rname = request.getParameter("rname");
        rname=new String((rname.getBytes("iso-8859-1")),"utf-8");
       //   rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
        if ("null".equals(rname)){
            rname=null;
        };


        int cid=0;//类别id
        if (cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){
            cid=Integer.parseInt(cidStr);
        }

        int currentPage=0;//当前页码
        if (currentPageStr!=null&&currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        int pageSize=0;//每页显示数
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;
        }

        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);

        //输出ajax字符形式输出html页面上
        writeValue(pb,response);

    }

    public void findone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route=routeService.findOne(rid);

        writeValue(route,response);

    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;

        if (user==null){
            uid=0;
        }
        else {
            uid=user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);

        writeValue(flag,response);
    }

    /**
     * 点击收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;

        if (user==null){
            return;
        }
        else {
            uid=user.getUid();
        }
         favoriteService.addFavorite(rid,uid);
    }

    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;

        if (user==null){
            return;
        }
        else {
            uid=user.getUid();
        }
        favoriteService.deleteFavorite(rid,uid);
    }
}



