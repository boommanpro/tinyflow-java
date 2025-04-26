package dev.tinyflow.core.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码功能
 *
 * @author <a href="mailto:wangqimeng03@meituan.com">wangqimeg03</a>
 * @date 2025/04/25 13:48
 */
@NoArgsConstructor
@Data
public class ConditionGroup {
    @JsonProperty("condition")
    private ConditionDTO condition;

    @NoArgsConstructor
    @Data
    public static class ConditionDTO {
        @JsonProperty("logic")
        private Integer logic;
        @JsonProperty("description")
        private String description;
        @JsonProperty("conditions")
        private List<ConditionRule> conditions;
    }
}
