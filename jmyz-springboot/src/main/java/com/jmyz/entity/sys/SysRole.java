package com.jmyz.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色表")
public class SysRole extends Model<SysRole> {
    @TableId(value = "role_id", type = IdType.UUID)
    private String roleId;
    private String roleName;
    private String remark;
    @ApiModelProperty(value = "创建人ID", example = "1")
    private String createAccountId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
