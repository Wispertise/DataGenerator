package com.cgl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDataRequest {
    private String UserId;
    private String TableName;
}
