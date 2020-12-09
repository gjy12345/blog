package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class MenuModel
 * 左侧菜单的实体
 */
public class MenuModel extends BaseWeAdminModel<MenuModel.MenuData>{


    public static class MenuData{
        private Integer id;
        private String name;
        private String icon;
        private String url;
        private List<MenuData> children;
        @Alias("parent_id")
        private transient Integer parentId;

        public Integer getParentId() {
            return parentId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<MenuData> getChildren() {
            return children;
        }

        public void setChildren(List<MenuData> children) {
            this.children = children;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }
    }

    public static MenuModel buildMenuModel(List<MenuData> data){
        MenuModel menuModel=new MenuModel();
        menuModel.setData(data);
        menuModel.setMsg("ok");
        menuModel.setStatus(0);
        return menuModel;
    }
}
