package com.cgl.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/16
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataType {
    private Integer id;
    private String TypeName;
    private int IsRandomCrea;
    private int isDelete;
    private String limits;
    private String userId;
}
