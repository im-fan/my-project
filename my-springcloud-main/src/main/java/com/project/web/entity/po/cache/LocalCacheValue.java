package com.project.web.entity.po.cache;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 本地缓存内容
 *
 *@author: Weiyf
 *@Date: 2020/2/21 18:20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalCacheValue {

    @ApiModelProperty("当前key可存活时长 单位s")
    private long liveSecond;

    @ApiModelProperty("缓存时的时间戳")
    private long cachedTime;

    @ApiModelProperty("缓存的结果")
    private Object values;

}
