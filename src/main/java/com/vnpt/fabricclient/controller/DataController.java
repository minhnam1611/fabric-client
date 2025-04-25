package com.vnpt.fabricclient.controller;

import com.vnpt.fabricclient.model.Asset;
import com.vnpt.fabricclient.model.DataInfo;
import com.vnpt.fabricclient.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private FabricService fabricService;

    @PostMapping("/init-data")
    public String createInitAsset() throws Exception {
        return fabricService.initListData();
    }
    @GetMapping
    public List<DataInfo> getAllAssets() throws Exception {
        return fabricService.getAllData();
    }
}
