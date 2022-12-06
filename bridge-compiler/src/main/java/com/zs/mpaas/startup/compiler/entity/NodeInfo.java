package com.zs.mpaas.startup.compiler.entity;

import java.util.List;

import javax.lang.model.type.TypeMirror;

/**
 * ******************************
 * 项目名称:MyAnnotation
 *
 * @Author zhangsen
 * 邮箱:zhangsen839705693@163.com
 * 创建时间:2021    8:43 上午
 * 说明:
 * ******************************
 */
public class NodeInfo {
    /**
     * 包路径
     */
    private String packageName;
    /**
     * 节点所在类名称
     */
    private String className;
    /**
     * 类路径
     */
    private String classPathName;
    /**
     * 注解的value
     */
    private String appId;

    private boolean isDebug;

    /**
     * 注解类的协议类的包名
     */
    private String protocolPackageName;
    /**
     * 注解类的协议类的名字
     */
    private String protocolSimpleClassName;

    public NodeInfo(String packageName, String className, String classPathName, String appId, boolean isDebug, String protocolPackageName,String protocolSimpleClassName) {
        this.packageName = packageName;
        this.className = className;
        this.classPathName = classPathName;
        this.appId = appId;
        this.isDebug = isDebug;
        this.protocolPackageName = protocolPackageName;
        this.protocolSimpleClassName = protocolSimpleClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getClassPathName() {
        return classPathName;
    }

    public String getAppId() {
        return appId;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getProtocolPackageName() {
        return protocolPackageName;
    }

    public String getProtocolSimpleClassName() {
        return protocolSimpleClassName;
    }

}
