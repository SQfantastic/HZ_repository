package utils;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {
    private Integer id;
    @JSONField(name="parentId")
    private Integer pid;
    private String icon;
    private String title;
    private String href;
    private Boolean spread;

    private List<TreeNode> children=new ArrayList<>();

    private String checkArr="0";


    /**
     * 构建dtree组件
     * @param id        编号
     * @param pid       父级编号
     * @param title     名称
     * @param spread    展开状态
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }


    //用于角色分配菜单时的复选树
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }


    //用于首页左边导航
    public TreeNode(Integer id, Integer pid, String icon, String title, String href, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.icon = icon;
        this.title = title;
        this.href = href;
        this.spread = spread;
    }
}
