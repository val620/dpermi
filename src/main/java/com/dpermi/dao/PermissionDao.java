package com.dpermi.dao;

import com.dpermi.domain.Menu;
import com.dpermi.domain.MethodObject;
import com.dpermi.domain.TreeNode;
import com.dpermi.mapper.MenuMapper;
import com.dpermi.mapper.MethodObjectMapper;
import com.dpermi.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Component
public class PermissionDao {
    @Autowired
    private MethodObjectMapper methodObjectMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;

    private List<TreeNode> menuTree;

    //得到权限树
    public List<TreeNode> getMenuPermission(String roleId) {
        List<Menu> menus = menuMapper.getMenus();
        if (menus == null || menus.size() == 0) return null;

        String[] objIds=roleMapper.getObjIds(0,roleId);
        List<TreeNode> menuNodes = menuToTreeNode(menus,objIds);
        List<MethodObject> methodObjects = methodObjectMapper.getMethodObjects();
        if (methodObjects != null && methodObjects.size() > 0) {
            List<TreeNode> methodNodes = methodObjectToTreeNode(methodObjects, menus,objIds);
            menuNodes.addAll(methodNodes);
        }
        List<TreeNode> root = menuNodes.parallelStream().filter(x -> x.getParent() == null || x.getParent().equals("")).collect(Collectors.toList());
        if (root != null && root.size() > 0) {
            menuTree = new ArrayList<>();
            root.forEach(x -> {
                appendChildren(menuNodes, x);
                menuTree.add(x);
            });
        }

        return menuTree;
    }

    //为根节点找到所有子节点
    private void appendChildren(List<TreeNode> menuNodes, TreeNode node) {
        List<TreeNode> children = menuNodes.parallelStream().filter(x -> x.getParent() != null && x.getParent().equals(node.getId())).collect(Collectors.toList());
        if (children == null || children.size() == 0) {
            node.setIcon("jstree-file");
            return;
        }
        node.setChildren(children);
        children.forEach(x -> appendChildren(menuNodes, x));
    }

    private List<TreeNode> menuToTreeNode(List<Menu> menus,String[] objIds) {
        if (menus == null || menus.size() == 0) return null;

        List<TreeNode> menuNodes = new ArrayList<>();
        menus.forEach(x -> {
            TreeNode obj = new TreeNode();
            obj.setId(x.getMenuId());
            obj.setText(x.getMenuName());
            obj.setParent(x.getParentId());
            if(objIds!=null && objIds.length>0){
                List<String> idList = Arrays.asList(objIds);
                if(idList.contains(x.getMenuId())) {
                    obj.setState(obj.new State(false,false,true));
                }
            }
            menuNodes.add(obj);
        });
        return menuNodes;
    }

    private List<TreeNode> methodObjectToTreeNode(List<MethodObject> methodObjects, List<Menu> menus,String[] objIds) {
        if (methodObjects == null || methodObjects.size() == 0) return null;

        List<TreeNode> methodNodes = new ArrayList<>();
        methodObjects.forEach(x -> {
            TreeNode obj = new TreeNode();
            obj.setId(x.getMethodObjectId());
            obj.setText(x.getMethodName());
            String parentId = null;
            if (menus.parallelStream().anyMatch(m -> m.getMenuCode().equals(x.getParentId()))) {
                parentId = menus.parallelStream().filter(m -> m.getMenuCode().equals(x.getParentId())).findFirst().get().getMenuId();
            }
            obj.setParent(parentId);

            if(objIds!=null && objIds.length>0){
                List<String> idList = Arrays.asList(objIds);
                if(idList.contains(x.getMethodObjectId())) {
                    obj.setState(obj.new State(false,false,true));
                }
            }
            methodNodes.add(obj);
        });
        return methodNodes;
    }

}
