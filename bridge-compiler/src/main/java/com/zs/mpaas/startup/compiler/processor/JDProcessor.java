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
// * ????????????:MyAnnotation
// *
// * @Author zhangsen
// * ??????:zhangsen839705693@163.com
// * ????????????:2021    8:48 ??????
// * ??????:
// * ******************************
// */
//@AutoService(Processor.class)
//public class JDProcessor extends AbstractProcessor {
//
//    /**
//     * java?????????????????????????????????????????????java?????????
//     */
//    private Filer mFiler;
//    /**
//     * ????????????????????????????????????????????????java???????????????
//     * ??????TypeElement????????????VariableElement????????????ExecuteableElement
//     */
//    private Elements mElementsUtils;
//    /**
//     * ????????????????????????log,???????????????????????????
//     */
//    private Logger logger;
//
//    /**
//     * ??????????????????
//     */
//    private Types mTypeUtils;
//
//    /**
//     * ??????????????????
//     */
//    private Map<String, List<NodeInfo>> mCache = new HashMap<>();
//
//    /**
//     * ?????????????????????????????????????????????
//     * ????????????????????????????????????????????????ProcessingEnvironment??????????????????????????????????????????????????????
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
//     * ?????????????????????
//     * ???????????????????????????????????????????????????????????????????????????+?????????
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
//     * ???????????????
//     * ?????????????????????????????????Java?????????????????????SourceVersion.latestSupported()???????????????java????????????
//     *
//     * @return
//     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    /**
//     * 1.????????????
//     * 2.??????java?????????
//     * ???????????????????????????????????????????????????????????????????????????
//     *
//     * @param annotations
//     * @param roundEnv
//     * @return
//     */
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        if (annotations == null || annotations.isEmpty()) return false;
//
//        //??????Bind??????????????????????????????????????????TypeElement
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
//            // ????????????????????????Class
//            if (element.getKind() == ElementKind.CLASS) {
//                //???????????????????????????????????????TypeElement
//                TypeElement typeElement = (TypeElement) element;
//
//
//                // ???????????????  kt.dongdong.myannotation
//                String packageName = mElementsUtils.getPackageOf(typeElement).getQualifiedName().toString();
//                logger.warning(packageName);
//
//                //???????????????  kt.dongdong.myannotation.AppliationA
//                String classPathName = typeElement.getQualifiedName().toString();
//                logger.warning(classPathName);
//
//                //???????????????????????????  AppliationA
//                String className = getClassName(typeElement, packageName);
//                logger.warning(className);
//
//                //???????????????
//                ComponentBridge jdStartUp = typeElement.getAnnotation(ComponentBridge.class);
//
//
//                String protocolQualifiedClassName;
//                String protocolSimpleClassName;
//                try {  // ?????????????????????
//                    Class<?> clazz = jdStartUp.protocol();
//                    protocolQualifiedClassName = clazz.getCanonicalName();
//                    protocolSimpleClassName = clazz.getSimpleName();
//                } catch (MirroredTypeException mte) {// ??????????????????
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
//                // ??????KEY
//                String key = classPathName;
////                logger.warning(key);
//
//                // ??????????????????
//                List<NodeInfo> nodeInfos = mCache.get(key);
//                if (nodeInfos == null) {
//                    nodeInfos = new ArrayList<>();
//                    nodeInfos.add(new NodeInfo(packageName, className, classPathName, "appId", false,protocolPackageName,protocolSimpleClassName));
//                        // ??????
//                    mCache.put(key, nodeInfos);
//                } else {
//                    nodeInfos.add(new NodeInfo(packageName, className, classPathName, "appId", false,protocolPackageName,protocolSimpleClassName));
//                }
//            }
//        }
//
//        // ?????????????????????????????????
//        if (!mCache.isEmpty()) {
//            // ????????????????????????
//            for (Map.Entry<String, List<NodeInfo>> stringListEntry : mCache.entrySet()) {
//                try {
//                    // ????????????
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
//     * ?????????????????????????????????
//     */
//    private void createFile(List<NodeInfo> infos) throws IOException {
//        NodeInfo info = infos.get(0);
//        logger.warning("createFile");
//
//        // ????????????
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
//        //???????????? createProtocol
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
//        // ???create?????????????????????
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
//        //???????????? getProtocol
//        MethodSpec.Builder methodGetProtocol = MethodSpec
//                .methodBuilder(Annotation_MethodGetProtocol)
//                .addAnnotation(NonNull.class)
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .returns(inputMdddapTypeOfRoot);
//
//
//        // ???getProtocol?????????????????????
//        methodGetProtocol.addStatement("return $L$L", info.getProtocolSimpleClassName(),".class");
//
//
//        //????????????xx
//        TypeSpec type = TypeSpec
//                .classBuilder(ClassName.get(info.getPackageName(), "JDStartUp$$" + info.getClassName()))
//                .addJavadoc(WARNING_TIPS)
//                .addModifiers(Modifier.PUBLIC)
//                .superclass(ParameterizedTypeName.get(type_AbProtocolFactory, clazzProtocol))
//                .addMethod(methodCreate.build())
//                .addMethod(methodGetProtocol.build())
//                .build();
//
//        //????????????
//        JavaFile.builder(PACKAGE_OF_GENERATE_FILE, type).build().writeTo(mFiler);
//    }
//
//    /**
//     * ??????type???package????????????
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
