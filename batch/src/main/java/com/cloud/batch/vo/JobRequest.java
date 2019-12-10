package com.cloud.batch.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel
@Data
public class JobRequest {
    @ApiModelProperty(value = "时间", required = true)
    private String cron;
    @ApiModelProperty(value = "任务名称", required = true)
    private String jobName;
    @ApiModelProperty(value = "任务组", required = true)
    private String jobGroup;
    @ApiModelProperty(value = "任务实体名", required = false)
    private String jobClassName;
}
