package com.pro.sup;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SuperEntity<T> extends Model<SuperEntity<Model<?>>> {

    /**
     * id
     */
    @ApiModelProperty("id")
    @TableId(type= IdType.AUTO)
    protected Long id;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modifyTime;
    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    @TableField(fill = FieldFill.INSERT)
    protected Long createId;
    /**
     * 修改人id
     */
    @ApiModelProperty("修改人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long modifyId;
    /**
     * 逻辑删除
     */
    @ApiModelProperty("逻辑删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Boolean isDelete;
}
