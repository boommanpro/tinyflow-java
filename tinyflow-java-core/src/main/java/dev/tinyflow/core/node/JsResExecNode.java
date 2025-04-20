package dev.tinyflow.core.node;

import com.agentsflex.core.chain.Chain;
import com.agentsflex.core.chain.node.CodeNode;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class JsResExecNode extends CodeNode {
    public JsResExecNode() {
    }

    protected Map<String, Object> executeCode(String code, Chain chain) {
        code = String.format("function codeFunc() {%s};codeFunc();", code);
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
