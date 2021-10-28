package org.lhz.dao;

import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public interface SysMenuMapper {
    List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo);//查询到所有的菜单信息

    List<SysMenu> findMenuByUserId(Integer userid);
}
