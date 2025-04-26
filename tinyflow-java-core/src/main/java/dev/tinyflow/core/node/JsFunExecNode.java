package dev.tinyflow.core.node;

import com.agentsflex.core.chain.Chain;
import com.agentsflex.core.chain.node.CodeNode;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码功能
 *
 * @author <a href="mailto:wangqimeng03@meituan.com">wangqimeg03</a>
 * @date 2025/04/21 19:04
 */
public class JsFunExecNode extends CodeNode {
    private static final String funcWrapper = "function jsFunc(){%s};\njsFunc();";
    protected Map<String, Object> executeCode(String code, Chain chain) {
        ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("graal.js");
        if (engine == null) {
            throw new RuntimeException("未找到 GraalJS 引擎，请确认依赖配置");
        } else {
            Bindings bindings = engine.createBindings();
            bindings.put("polyglot.js.allowHostAccess", true);
            bindings.put("polyglot.js.allowHostClassLookup", true);
            Map<String, Object> parameterValues = chain.getParameterValues(this);
            if (parameterValues != null) {
                bindings.putAll(parameterValues);
            }
             code = String.format(funcWrapper, code);

            Map<String, Object> result = new HashMap();
            bindings.put("_chain", chain);
            bindings.put("_result", result);

            try {
                return (Map<String, Object>) engine.eval(code, bindings);
            } catch (ScriptException e) {
                throw new RuntimeException("GraalJS 执行失败", e);
            }
        }
    }

    public String toString() {
        return "JsExecNode{inwardEdges=" + this.inwardEdges + ", code='" + this.code + '\'' + ", description='" + this.description + '\'' + ", parameters=" + this.parameters + ", outputDefs=" + this.outputDefs + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", async=" + this.async + ", outwardEdges=" + this.outwardEdges + ", condition=" + this.condition + ", memory=" + this.memory + ", nodeStatus=" + this.nodeStatus + '}';
    }
}
