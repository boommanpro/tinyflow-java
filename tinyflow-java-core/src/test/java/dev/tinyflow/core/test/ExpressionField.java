package dev.tinyflow.core.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 代码功能
 *
 * @author <a href="mailto:wangqimeng03@meituan.com">wangqimeg03</a>
 * @date 2025/04/25 13:49
 */
@Data
public class ExpressionField {
    @JsonProperty("type")
    private String type;
    @JsonProperty("content")
    private String content;
}
