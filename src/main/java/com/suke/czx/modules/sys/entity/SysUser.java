package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author czx
 */
@Data
@Schema(description = "系统用户")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> {

    @TableId(value = "user_id", type = IdType.AUTO)
    public Long userId;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    public String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    public String password;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    public String email;

    @Schema(description = "手机号")
    public String mobile;

    @Schema(description = "状态  0：禁用   1：正常")
    public Integer status;

    @Schema(description = "创建者ID")
    public Long createUserId;

    @Schema(description = "创建时间")
    public Date createTime;

    @TableField(exist = false)
    @Schema(description = "角色ID")
    public List<Long> roleIdList;
}
