package com.zs.npm.impl.register.core

import com.zs.npm.impl.register.utils.Logger
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * 扫面目录和第三方jar工具类
 */
class ScanUtil {

    /**
     * scan jar file
     * @param jarFile All jar files that are compiled into apk
     * @param destFile dest file after this transform
     */
    static void scanJar(File jarFile, File destFile) {
        if (jarFile) {
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                //通过判断class的className是否以对应ROUTER_CLASS_PACKAGE_NAME开头，
                // ROUTER_CLASS_PACKAGE_NAME开头的话证明就是JDAnitation在注解编译期间产生的类文件，
                // 然后调用scanClass方法
                if (entryName.startsWith(ScanSetting.ROUTER_CLASS_PACKAGE_NAME)) {
                    InputStream inputStream = file.getInputStream(jarEntry)
                    scanClass(inputStream)
                    inputStream.close()
                } else if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == entryName) {
                    // mark this jar file contains JDAnnotationCenter.class
                    // After the scan is complete, we will generate register code into this file
                    JDInitTransform.fileContainsInitClass = destFile
                }
            }
            file.close()
        }
    }

    static boolean shouldProcessPreDexJar(String path) {
        return !path.contains("com.android.support") && !path.contains("/android/m2repository")
    }

    static boolean shouldProcessClass(String entryName) {
        return entryName != null && entryName.startsWith(ScanSetting.ROUTER_CLASS_PACKAGE_NAME)
    }

    /**
     * scan class file
     * @param class file
     */
    static void scanClass(File file) {
        scanClass(new FileInputStream(file))
    }

    static void scanClass(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ScanClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        inputStream.close()
    }

    static class ScanClassVisitor extends ClassVisitor {

        ScanClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
            Logger.i('superName ==='+superName+"    interfaces   "+Arrays.asList(interfaces))
            JDInitTransform.registerList.each { ext ->
                if (ext.mySuperName && superName != null) {
//                    interfaces.each { itName ->
                        if (superName == ext.mySuperName) {
                            //fix repeated inject init code when Multi-channel packaging
                            //判断是否class实现了IRouteRoot接口
                            if (!ext.classList.contains(name)) {
                                ext.classList.add(name)
                            }
                        }
//                    }
                }
            }
        }
    }
}