package org.lhz.service.impl;

import org.lhz.dao.impl.SysLogInfoMapper;
import org.lhz.dao.impl.SysLogInfoMapperImpl;
import org.lhz.entity.SysLogInfo;
import org.lhz.service.SysLogInfoService;
import org.lhz.vo.SysLogInfoVo;

import java.util.List;

public class SysLogInfoServiceImpl implements SysLogInfoService {
    //注入dao层对象
    private SysLogInfoMapper sysLogInfoMapper = new SysLogInfoMapperImpl();

    @Override
    public List<SysLogInfo> findAllSysLoInfoList(SysLogInfoVo sysLogInfoVo) {
        return sysLogInfoMapper.findAllSysLoInfoList(sysLogInfoVo);
    }

    @Override
    public int addLogInfo(SysLogInfoVo sysLogInfoVo) {
        return sysLogInfoMapper.addLogInfo(sysLogInfoVo);
    }

    @Override
    public int deleteLogInfo(Integer id) {
        return sysLogInfoMapper.deleteLogInfo(id);
    }
}
