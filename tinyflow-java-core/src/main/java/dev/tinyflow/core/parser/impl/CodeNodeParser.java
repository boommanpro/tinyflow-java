/**
 * Copyright (c) 2025-2026, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.tinyflow.core.parser.impl;

import com.agentsflex.chain.node.GroovyExecNode;
import com.agentsflex.chain.node.JsExecNode;
import com.agentsflex.chain.node.QLExpressExecNode;
import com.agentsflex.core.chain.ChainNode;
import com.agentsflex.core.chain.Parameter;
import com.agentsflex.core.chain.node.BaseNode;
import com.agentsflex.core.chain.node.CodeNode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dev.tinyflow.core.Tinyflow;
import dev.tinyflow.core.node.JsFunExecNode;
import dev.tinyflow.core.parser.BaseNodeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeNodeParser extends BaseNodeParser {

    @Override
    public ChainNode parse(JSONObject nodeJSONObject, Tinyflow tinyflow) {

        JSONObject data = getData(nodeJSONObject);
        String engine = data.getJSONObject("config").getString("language");
        String code = data.getJSONObject("config").getString("code");
        CodeNode codeNode;
        switch (engine) {
            case "groovy":
                codeNode = new GroovyExecNode();
                break;
            case "js":
            case "javascript":
                codeNode = new JsExecNode();
                break;
            case "javaScriptFunc":
                codeNode = new JsFunExecNode();
                break;
            default:
                codeNode = new QLExpressExecNode();
                break;
        }

        codeNode.setCode(code);

        addParameters(codeNode, data);
        addOutputDefs(codeNode, data);

        return codeNode;
    }


    public void addParameters(BaseNode node, JSONObject data) {
        List<Parameter> inputParameters = getParameters(data, "inputs");
        node.setParameters(inputParameters);
    }

    // 添加新的方法来处理参数
    public List<Parameter> getParameters(JSONObject data, String key) {
        List<Parameter> parameters = new ArrayList<>();

        // 获取 inputs 数组
        JSONObject properties = data.getJSONObject(key).getJSONObject("properties");
        if (properties == null) {
            return parameters;
        }
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String name = entry.getKey();
            ;

            Parameter parameter = new Parameter();
            parameter.setName(name);
            // 可以根据需要设置其他参数属性
            // parameter.setValue(input.getJSONObject("value").getString("content"));

            parameters.add(parameter);
        }
        return parameters;
    }
}
