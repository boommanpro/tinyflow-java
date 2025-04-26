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

import com.agentsflex.core.chain.ChainNode;
import com.agentsflex.core.chain.Parameter;
import com.agentsflex.core.chain.node.BaseNode;
import com.agentsflex.core.chain.node.StartNode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dev.tinyflow.core.Tinyflow;
import dev.tinyflow.core.parser.BaseNodeParser;

import java.util.ArrayList;
import java.util.List;

public class StartNodeParser extends BaseNodeParser {

    @Override
    public ChainNode parse(JSONObject nodeJSONObject, Tinyflow tinyflow) {
        StartNode startNode = new StartNode();
        JSONObject data = getData(nodeJSONObject);
        startNode.setName(data.getString("title"));
        addParameters(startNode, data);
        return startNode;
    }

    public void addParameters(BaseNode node, JSONObject data) {
        List<Parameter> inputParameters = getParameters(data, "outputs");
        node.setParameters(inputParameters);
    }

    // 添加新的方法来处理参数
    public List<Parameter> getParameters(JSONObject data, String key) {
        List<Parameter> parameters = new ArrayList<>();

        // 获取 inputs 数组
        JSONArray inputs = data.getJSONArray(key);
        if (inputs == null) {
            return parameters;
        }

        // 遍历 inputs 数组
        for (int i = 0; i < inputs.size(); i++) {
            JSONObject inputObj = inputs.getJSONObject(i);
            String name = inputObj.getString("name");
            JSONObject input = inputObj.getJSONObject("input");

            Parameter parameter = new Parameter();
            parameter.setName(name);
            // 可以根据需要设置其他参数属性
            // parameter.setValue(input.getJSONObject("value").getString("content"));

            parameters.add(parameter);
        }

        return parameters;
    }
}
