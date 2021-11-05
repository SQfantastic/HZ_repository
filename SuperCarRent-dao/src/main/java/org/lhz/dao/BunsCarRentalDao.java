package org.lhz.dao;


import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.vo.BunsBusRentVo;

import java.text.ParseException;
import java.util.List;

public interface BunsCarRentalDao {
    BunsBusCustomer checkIdNum(BunsBusCustomer bunsBusCustomer);
    BunsBusRent checkSingleNo(String single);
    Integer addRentSingle(BunsBusRent bunsBusRent);
    Long total();
    List<BunsBusRent> checkRentSingle(BunsBusRentVo bunsBusRentVo);
    Integer updateRentSingle(BunsBusRent bunsBusRent) throws ParseException;
    Integer delRentSingle(String rentid);
}
