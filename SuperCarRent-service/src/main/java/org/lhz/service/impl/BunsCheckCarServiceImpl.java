package org.lhz.service.impl;


import org.lhz.entity.BunsBusChecks;
import org.lhz.entity.BunsBusRent;
import org.lhz.dao.BunsCheckCarDao;
import org.lhz.dao.impl.BunsCheckCarDaoImpl;
import org.lhz.service.BunsCheckCarService;
import org.lhz.vo.BunsBusChecksVo;

import java.util.List;
import java.util.Map;

public class BunsCheckCarServiceImpl implements BunsCheckCarService {
    private BunsCheckCarDao checkListDao=new BunsCheckCarDaoImpl();
    @Override
    public Long getTotal() {
        return checkListDao.getTotal();
    }
    @Override
    public BunsBusRent queryRentByRentId(String rentid) {
        return checkListDao.queryRentByRentId(rentid);
    }

    @Override
    public Map<String, Object> initCheckFormData(String rentid) {
        return checkListDao.initCheckFormData(rentid);
    }

    @Override
    public List<BunsBusChecks> loadAll(BunsBusChecksVo bnsBusChecksvo) {
        return checkListDao.lodaAll(bnsBusChecksvo);
    }


    @Override
    public Integer updateCheck(BunsBusChecks bnsBusChecks) {
        return checkListDao.updateCheck(bnsBusChecks);
    }

}
