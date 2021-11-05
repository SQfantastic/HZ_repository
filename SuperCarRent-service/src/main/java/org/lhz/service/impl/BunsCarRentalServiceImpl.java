package org.lhz.service.impl;



import org.lhz.dao.BunsCarRentalDao;
import org.lhz.dao.impl.BunsCarRentalDaoImpl;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.service.BunsCarRentalService;
import org.lhz.vo.BunsBusRentVo;

import java.text.ParseException;
import java.util.List;

public class BunsCarRentalServiceImpl implements BunsCarRentalService {
    private BunsCarRentalDao carRentalDao = new BunsCarRentalDaoImpl();

    @Override
    public BunsBusCustomer checkIdNum(BunsBusCustomer bunsBusCustomer) {
        return carRentalDao.checkIdNum(bunsBusCustomer);
    }

    @Override
    public Boolean checkSingleNo(String single) {
        BunsBusRent bunsBusRent = carRentalDao.checkSingleNo(single);
        return bunsBusRent != null?true:false;
    }

    @Override
    public Boolean addRentSingle(BunsBusRent bunsBusRent) {
        Integer integer = carRentalDao.addRentSingle(bunsBusRent);
        return integer!=0?true:false;
    }

    @Override
    public List<BunsBusRent> checkRentSingle(BunsBusRentVo bunsBusRentVo) {
        return carRentalDao.checkRentSingle(bunsBusRentVo);
    }

    @Override
    public Long total() {
        return carRentalDao.total();
    }

    @Override
    public Boolean updateRentSingle(BunsBusRent bunsBusRent) throws ParseException {
        Integer integer = carRentalDao.updateRentSingle(bunsBusRent);
        System.out.println(integer);
        return integer!=0?true:false;
    }

    @Override
    public Boolean delRentSingle(String rentid) {
        Integer integer = carRentalDao.delRentSingle(rentid);
        return integer!=0?true:false;
    }
}
