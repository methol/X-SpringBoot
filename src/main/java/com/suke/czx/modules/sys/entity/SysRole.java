package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author czx
 */
@Data
@Schema(description = "角色")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends Model<SysRole> implements Serializable {

    @Schema(description = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    public Long roleId;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    public String roleName;

    @Schema(description = "备注")
    public String remark;

    @Schema(description = "创建者ID")
    public Long createUserId;

    @Schema(description = "创建时间")
    public Date createTime;

    @Schema(description = "菜单ID")
    @TableField(exist = false)
    public List<Long> menuIdList;
}
