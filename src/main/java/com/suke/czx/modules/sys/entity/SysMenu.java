package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author czx
 */
@Data
@Schema(description = "菜单管理")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> implements Serializable {

    @TableId(value = "menu_id", type = IdType.AUTO)
    @Schema(description = "菜单ID")
    public Long menuId;

    @Schema(description = "父菜单ID，一级菜单为0")
    public Long parentId;

    @Schema(description = "父菜单名称")
    @TableField(exist = false)
    public String parentName;

    @Schema(description = "菜单名称")
    public String name;

    @Schema(description = "菜单URL")
    public String url;

    @Schema(description = "授权(多个用逗号分隔，如：user:list,user:create)")
    public String perms;

    @Schema(description = "类型 0：目录 1：菜单 2：按钮")
    public Integer type;

    @Schema(description = "菜单图标")
    public String icon;

    @Schema(description = "排序")
    public Integer orderNum;

    @Schema(description = "ztree属性")
    @TableField(exist = false)
    public Boolean open;

    @Schema(description = "子菜单")
    @TableField(exist = false)
    public List<?> list;
}
