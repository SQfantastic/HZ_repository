package org.lhz.dao;

import org.lhz.entity.BunsBusCar;
import org.lhz.vo.BunsBusCarVo;

import java.util.List;

public interface BunsCarMessageDao {
    Long getTotal();
    List<BunsBusCar> findAll(BunsBusCarVo busCarVo);
    Integer addCar(BunsBusCar busCar);
    Integer updateCar(BunsBusCar busCar);

    Integer deleteCar(String carnumber);

    Integer deleteBatchCar(String[] ids);
}
