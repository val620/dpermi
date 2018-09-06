package com.dpermi.core;

import com.dpermi.dao.MethodObjectDao;
import com.dpermi.domain.MethodObject;
import com.dpermi.util.FileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by val620@126.com on 2018/7/12.
 */
@Component
public class Global {
    @Autowired
    private MethodObjectDao methodObjectDao;

    @PostConstruct
    public void applicationStart() {
        System.out.println("application start");
        initMethodPermission();
    }

    private void initMethodPermission() {
        Field field = null;
        try {
            field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            Vector<Class> classes = (Vector<Class>) field.get(this.getClass().getClassLoader());
            List<Class> cl = new ArrayList<>(classes);
            List<MethodObject> methodObjects = methodObjectDao.getMethodObjects();
            List<MethodObject> methodList=new ArrayList<>();

            Iterator<Class> itor = cl.iterator();
            String controller= FileConfig.getConfig("dpermi.controller");
            while (itor.hasNext()) {
                Class c = itor.next();
                String className = c.getName().toLowerCase();

                if (className.contains(controller) && c.getAnnotation(Permission.class) != null) {
//                if (c.getAnnotation(Permission.class) != null) {
                    System.out.println(" Class Name : " + className);
                    Method[] methods = c.getDeclaredMethods();
                    RequestMapping req = (RequestMapping) c.getAnnotation(RequestMapping.class);
                    String[] bath = req.path().length > 0 ? req.path() : req.value();
                    int order = 0;
                    for (Method m : methods) {
                        MethodObject mobj = new MethodObject();
                        Permission permission = m.getAnnotation(Permission.class);
                        if (permission != null) {
                            String methodId = className + "." + m.getName().toLowerCase();
                            RequestMapping rm = m.getAnnotation(RequestMapping.class);
                            String[] bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            mobj.setMethodName(permission.name());
                            mobj.setParentId(permission.parentId());
                            String url=bath[0] + bath2[0];
                            mobj.setUrl(url.toLowerCase());
                            mobj.setOrderNo(++order);
                            mobj.setMethodId(methodId);
                            methodList.add(mobj);

                            MethodObject match = null;
                            if(methodObjects!=null && methodObjects.size()>0) {
                                Optional<MethodObject> opt = methodObjects.stream().filter(x -> x.getMethodId().equals
                                        (methodId)).findFirst();
                                if (opt.isPresent()) match = opt.get();
                            }

                            if (match == null) { //还没导入数据库的新方法 methodObjects.stream().noneMatch(x->x.getMethodId()
                                // .equals(methodId))
                                methodObjectDao.insertMethodObject(mobj);
                            } else if (!match.getMethodName().equals(mobj.getMethodName()) || !match.getUrl().equals
                                    (mobj.getUrl()) || !match.getParentId().equals(mobj.getParentId())) {//已经导入数据库的方法，判断需不需要更新
                                mobj.setMethodObjectId(match.getMethodObjectId());
                                methodObjectDao.updateMethodObject(mobj);
                            }
                        }
                    }

                }
            }
            //删除数据库不加权限的方法
            for(MethodObject obj: methodObjects){
               if(methodList.stream().noneMatch(x->x.getMethodId().equals(obj.getMethodId()))){
                   methodObjectDao.deleteMethodObject(obj.getMethodObjectId());
               }
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 在web结束时执行
     */
    @PreDestroy
    public void applicationEnd() {
        System.out.println("application end");
    }
}
