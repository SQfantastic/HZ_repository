package org.lhz.service.impl;

import org.lhz.entity.BunsBusCar;
import org.lhz.dao.BunsCarMessageDao;
import org.lhz.dao.impl.BunsCarMessageDaoImpl;
import org.lhz.service.BunsCarMessageService;
import org.lhz.vo.BunsBusCarVo;

import java.util.List;

public class BunsCarMessageServiceImpl implements BunsCarMessageService {
    private BunsCarMessageDao storageDao=new BunsCarMessageDaoImpl();
    @Override
    public Long getTotal() {
        Long total = storageDao.getTotal();
        return total;
    }
    @Override
    public List<BunsBusCar> loadAll(BunsBusCarVo busCarVo) {
        Long total = storageDao.getTotal();
        List<BunsBusCar> cars = storageDao.findAll(busCarVo);
        return cars;
    }

    @Override
    public Integer addCar(BunsBusCar busCar) {
        return storageDao.addCar(busCar);
    }

    @Override
    public Integer updateCar(BunsBusCar busCar) {
        return storageDao.updateCar(busCar);
    }

    @Override
    public Integer deleteCar(String carnumber) {
        return storageDao.deleteCar(carnumber);
    }

    @Override
    public Integer deleteBatchCar(String[] ids) {
        return storageDao.deleteBatchCar(ids);
    }
}
