package cn.itcast.travel.service.impl;

import cn.itcast.travel.Dao.FavoriteDao;
import cn.itcast.travel.Dao.RouteDao;
import cn.itcast.travel.Dao.RouteImgDao;
import cn.itcast.travel.Dao.SellerDao;
import cn.itcast.travel.Dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.Dao.impl.RouteDaoimpl;
import cn.itcast.travel.Dao.impl.RouteImgDaoimpl;
import cn.itcast.travel.Dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceimpl implements RouteService {
    private RouteDao routeDao=new RouteDaoimpl();
    private RouteImgDao routeImgDao=new RouteImgDaoimpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int PageSize,String rname) {

        PageBean<Route> pd=new PageBean<Route>();

        pd.setCurrentPage(currentPage);//当前页面
        pd.setIntpageSize(PageSize);//每页数量

        int totalCount = routeDao.getTotalCount(cid,rname);
        pd.setTotalCount(totalCount);//总数

        int start=(currentPage-1)*PageSize;
        List<Route> list=routeDao.findByPage(cid,start,PageSize,rname);
        pd.setList(list);//每页集合

        int totalPage=totalCount%PageSize==0?totalCount/PageSize:(totalCount/PageSize)+1;

        pd.setTotalPage(totalPage);//总页数

        return pd;
    }

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));

        List<RouteImg> byRid = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(byRid);

        Seller bySid = sellerDao.findBySid(route.getSid());
        route.setSeller(bySid);

        int i = favoriteDao.favoriteCount(Integer.parseInt(rid));
        route.setCount(i);

        return route;
    }
}
