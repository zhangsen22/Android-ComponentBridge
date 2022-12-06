package com.zs.npm.impl.register.core

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import com.zs.npm.impl.register.utils.Logger


/**
 * ******************************
 * 项目名称:JDAnnotationGradlePluginMoudle
 *
 * @Author zhangsen
 * 邮箱:zhangsen839705693@163.com
 * 创建时间:2021    1:17 下午
 * 说明:
 * ******************************
 */
public class JDInitTransform extends Transform {

    Project project
    static ArrayList<ScanSetting> registerList
    static File fileContainsInitClass;

    JDInitTransform(Project project) {
        this.project = project
    }

    /**
     * 转换器的名字
     * @return
     */
    @Override
    public String getName() {
        return ScanSetting.PLUGIN_NAME
    }

    /**
     * 返回转换器需要消费的数据类型。我们需要处理所有的 class 内容
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     *  返回转换器的作用域，即处理范围 全项目 jar
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     *  不支持增量
     * @return
     */
    @Override
    public boolean isIncremental() {
        return false
    }

    /**
     *  实现转换逻辑
     * @param context
     * @param inputs
     * @param referencedInputs
     * @param outputProvider
     * @param isIncremental
     * @throws IOException
     * @throws TransformException
     * @throws InterruptedException
     */
    @Override
    public void transform(Context context, Collection<TransformInput> inputs
                          , Collection<TransformInput> referencedInputs
                          , TransformOutputProvider outputProvider
                          , boolean isIncremental) throws IOException, TransformException, InterruptedException {

        Logger.i("Start scan register info in jar file.");

        long startTime = System.currentTimeMillis();
        boolean leftSlash = File.separator == '/'

        // 由于不支持增量编译，清空 OutputProvider 的内容
        if (!isIncremental){
            outputProvider.deleteAll()
        }

        inputs.each { TransformInput input ->

            // 处理 jar 输入
            input.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.name
                // rename jar files
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }

                // TODO 编辑 class 文件，添加日志打印逻辑
                // 有输入进来就必须将其输出，否则会出现类缺失的问题，
                // 无论是否经过转换，我们都需要将输入目录复制到目标目录
                File src = jarInput.file
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                //scan jar file to find classes
                if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                    ScanUtil.scanJar(src, dest)
                }
                FileUtils.copyFile(src, dest)

            }
            // 处理目录输入
            input.directoryInputs.each { DirectoryInput directoryInput ->
                // TODO 编辑 class 文件，添加日志打印逻辑
                // 有输入进来就必须将其输出，否则会出现类缺失的问题，
                // 无论是否经过转换，我们都需要将输入目录复制到目标目录
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }
                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
                        ScanUtil.scanClass(file)
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        Logger.i('Scan finish, current cost time ' + (System.currentTimeMillis() - startTime) + "ms")

        if (fileContainsInitClass) {
            registerList.each { ext ->
                    Logger.i('Insert register code to file ' + fileContainsInitClass.absolutePath)

                if (ext.classList.isEmpty()) {
                    Logger.e("No class implements found for superclass:" + ext.mySuperName)
                } else {
                    ext.classList.each {
                        Logger.i(it)
                    }
                    JDInitCodeGenerator.insertInitCodeTo(ext)
                }
            }
        }

        Logger.i("Generate code finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
    }
}
