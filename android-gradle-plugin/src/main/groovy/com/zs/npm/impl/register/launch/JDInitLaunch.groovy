package com.zs.npm.impl.register.launch

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.zs.npm.impl.register.core.JDInitTransform
import com.zs.npm.impl.register.core.ScanSetting
import com.zs.npm.impl.register.utils.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ******************************
 * 项目名称:MyAnnotation
 *
 * @Author zhangsen
 * 邮箱:zhangsen839705693@163.com
 * 创建时间:2021    4:26 下午
 * 说明:
 * ******************************
 */
public class JDInitLaunch implements Plugin<Project> {
    private static String JDSTARTUP_COMPILER_VERSION = ScanSetting.STARTUP_COMPILER_VERSION

    @Override
    void apply(Project project) {
        println "------jd_startup_register plugin entrance-------start"
//        addDependencies(project)
        def isApp = project.plugins.hasPlugin(AppPlugin)
        //只有application 模块才需要运行相关的逻辑
        if (isApp) {
            Logger.make(project)

            println '----Project enable jd_startup_register plugin---'

            def android = project.extensions.getByType(AppExtension)
            def transformImpl = new JDInitTransform(project)

            //init jd_annotation_register settings
            ArrayList<ScanSetting> list = new ArrayList<>()
            list.add(new ScanSetting('AbProtocolFactory'))
            JDInitTransform.registerList = list
            //Transform会在gradle构建过程中，从class文件到dex文件期间，对class文件或资源文件做相关的修改。
            android.registerTransform(transformImpl)
        }

        println "------jd_startup_register plugin entrance-------end"

    }

    private void addDependencies(Project project) {
        project.dependencies {
            if (project.plugins.hasPlugin('kotlin-kapt')) {
                Logger.i("has 'kotlin-kapt' plugin ,use kapt!!")
                kapt "com.jingdong.wireless.mpaas:startup-compiler:$JDSTARTUP_COMPILER_VERSION"
            } else {
                Logger.i("use annotationProcessor!!")
                annotationProcessor "com.jingdong.wireless.mpaas:startup-compiler:$JDSTARTUP_COMPILER_VERSION"
            }
        }
    }
}
