package org.lhz.service;

import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo);//查询到所有的菜单信息

    List<SysMenu> findMenuByUserId(Integer userid);

    List<SysMenu> findAllMenu(SysMenuVo sysMenuVo);//查询所有的菜单数据，包括表头的模糊查询

    int checkMenuById(Integer id);//根据id查询该菜单是否有子菜单

    int deleteMenuById(Integer id);//删除菜单

    int addMenu(SysMenu sysMenu);//添加菜单

    int updateMenu(SysMenu sysMenu);//更新菜单

    boolean insertMenuByRoleId(Integer roleid,String ids);//根据校色id更新角色信息
}
