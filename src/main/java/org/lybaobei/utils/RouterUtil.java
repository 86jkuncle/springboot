package org.lybaobei.utils;

import org.apache.commons.lang3.StringUtils;
import org.lybaobei.common.Constants;
import org.lybaobei.entity.SystemMenu;
import org.lybaobei.vo.MetaVO;
import org.lybaobei.vo.RouterVO;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
public class RouterUtil {
    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    public static List<RouterVO> buildRouters(List<SystemMenu> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (SystemMenu menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getMenuIcon()));
            List<SystemMenu> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getMenuType() == Constants.MenuType.MENUED) {
                List<SystemMenu> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SystemMenu hiddenMenu : hiddenMenuList) {
                    RouterVO hiddenRouter = new RouterVO();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVO(hiddenMenu.getMenuName(), hiddenMenu.getMenuIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }
    
    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(SystemMenu menu) {
        String routerPath = "/" + menu.getMenuAddr();
        if(menu.getParentId() != 0) {
            routerPath = menu.getMenuAddr();
        }
        return routerPath;
    }
}
