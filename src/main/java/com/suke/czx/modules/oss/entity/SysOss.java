package com.suke.czx.modules.oss.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件上传
 *
 * @author czx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOss extends Model<SysOss> implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //URL地址
    private String url;
    //创建时间
    private Date createDate;
}
