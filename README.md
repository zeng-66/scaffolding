需要安装lombok和Mybatisplus Code Generator插件
安装好Mybatisplus Code Generator插件后按Ctrl+alt+0呼出
配置Entity超类 com.pro.sup.SuperEntity 去前缀by_
使用自定义模板
package ${entity.packageName};

import java.io.Serializable;
<#list entity.importClasses as importClass>
import ${importClass};
</#list>

/**
 * ${tableRemark}(${tableName})实体类
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
<#list entity.classAnnotationList as classAnnotation>
${classAnnotation}
</#list>
public class ${entity.name} ${entity.extendsStatement}implements Serializable {
    private static final long serialVersionUID = 1L;

<#list entity.fields as field>
    /**
     * ${field.comment}
     */
    ${field.annotations}private ${field.type} ${field.name};

</#list>

<#if !global.lombokEnabled>
<#list entity.fields as field>
    public ${field.type} ${field.getMethodName}() {
        return ${field.name};
    }

    public void ${field.setMethodName}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }

</#list>
</#if>
}