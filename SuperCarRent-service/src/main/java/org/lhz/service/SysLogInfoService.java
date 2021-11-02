package org.lhz.service;

import org.lhz.entity.SysLogInfo;
import org.lhz.vo.SysLogInfoVo;

import java.util.List;

public interface SysLogInfoService {

    List<SysLogInfo> findAllSysLoInfoList(SysLogInfoVo sysLogInfoVo);//查询所有的登录信息

    int addLogInfo(SysLogInfoVo sysLogInfoVo);//获取登录信息，并写入数据库中

    int deleteLogInfo(Integer id);//删除该登录信息
}
