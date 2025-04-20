package dev.tinyflow.core.test;

import cn.hutool.core.io.resource.ClassPathResource;
import com.agentsflex.core.chain.Chain;
import com.agentsflex.core.chain.Parameter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import dev.tinyflow.core.node.JsResExecNode;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsResExecNodeRunTest {
    @Test
    public void test() throws IOException {
        String body = IOUtils.toString(new ClassPathResource("jsResExecNodeRun.json").getStream());
        JSONObject jsonObject = JSON.parseObject(body);
        Map<String, Object> variables = jsonObject.getJSONObject("param").getInnerMap();
        Chain chain = new Chain();
        JsResExecNode chainNode = new JsResExecNode();
        Map<String, Object> schema = jsonObject.getJSONObject("schema").getInnerMap();
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("a"));
        parameters.add(new Parameter("b"));
        chainNode.setParameters(parameters);
        chainNode.setCode(JSONPath.eval(schema, "$.data.config.code").toString());

        chain.addNode(chainNode);
        Map<String, Object> stringObjectMap = chain.executeForResult(variables);
        System.out.println(stringObjectMap);
    }
}
