package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author czx
 */
@Data
@Schema(description = "用户与角色对应关系")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> implements Serializable {

    @TableId
    public Long id;

    @Schema(description = "用户ID")
    public Long userId;

    @Schema(description = "角色ID")
    public Long roleId;

}
