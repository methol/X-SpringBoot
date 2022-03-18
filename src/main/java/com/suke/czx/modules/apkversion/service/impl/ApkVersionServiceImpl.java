package com.suke.czx.modules.apkversion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.apkversion.entity.ApkVersion;
import com.suke.czx.modules.apkversion.mapper.ApkVersionMapper;
import com.suke.czx.modules.apkversion.service.ApkVersionService;
import org.springframework.stereotype.Service;


@Service
public class ApkVersionServiceImpl extends ServiceImpl<ApkVersionMapper, ApkVersion> implements ApkVersionService {

}
