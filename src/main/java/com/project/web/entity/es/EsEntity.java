package com.project.web.entity.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsEntity {

    /** database **/
    public static final String INDEX_NAME = "product";

    /** table **/
    public static final String TYPE = "estype";

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

}
