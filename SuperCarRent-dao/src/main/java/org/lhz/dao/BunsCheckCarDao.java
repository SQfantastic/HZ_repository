package org.lhz.dao;

import org.lhz.entity.BunsBusChecks;
import org.lhz.entity.BunsBusRent;
import org.lhz.vo.BunsBusChecksVo;

import java.util.List;
import java.util.Map;

public interface BunsCheckCarDao {
    BunsBusRent queryRentByRentId(String rentid);
    Map<String, Object> initCheckFormData(String rentid);
    List<BunsBusChecks> lodaAll(BunsBusChecksVo bnsBusChecksvo);
    Long getTotal();

    Integer updateCheck(BunsBusChecks bnsBusChecks);
}
