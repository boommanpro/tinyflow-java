package dev.tinyflow.core.test;

import com.agentsflex.core.chain.*;
import com.agentsflex.llm.openai.OpenAILlm;
import dev.tinyflow.core.Tinyflow;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TinyflowTest {

    static String data1 = "";

    static {
        try (InputStream inputStream = TinyflowTest.class.getClassLoader().getResourceAsStream("test.json")) {
            if (inputStream == null) {
                throw new RuntimeException("test.json not found in resources folder");
            }
            data1 = IOUtils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Tinyflow tinyflow = new Tinyflow(data1);
        tinyflow.setLlmProvider(id -> OpenAILlm.of(""));

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "michael");

        Chain chain = tinyflow.toChain();
        chain.addEventListener(new ChainEventListener() {
            @Override
            public void onEvent(ChainEvent event, Chain chain) {
                System.out.println(event.toString());
            }
        });

        chain.addOutputListener(new ChainOutputListener() {
            @Override
            public void onOutput(Chain chain, ChainNode node, Object outputMessage) {
                System.out.println("outputMessage: " + outputMessage);
            }
        });

        Map<String, Object> result = chain.executeForResult(variables);

        System.out.println(result);
    }
}
