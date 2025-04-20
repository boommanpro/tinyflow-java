package dev.tinyflow.core.test;

import cn.hutool.core.io.resource.ClassPathResource;
import com.agentsflex.chain.node.JsExecNode;
import com.agentsflex.core.chain.Chain;
import com.agentsflex.core.chain.Parameter;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeChainTest {
    @Test
    public void test() throws IOException {
        String code = IOUtils.toString(new ClassPathResource("code.js").getStream());
        Chain chain = new Chain();
        JsExecNode chainNode = new JsExecNode();
        chainNode.setCode(code);
        Parameter parameter = new Parameter();
        parameter.setName("c");
        chainNode.addInputParameter(parameter);
        chain.addNode(chainNode);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("c", "123");
        Map<String, Object> stringObjectMap = chain.executeForResult(variables);
        System.out.println(stringObjectMap);
    }
}
