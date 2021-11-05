package org.lhz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 车辆实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BunsBusCar {

  private String carnumber;
  private String cartype;
  private String color;
  private double price;
  private double rentprice;
  private double deposit;
  private long isrenting;
  private String description;
  private String carimg;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createtime;

}
