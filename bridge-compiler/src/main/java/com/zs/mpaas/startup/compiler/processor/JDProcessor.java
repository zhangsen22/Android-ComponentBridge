//package com.zs.mpaas.startup.compiler.processor;
//
//import static com.zs.mpaas.startup.compiler.utils.Consts.Annotation_MethodCreateProtocol;
//import static com.zs.mpaas.startup.compiler.utils.Consts.Annotation_MethodGetProtocol;
//import static com.zs.mpaas.startup.compiler.utils.Consts.IROUTE_GROUP;
//import static com.zs.mpaas.startup.compiler.utils.Consts.IROUTE_GROUP_CLASSNAME;
//import static com.zs.mpaas.startup.compiler.utils.Consts.KEY_MODULE_NAME;
//import static com.zs.mpaas.startup.compiler.utils.Consts.PACKAGE_OF_GENERATE_FILE;
//import static com.zs.mpaas.startup.compiler.utils.Consts.Protocol_MethodName;
//import static com.zs.mpaas.startup.compiler.utils.Consts.WARNING_TIPS;
//
//import androidx.annotation.NonNull;
//
//import com.example.zhujie.ComponentBridge;
//import com.google.auto.service.AutoService;
//import com.squareup.javapoet.ClassName;
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.MethodSpec;
//import com.squareup.javapoet.ParameterSpec;
//import com.squareup.javapoet.ParameterizedTypeName;
//import com.squareup.javapoet.TypeName;
//import com.squareup.javapoet.TypeSpec;
//import com.zs.mpaas.startup.compiler.entity.NodeInfo;
//import com.zs.mpaas.startup.compiler.utils.Logger;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.Processor;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.ElementKind;
//import javax.lang.model.element.Modifier;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.type.DeclaredType;
//import javax.lang.model.type.MirroredTypeException;
//import javax.lang.model.type.TypeMirror;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
///**
// * ******************************
// * 项目名称:MyAnnotation
// *
// * @Author zhangsen
// * 邮箱:zhangsen839705693@163.com
// * 创建时间:2021    8:48 下午
// * 说明:
// * ******************************
// */
//@AutoService(Processor.class)
//public class JDProcessor extends AbstractProcessor {
//
//    /**
//     * java源文件操作相关类，主要用于生成java源文件
//     */
//    private Filer mFiler;
//    /**
//     * 注解类型工具类，主要用于后续生成java源文件使用
//     * 类为TypeElement，变量为VariableElement，方法为ExecuteableElement
//     */
//    private Elements mElementsUtils;
//    /**
//     * 日志打印，类似于log,可用于输出错误信息
//     */
//    private Logger logger;
//
//    /**
//     * 类信息工具类
//     */
//    private Types mTypeUtils;
//
//    /**
//     * 节点信息缓存
//     */
//    private Map<String, List<NodeInfo>> mCache = new HashMap<>();
//
//    /**
//     * 初始化，主要用于初始化各个变量
//     * 注解处理器的初始化阶段，可以通过ProcessingEnvironment来获取一些帮助我们来处理注解的工具类
//     *
//     * @param processingEnv
//     */
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        mTypeUtils = processingEnv.getTypeUtils();
//        mFiler = processingEnv.getFiler();
//        mElementsUtils = processingEnv.getElementUtils();
//        logger = new Logger(processingEnv.getMessager());
//    }
//
//    /**
//     * 支持的注解类型
//     * 指明有哪些注解需要被扫描到，返回注解的全路径（包名+类名）
//     *
//     * @return
//     */
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return Collections.singleton(ComponentBridge.class.getCanonicalName());
//    }
//
//    @Override
//    public Set<String> getSupportedOptions() {
//        return new HashSet<String>() {{
//            this.add(KEY_MODULE_NAME);
//        }};
//    }
//
//    /**
//     * 支持的版本
//     * 用来指定当前正在使用的Java版本，一般返回SourceVersion.latestSupported()表示最新的java版本即可
//     *
//     * @return
//     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    /**
//     * 1.搜集信息
//     * 2.生成java源文件
//     * 核心方法，注解的处理和生成代码都是在这个方法中完成
//     *
//     * @param annotations
//     * @param roundEnv
//     * @return
//     */
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        if (annotations == null || annotations.isEmpty()) return false;
//
//        //获取Bind注解类型的元素，这里是类类型TypeElement
//        Set<? extends Element> bindElement = roundEnv.getElementsAnnotatedWith(ComponentBridge.class);
//        if (bindElement == null || bindElement.isEmpty()) return false;
//
//        try {
//            generateCode(bindElement);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * @param elements
//     */
//    private void generateCode(Set<? extends Element> elements) throws IOException {
//
//        for (Element element : elements) {
//            // 判断元素的类型为Class
//            if (element.getKind() == ElementKind.CLASS) {
//                //由于是在类上注解，那么获取TypeElement
//                TypeElement typeElement = (TypeElement) element;
//
//
//                // 获取包路径  kt.dongdong.myannotation
//                String packageName = mElementsUtils.getPackageOf(typeElement).getQualifiedName().toString();
//                logger.warning(packageName);
//
//                //获取类路径  kt.dongdong.myannotation.AppliationA
//                String classPathName = typeElement.getQualifiedName().toString();
//                logger.warning(classPathName);
//
//                //获取用于生成的类名  AppliationA
//                String className = getClassName(typeElement, packageName);
//                logger.warning(className);
//
//                //获取注解值
//                ComponentBridge jdStartUp = typeElement.getAnnotation(ComponentBridge.class);
//
//
//                String protocolQualifiedClassName;
//                String protocolSimpleClassName;
//                try {  // 该类已经被编译
//                    Class<?> clazz = jdStartUp.protocol();
//                    protocolQualifiedClassName = clazz.getCanonicalName();
//                    protocolSimpleClassName = clazz.getSimpleName();
//                } catch (MirroredTypeException mte) {// 该类未被编译
//                    DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
//                    TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
//                    protocolQualifiedClassName = classTypeElement.getQualifiedName().toString();
//                    protocolSimpleClassName = classTypeElement.getSimpleName().toString();
//                }
//
//                int index = protocolQualifiedClassName.lastIndexOf(".");
//                String protocolPackageName = protocolQualifiedClassName.substring(0, index);
//
//                System.out.println("protocolQualifiedClassName:" + protocolQualifiedClassName+"  protocolPackageName  : "+protocolPackageName+"   protocolSimpleClassName  :"+protocolSimpleClassName);
//
//                // 缓存KEY
//                String key = classPathName;
////                logger.warning(key);
//
//                // 缓存节点信息
//                List<NodeInfo> nodeInfos = mCache.get(key);
//                if (nodeInfos == null) {
//                    nodeInfos = new ArrayList<>();
//                    nodeInfos.add(new NodeInfo(packageName, className, classPathName, "appId", false,protocolPackageName,protocolSimpleClassName));
//                        // 缓存
//                    mCache.put(key, nodeInfos);
//                } else {
//                    nodeInfos.add(new NodeInfo(packageName, className, classPathName, "appId", false,protocolPackageName,protocolSimpleClassName));
//                }
//            }
//        }
//
//        // 判断临时缓存是否不为空
//        if (!mCache.isEmpty()) {
//            // 遍历临时缓存文件
//            for (Map.Entry<String, List<NodeInfo>> stringListEntry : mCache.entrySet()) {
//                try {
//                    // 创建文件
//                    createFile(stringListEntry.getValue());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 创建文件，自动生成代码
//     */
//    private void createFile(List<NodeInfo> infos) throws IOException {
//        NodeInfo info = infos.get(0);
//        logger.warning("createFile");
//
//        // 方法参数
//        ParameterSpec isDebugParamSpec = ParameterSpec.builder(TypeName.BOOLEAN, "isDebug").build();
//
//        ClassName context = ClassName.get("android.content", "Context");
//        ParameterSpec savedInstanceState = ParameterSpec.builder(context, "context").build();
//        ClassName type_AbProtocolFactory = ClassName.get(IROUTE_GROUP, IROUTE_GROUP_CLASSNAME);
//
//        //return
//        ClassName returnToCreat = ClassName.get(info.getPackageName(), info.getClassName());
//
//        ClassName clazzProtocol = ClassName.get(info.getProtocolPackageName(), info.getProtocolSimpleClassName());
//
//
//        //生成方法 createProtocol
//        MethodSpec.Builder methodCreate = MethodSpec
//                .methodBuilder(Annotation_MethodCreateProtocol)
//                .addAnnotation(NonNull.class)
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(savedInstanceState)
////                .addParameter(appIdParamSpec)
//                .addParameter(isDebugParamSpec)
//                .returns(clazzProtocol);
//
//
//        // 给create方法添加代码块
//        methodCreate.addStatement("$L $L = new $L();\n$L.$L($L,$L)",
//                info.getProtocolSimpleClassName(),
//                info.getProtocolSimpleClassName().toLowerCase(),
//                returnToCreat,
//                info.getProtocolSimpleClassName().toLowerCase(),
//                Protocol_MethodName,
//                savedInstanceState.name,
//                isDebugParamSpec.name)
//                .addStatement("return $L", info.getProtocolSimpleClassName().toLowerCase());
//
//
//        /**
//         * List<Class<? extends IRouteGroup<?>>>
//         */
//        ParameterizedTypeName inputMdddapTypeOfRoot = ParameterizedTypeName.get(
//                ClassName.get(Class.class),clazzProtocol);
//
//
//        //生成方法 getProtocol
//        MethodSpec.Builder methodGetProtocol = MethodSpec
//                .methodBuilder(Annotation_MethodGetProtocol)
//                .addAnnotation(NonNull.class)
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .returns(inputMdddapTypeOfRoot);
//
//
//        // 给getProtocol方法添加代码块
//        methodGetProtocol.addStatement("return $L$L", info.getProtocolSimpleClassName(),".class");
//
//
//        //生成的类xx
//        TypeSpec type = TypeSpec
//                .classBuilder(ClassName.get(info.getPackageName(), "JDStartUp$$" + info.getClassName()))
//                .addJavadoc(WARNING_TIPS)
//                .addModifiers(Modifier.PUBLIC)
//                .superclass(ParameterizedTypeName.get(type_AbProtocolFactory, clazzProtocol))
//                .addMethod(methodCreate.build())
//                .addMethod(methodGetProtocol.build())
//                .build();
//
//        //生成文件
//        JavaFile.builder(PACKAGE_OF_GENERATE_FILE, type).build().writeTo(mFiler);
//    }
//
//    /**
//     * 根据type和package获取类名
//     *
//     * @param type
//     * @param packageName
//     * @return
//     */
//    private static String getClassName(TypeElement type, String packageName) {
//        int packageLen = packageName.length() + 1;
//        return type.getQualifiedName().toString().substring(packageLen)
//                .replace('.', '$');
//    }
//}
