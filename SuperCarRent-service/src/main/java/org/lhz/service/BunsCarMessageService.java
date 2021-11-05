package org.lhz.service;

import org.lhz.entity.BunsBusCar;
import org.lhz.vo.BunsBusCarVo;

import java.util.List;

public interface BunsCarMessageService {
        List<BunsBusCar> loadAll(BunsBusCarVo busCarVo);
        Integer addCar(BunsBusCar busCar);
        Long getTotal();
        Integer updateCar(BunsBusCar busCar);

        Integer deleteCar(String carnumber);

        Integer deleteBatchCar(String[] ids);
}
