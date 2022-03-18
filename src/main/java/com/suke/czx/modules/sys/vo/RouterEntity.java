package com.suke.czx.modules.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * @author czx
 */
@Data
public class RouterEntity {

    private Long menuId;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private List<RouterEntity> children;
}
