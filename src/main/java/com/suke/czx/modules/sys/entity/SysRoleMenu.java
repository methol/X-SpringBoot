package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 *
 * @author czx
 */
@Data
@Schema(description = "角色与菜单对应关系")
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {

    @TableId
    public Long id;

    @Schema(description = "角色ID")
    public Long roleId;

    @Schema(description = "菜单ID")
    public Long menuId;

}
