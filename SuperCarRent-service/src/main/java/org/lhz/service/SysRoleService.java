package org.lhz.service;

import org.lhz.entity.SysMenu;
import org.lhz.entity.SysRole;
import org.lhz.vo.SysRoleVo;

import java.util.List;

public interface SysRoleService {
    List<SysRole> findAllRoleList(SysRoleVo sysRoleVo);//查找所有的角色信息

    int addRole(SysRole sysRole);//添加角色

    int deleteRoleById(Integer roleid);//删除角色

    int updateRole(SysRole sysRole);//更新角色

    List<SysMenu> findMenuListByRoleId(Integer roleid);//通过角色id查询该角色所对应的菜单信息

    List<SysRole> findRoleListByUserId(Integer userid);//通过该用户id查询到该用户所拥有的角色

    boolean insertRoleByUserId(Integer userid, String roleids);//通过用户id插入角色信息
}
