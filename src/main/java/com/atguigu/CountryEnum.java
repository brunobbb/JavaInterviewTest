package com.atguigu;

import lombok.Getter;

/**
 * 自定义枚举类
 */
public enum  CountryEnum {
    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");

    @Getter
    private Integer code;
    @Getter
    private String message;

    CountryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CountryEnum foreach_CountryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum value : values) {
            if(index == value.getCode())
                return value;
        }
        return null;
    }
}
