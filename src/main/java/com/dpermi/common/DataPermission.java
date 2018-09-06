package com.dpermi.common;

import com.dpermi.dao.DataObjectDao;
import com.dpermi.domain.DataObject;
import com.dpermi.domain.PermissionObject;
import com.dpermi.domain.User;
import com.dpermi.util.TypeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by val620@126.com on 2018/7/2.
 */
@Component
public class DataPermission {

    @Autowired
    private DataObjectDao dataObjectDao;
    private static DataObjectDao dao;

    @PostConstruct
    public void init() {
        dao = this.dataObjectDao;
    }

    //根据类和属性获取有权限的属性值
    public <T> String[] getObjectIds(Class<T> tClass, String fieldName) {
//        System.out.print("Name: " + tClass.getName() + "; Type:" + tClass.getTypeName());
        String className = tClass.getTypeName();
        String[] ids = null;
        Integer dataType = dao.getDataType(className, fieldName);
        if (dataType != null)
            ids = getObjectIds(dataType.intValue());
        return ids;
    }

    //根据数据类型获取有权限的属性值
    public String[] getObjectIds(int dataType) {
        List<PermissionObject> permissionObjects = UserService.getPermission();
        String[] ids = null;
        if (permissionObjects != null && permissionObjects.size() > 0) {
            ids = permissionObjects.stream().filter(x -> x.getDataType() == dataType).map(x -> x.getObjectId()).toArray(String[]::new);
        }
        return ids;
    }

    //获取有权限的泛型属性值
    public <T> List<T> getObjectIds(int dataType, Class<T> eClass) throws Exception {
        String[] ids = getObjectIds(dataType);
        if (ids == null) return null;

        List<T> list = new ArrayList<T>();
        Object obj = null;
        for (String id : ids) {
            switch (eClass.getTypeName()) {
                case "java.lang.Integer":
                    obj = Integer.parseInt(id);
                    break;
                case "java.lang.Float":
                    obj = Float.parseFloat(id);
                    break;
                case "java.lang.Double":
                    obj = Double.parseDouble(id);
                    break;
                case "java.math.BigDecimal":
                    obj = new BigDecimal(id);
                    break;
                case "java.util.Date":
                    obj = TypeConvert.StringToDate(id);
                    break;
                default:
                    break;

            }
            list.add((T) obj);
        }
        return list;
    }

    //从源数据获取有权限的数据
    public <T> List<T> getAuthorizedData(List<T> source, Class<T> tClass) throws Exception {
        User user = UserService.getCurrentUser();
        if(user==null) return null;
        if(user.isAdmin()) return source;

        String className = tClass.getName();
        List<DataObject> dataObjects = dao.getDataTypes(className);
        if (dataObjects == null || dataObjects.size() == 0) return null;

        //得到该类下的约定类型值
        List<Integer> dataTypes = dataObjects.stream().map(x -> x.getDataType()).collect(Collectors.toList());

        List<PermissionObject> permissionObjects = UserService.getPermission();
        if (permissionObjects == null || permissionObjects.size() == 0) return null;

        //用户有权限的属性值
        List<String> ids = permissionObjects.stream().filter(x -> dataTypes.contains(x.getDataType())).map(x -> x.getObjectId())
                .collect(Collectors.toList());
        //得到该类下的约定属性名
        List<String> fieldNames = dataObjects.stream().map(x -> x.getFieldName()).collect(Collectors.toList());
        Field[] fields = tClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field f : fields) {
            if (fieldNames.contains(f.getName())) {
                f.setAccessible(true);//允许访问私有属性
                fieldList.add(f);//由约定属性名得到约定属性
            }
        }
        List<T> list = new ArrayList<T>();
        for (T t : source) {
            for (Field f : fieldList) {
                String value = f.get(t).toString();//源数据关联字段属性值
                if (ids.contains(value) && !list.contains(t))
                    list.add(t);
            }
        }

        return list;

    }

    //从源数据获取有权限的列数据
    public <T> List<T> getAuthorizedColumnData(List<T> source, Class<T> tClass, int dataType) throws Exception {

        List<PermissionObject> permissionObjects = UserService.getPermission();
        //用户有权限的属性
        List<String> ids = permissionObjects.stream().filter(x -> x.getDataType() == dataType).map(x -> x.getObjectId())
                .collect(Collectors.toList());

        Field[] fields = tClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field f : fields) {
            if (ids.contains(f.getName())) {
                f.setAccessible(true);//允许访问私有属性
                fieldList.add(f);//由约定属性名得到约定属性
            }
        }
        List<T> list = new ArrayList<T>();
        for (T t : source) {
            T newObj = tClass.newInstance();
            for (Field f : fieldList) {
                Object value = f.get(t);//源数据关联字段属性值
                f.set(newObj, value);
            }
            list.add(newObj);
        }

        return list;

    }


}
