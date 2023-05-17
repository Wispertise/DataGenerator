package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonStringVo<T> {
    private String valname;
    private T val;
}
