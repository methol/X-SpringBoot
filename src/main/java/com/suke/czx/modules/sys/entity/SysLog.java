package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 系统日志
 *
 * @author czx
 */
@Data
@Schema(description = "系统日志")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends Model<SysLog> {

    @TableId
    public Long id;

    @Schema(description = "用户名")
    public String username;

    @Schema(description = "用户操作")
    public String operation;

    @Schema(description = "请求方法")
    public String method;

    @Schema(description = "请求参数")
    public String params;

    @Schema(description = "执行时长(毫秒)")
    public Long time;

    @Schema(description = "IP地址")
    public String ip;

    @Schema(description = "创建时间")
    public Date createDate;
}
