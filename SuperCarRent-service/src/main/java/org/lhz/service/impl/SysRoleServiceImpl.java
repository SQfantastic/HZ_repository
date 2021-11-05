package org.lhz.service.impl;

import org.lhz.dao.SysRoleMapper;
import org.lhz.dao.impl.SysRoleMapperImpl;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysRole;
import org.lhz.service.SysRoleService;
import org.lhz.vo.SysRoleVo;

import java.util.List;

public class SysRoleServiceImpl implements SysRoleService {
    //注入dao层对象
    private SysRoleMapper sysRoleMapper = new SysRoleMapperImpl();

    @Override
    public List<SysRole> findAllRoleList(SysRoleVo sysRoleVo) {
        return sysRoleMapper.findAllRoleList(sysRoleVo);
    }

    @Override
    public int addRole(SysRole sysRole) {
        return sysRoleMapper.addRole(sysRole);
    }

    @Override
    public int deleteRoleById(Integer roleid) {
        return sysRoleMapper.deleteRoleById(roleid);
    }

    @Override
    public int updateRole(SysRole sysRole) {
        return sysRoleMapper.updateRole(sysRole);
    }

    @Override
    public List<SysMenu> findMenuListByRoleId(Integer roleid) {
        return sysRoleMapper.findMenuListByRoleId(roleid);
    }

    @Override
    public List<SysRole> findRoleListByUserId(Integer userid) {
        return sysRoleMapper.findRoleListByUserId(userid);
    }

    /**
     * Infor: 将拿到的角色字符串拆分成数组再转换成数字，依次插入
     *         但是在插入的时候需要清空该用户的所有角色信息
     * @param userid
     * @param roleids
     * @return : boolean
     * @author : LHZ
     * @date : 2021/11/1 17:15
     */
    @Override
    public boolean insertRoleByUserId(Integer userid, String roleids) {
        try {
            //删除所有的角色
            sysRoleMapper.deleteRoleByUserId(userid);
            //拆分字符串
            String[] roleid = roleids.split(",");
            for (int i = 0; i < roleid.length; i++) {
                sysRoleMapper.deleteRoleByUserId(Integer.valueOf(roleid[i]));
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Infor: 获取总数
     * @param
     * @return : java.lang.Long
     * @author : LHZ
     * @date : 2021/11/4 22:51
     */
    @Override
    public Long getTotal() {
        return null;
    }
}
