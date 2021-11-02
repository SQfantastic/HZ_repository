package org.lhz.service.impl;

import org.lhz.dao.SysMenuMapper;
import org.lhz.dao.impl.SysMenuMapperImpl;
import org.lhz.entity.SysMenu;
import org.lhz.service.SysMenuService;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public class SysMenuServiceImpl implements SysMenuService {
    //注入dao层的对象
    private SysMenuMapper sysMenuMapper = new SysMenuMapperImpl();
    @Override
    public List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo) {
        return sysMenuMapper.findAllMenuList(sysMenuVo);
    }

    @Override
    public List<SysMenu> findMenuByUserId(Integer userid) {
        return sysMenuMapper.findMenuByUserId(userid);
    }

    @Override
    public List<SysMenu> findAllMenu(SysMenuVo sysMenuVo) {
        return sysMenuMapper.findAllMenu(sysMenuVo);
    }

    @Override
    public int checkMenuById(Integer id) {
        return sysMenuMapper.checkMenuById(id);
    }

    @Override
    public int deleteMenuById(Integer id) {
        return sysMenuMapper.deleteMenuById(id);
    }

    @Override
    public int addMenu(SysMenu sysMenu) {
        return sysMenuMapper.addMenu(sysMenu);
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
        return sysMenuMapper.updateMenu(sysMenu);
    }

    /**
     * Infor: 在更改角色菜单信息的情况下需要先清空该角色下的所有菜单
     *        信息
     * @param roleid
     * @param ids
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 11:13
     */
    @Override
    public boolean insertMenuByRoleId(Integer roleid,String ids) {
        try {
            //通过角色id删除该角色下的所有菜单信息
            sysMenuMapper.deleteMenuByRoleId(roleid);
            String[] menuIds = ids.split(",");
            System.out.println(menuIds);
            //循环插入菜单数据
            for (int i = 0; i < menuIds.length; i++) {
                sysMenuMapper.insertMenuByRoleId(roleid,Integer.parseInt(menuIds[i]));
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

}
