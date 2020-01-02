package com.test.order.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 地址实体类
 *
 * @author MicalJ
 * @create 2019/11/26 14:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private Integer addressId;

    private String address;

    @Pattern(regexp = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}")
    @NotBlank(message = "手机号码不能为空")
    private String addressTel;

    private Integer userId;

    private String addressName;

    private Integer isDefault;
}
