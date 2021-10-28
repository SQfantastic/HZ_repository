package org.lhz.service;

import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo);//查询到所有的菜单信息

    List<SysMenu> findMenuByUserId(Integer userid);
}
