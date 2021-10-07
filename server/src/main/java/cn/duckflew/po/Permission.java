package cn.duckflew.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 后台权限表
 * </p>
 *
 * @author duckflew
 * @since 2021-10-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="后台权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自定id,主要供前端展示权限列表分类排序使用.")
    private Integer id;

    @ApiModelProperty(value = "归属菜单,前端判断并展示菜单使用,")
    @TableField("menu_code")
    private String menuCode;

    @ApiModelProperty(value = "菜单的中文释义")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "权限的代码/通配符,对应代码中@RequiresPermissions 的value")
    @TableField("permission_code")
    private String permissionCode;

    @ApiModelProperty(value = "本权限的中文释义")
    @TableField("permission_name")
    private String permissionName;

    @ApiModelProperty(value = "是否本菜单必选权限, 1.必选 2非必选 通常是'列表'权限是必选")
    @TableField("required_permission")
    private Boolean requiredPermission;


}
