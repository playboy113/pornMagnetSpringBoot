package com.flowers.controller;

import com.flowers.entity.flower;
import com.flowers.service.flowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flower")
public class flowerController {
    @Autowired
    private flowService flowService;

    @RequestMapping("/selectAll.do")
    @ResponseBody
    public Map<String,Object> selectAll(){
        Map<String, Object> retMap = new HashMap<>();
        List<flower> flowers = flowService.selectAll();
        retMap.put("flowers",flowers);
        return retMap;
    }

}
