package com.qgwy.template.aop.service.impl;

import com.qgwy.template.aop.service.AService;

/**
 * 接口的实现
 * @author lzq
 *
 */
public class AServiceImpl implements AService {
    @Override
    public void cool() {
        System.out.println("哇，楼主好帅！");
    }

    @Override
    public void cool(String name) {
        System.out.println("哇，楼主"+name+"，你好帅啊！");
    }
}