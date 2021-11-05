package org.lhz.service;



import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.vo.BunsBusRentVo;

import java.text.ParseException;
import java.util.List;

public interface BunsCarRentalService {
    BunsBusCustomer checkIdNum(BunsBusCustomer bunsBusCustomer);

    Boolean checkSingleNo(String single);

    Boolean addRentSingle(BunsBusRent bunsBusRent);

    List<BunsBusRent> checkRentSingle(BunsBusRentVo bunsBusRentVo);

    Long total();

    Boolean updateRentSingle(BunsBusRent bunsBusRent) throws ParseException;

    Boolean delRentSingle(String rentid);
}
