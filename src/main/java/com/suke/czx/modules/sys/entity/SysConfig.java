package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author czx
 */
@Data
@Builder
@Schema(description = "系统配置信息")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends Model<SysConfig> {

    @TableId
    public Long id;

    @Schema(description = "key")
    @NotBlank(message = "参数名不能为空")
    public String configKey;

    @Schema(description = "value")
    @NotBlank(message = "参数值不能为空")
    public String configValue;

    @Schema(description = "状态 0：隐藏 1：显示")
    public Integer configStatus;

    @Schema(description = "备注")
    public String remark;

}
