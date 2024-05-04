package org.lybaobei.utils;

import org.lybaobei.entity.SystemMenu;
import org.lybaobei.entity.SystemOrg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
public class TreeUtil {
    
    public static  List<SystemOrg> buildOrgTree(List<SystemOrg> list){
        List<SystemOrg> treeList = new ArrayList<>();
        for(SystemOrg t:list){
            if(t.getParentId() == 0){
                treeList.add(findOrgChildren(t,list));
            }
        }
        return treeList;
    }
    
    public static List<SystemMenu> buildMenuTree(List<SystemMenu> list){
        List<SystemMenu> treeList = new ArrayList<>();
        for(SystemMenu menu:list){
            
            if(menu.getParentId() == 0){
                treeList.add(findMenuChildren(menu,list));
            }
        }
        return treeList;
    }
    
    public static SystemMenu findMenuChildren(SystemMenu menu,List<SystemMenu> list){
        menu.setChildren(new ArrayList<>());
        for (SystemMenu m:list) {
            if(m.getParentId().equals(menu.getMenuId())){
                if(menu.getChildren() == null){
                    menu.setChildren(new ArrayList<>());
                }
                menu.getChildren().add(findMenuChildren(m,list));
            }
        }
        return menu;
    }
    
    
    private static SystemOrg findOrgChildren(SystemOrg t,List<SystemOrg> list){
        t.setChildren(new ArrayList<>());
    
        for (SystemOrg org:list) {
            if(org.getParentId().equals(t.getOrgId())){
                if(t.getChildren() == null){
                    t.setChildren(new ArrayList<>());
                }
                t.getChildren().add(findOrgChildren(org,list));
            }
            
        }
        return t;
    }
}
