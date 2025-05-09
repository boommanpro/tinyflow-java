package com.tinyflow.demo.controller;

import com.agentsflex.llm.qwen.QwenLlm;
import com.agentsflex.llm.qwen.QwenLlmConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import dev.tinyflow.core.Tinyflow;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class WorkFlowController {

    @PostMapping("workflow/singleNodeExec")
    public ResponseEntity<Map<String, Object>> singleNodeExec(@RequestBody JSONObject wf) {
        Tinyflow tinyflow = buildByNode(wf.getJSONObject("node"));
        Map<String, Object> variables = wf.getJSONObject("params").getInnerMap();
        Map<String, Object> result = tinyflow.toChain().executeForResult(variables);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/workflow/exe")
    public ResponseEntity<Map<String, Object>> exe(@RequestBody JSONObject wf) {
        Tinyflow tinyflow = parseFlowParam(wf.getJSONObject("data").toJSONString());
        Map<String, Object> variables = wf.getJSONObject("param").getInnerMap();
        Map<String, Object> result = tinyflow.toChain().executeForResult(variables);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Tinyflow parseFlowParam(String graph) {
        JSONObject json = JSONObject.parseObject(graph);
        JSONArray nodeArr = json.getJSONArray("nodes");
        Tinyflow tinyflow = new Tinyflow(json.toJSONString());
        for (int i = 0; i < nodeArr.size(); i++) {
            JSONObject node = nodeArr.getJSONObject(i);
            switch (node.getString("type")) {
                case "llmNode":
                    JSONObject data = node.getJSONObject("data");
                    QwenLlmConfig qwenLlmConfig = new QwenLlmConfig();
                    //  千问apikey
                    qwenLlmConfig.setApiKey("sk-xxxxxxxxxxx");
                    qwenLlmConfig.setModel("qwen-plus");
                    tinyflow.setLlmProvider(id -> new QwenLlm(qwenLlmConfig));
                    break;
                case "zsk":

                    break;
                default:
                    break;
            }
        }
        return tinyflow;
    }

    private Tinyflow buildByNode(JSONObject nodeStr) {
        HashMap<String, List<Object>> graphMap = new HashMap<>();
        graphMap.put("nodes", Arrays.asList(nodeStr));
        graphMap.put("edges", Arrays.asList());
        JSONObject json = JSONObject.parseObject(JSON.toJSONString(graphMap));
        JSONArray nodeArr = json.getJSONArray("nodes");
        Tinyflow tinyflow = new Tinyflow(json.toJSONString());
        for (int i = 0; i < nodeArr.size(); i++) {
            JSONObject node = nodeArr.getJSONObject(i);
            switch (node.getString("type")) {
                case "llmNode":
                    JSONObject data = node.getJSONObject("data");
                    QwenLlmConfig qwenLlmConfig = new QwenLlmConfig();
                    //  千问apikey
                    qwenLlmConfig.setApiKey("sk-xxxxxxxxxxx");
                    qwenLlmConfig.setModel("qwen-plus");
                    tinyflow.setLlmProvider(id -> new QwenLlm(qwenLlmConfig));
                    break;
                case "zsk":

                    break;
                default:
                    break;
            }
        }
        return tinyflow;
    }


}
