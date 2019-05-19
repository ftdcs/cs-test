package me.chen.core;

import lombok.extern.slf4j.Slf4j;
import me.chen.annotation.BindEntity;
import me.chen.annotation.BindValue;
import me.chen.annotation.Describe;
import me.chen.annotation.TestCase;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;

/**
 * @Author: ftdcs
 * @Date: 2019/05/16 0015:18:46
 * @Version 1.0
 */
@Configuration
@Slf4j
public class Reflex {

    @Autowired
    ApplicationContext context;

    Map<String,String> methodNameBeanList = new HashMap<>();

    Map<String,Method> methodNameMethodList = new HashMap<>();

    List<ClassDescribe> classDescribeList = new ArrayList<>();

    public Object invoke(String methodName) throws ReflectiveOperationException {
        String beanName = methodNameBeanList.get(methodName);
        Method method = methodNameMethodList.get(methodName);
        Object bean = context.getBean(beanName);
        StringBuilder sb = StringBuilderHolder.newStringBuilder();
        Object[] args = RequestParamsHolder.resolveParams(method);
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(bean,args);
        long endTime = System.currentTimeMillis();
        log.info("调用类: {} ,方法: {} ,入参: {}, 耗时: {}毫秒 ,返回值: {}",beanName,methodName,args,(endTime-startTime),result);
        if(result == null){
            result = sb.toString();
        }
        return result;
    }

    public void scanClass() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(TestCase.class);
        beansWithAnnotation.forEach((k,v)->{
            Class clazz = null;
            if(AopUtils.isAopProxy(v) || AopUtils.isCglibProxy(v) || AopUtils.isJdkDynamicProxy(v)){
                clazz = AopUtils.getTargetClass(v);
            }else{
                clazz = v.getClass();
            }
            TestCase testCase = (TestCase) clazz.getAnnotation(TestCase.class);
            ClassDescribe classDescr = new ClassDescribe(testCase.name(),testCase.desc());
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if(!Modifier.isPublic(method.getModifiers())){
                    continue;
                }
                Describe describe = method.getAnnotation(Describe.class);
                String desc = describe==null?"未设置":describe.desc();
                String methodName = method.getName();
                while(methodNameBeanList.containsKey(methodName)){
                    methodName += "_1";
                }
                MethodDescribe methodDescribe = new MethodDescribe(methodName,desc);
                Parameter[] parameters = method.getParameters();
                String[] names = new String[parameters.length];
                Class<?>[] types = new Class[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    names[i] = parameters[i].getName();
                    types[i] = parameters[i].getType();
                    BindEntity bindEntity = parameters[i].getAnnotation(BindEntity.class);
                    if(bindEntity!=null){
                        Class type = parameters[i].getType();
                        String name = Optional.ofNullable(bindEntity.value()).filter(s -> !s.equals("")).orElse(type.getSimpleName());
                        Field[] declaredFields = type.getDeclaredFields();
                        Map<String,EntityDescribe> fieldMap = new HashMap<>();
                        for (Field declaredField : declaredFields) {
                            EntityDescribe entityDescribe = new EntityDescribe(name+"."+declaredField.getName(),"",declaredField.getType().getSimpleName());
                            fieldMap.put(declaredField.getName(),entityDescribe);
                        }
                        methodDescribe.putEntityList(name,fieldMap);
                    }
                }
                methodDescribe.setNames(names);
                methodDescribe.setTypes(types);
                BindValue bindValue = method.getAnnotation(BindValue.class);
                if(bindValue!=null){
                    methodDescribe.setExtNames(bindValue.names());
                    methodDescribe.setExtTypes(bindValue.types());
                }
                classDescr.putMethod(methodDescribe);
                methodNameBeanList.put(methodName,k);
                methodNameMethodList.put(methodName,method);
            }
            classDescribeList.add(classDescr);
        });
    }



    public class ClassDescribe{

        private String name;
        private String desc;
        protected List<MethodDescribe> methodDescribeList = new ArrayList<>();

        public ClassDescribe(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        protected void putMethod(MethodDescribe methodDescribe){
            methodDescribeList.add(methodDescribe);
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public List<MethodDescribe> getMethodDescribeList() {
            return methodDescribeList;
        }
    }

    public class MethodDescribe{
        /**
         * method name
         */
        private String name;
        private String desc;
        private String[] names;
        private String[] types;

        private String[] extNames;
        private String[] extTypes;

        private Map<String,Map<String,EntityDescribe>> entityMap = new HashMap<>();


        public MethodDescribe(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        protected void setNames(String[] names) {
            this.names = names;
        }

        protected void setTypes(Class[] types) {
            String[] typesName = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                typesName[i] = types[i].getSimpleName();
            }
            this.types = typesName;
        }

        protected void setExtNames(String[] extNames) {
            this.extNames = extNames;
        }

        protected void setExtTypes(Class[] extTypes) {
            String[] typesName = new String[types.length];
            for (int i = 0; i < extTypes.length; i++) {
                typesName[i] = extTypes[i].getSimpleName();
            }
            this.extTypes = typesName;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public String[] getNames() {
            return names;
        }

        public String[] getTypes() {
            return types;
        }

        public String[] getExtNames() {
            return extNames;
        }

        public String[] getExtTypes() {
            return extTypes;
        }

        public Map<String, Map<String,EntityDescribe>> getEntityMap() {
            return entityMap;
        }

        public void putEntityList(String entityName,Map<String,EntityDescribe> fieldList){
            entityMap.put(entityName,fieldList);
        }
    }

    public class EntityDescribe{
        private String fieldName;
        private String fieldDesc;
        private String fieldType;

        public EntityDescribe(String fieldName, String fieldDesc, String fieldType) {
            this.fieldName = fieldName;
            this.fieldDesc = fieldDesc;
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldDesc() {
            return fieldDesc;
        }

        public String getFieldType() {
            return fieldType;
        }
    }


    public List<ClassDescribe> getClassDescribeList() {
        return classDescribeList;
    }
}
