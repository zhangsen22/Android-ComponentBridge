package com.zs.npm.impl.register.core
/**
 * 扫描的配置类
 */
class ScanSetting {

    public static final String STARTUP_COMPILER_VERSION = "1.0.0"
    //插件的名字
    public static final String PLUGIN_NAME = "init.moudle"
    //需要插入字节码的类名称
    static final String GENERATE_TO_CLASS_NAME = 'com/example/mycontainer/bridge/BridgeInterface'
    //需要插入字节码的类class
    static final String GENERATE_TO_CLASS_FILE_NAME = GENERATE_TO_CLASS_NAME + '.class'
    //需要插入字节码的方法
    static final String GENERATE_TO_METHOD_NAME = 'insertCodeByMSA'
    //通过kapt生成的注解类的包名
    static final String ROUTER_CLASS_PACKAGE_NAME = 'com/zs/npm/impl/compiler/startup/'
    //注解类实现协议的包名
    private static final INTERFACE_PACKAGE_NAME = 'com/example/baseprotocol/'
    //插入的方法
    static final String REGISTER_METHOD_NAME = 'registerModule'
    //变量 用于判断扫描的class是不是 继承 this class
    String mySuperName = ''
    //变量 用于判断JAR里的那个file包含这个class
    //变量 用于判断JAR里的那个file包含这个class
    File fileContainsInitClass
    /**
     * scan result for {@link #mySuperName}
     * class names in this list
     */
    ArrayList<String> classList = new ArrayList<>()

    ScanSetting(String mySuperName){
        this.mySuperName = INTERFACE_PACKAGE_NAME + mySuperName
    }
}