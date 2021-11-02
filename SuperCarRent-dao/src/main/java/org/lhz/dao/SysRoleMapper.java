package org.lhz.dao;

import org.lhz.entity.SysMenu;
import org.lhz.entity.SysRole;
import org.lhz.vo.SysRoleVo;

import java.util.List;

public interface SysRoleMapper {
    List<SysRole> findAllRoleList(SysRoleVo sysRoleVo);

    int addRole(SysRole sysRole);

    int deleteRoleById(Integer roleid);

    int updateRole(SysRole sysRole);

    List<SysMenu> findMenuListByRoleId(Integer roleid);

    List<SysRole> findRoleListByUserId(Integer userid);

    int insertRoleByUserId(Integer roleid ,Integer userid);

    int deleteRoleByUserId(Integer userid);
}
