package com.vnpt.fabricclient.controller;

import com.vnpt.fabricclient.model.Asset;
import com.vnpt.fabricclient.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private FabricService fabricService;


    @PostMapping("/init-data")
    public String createInitAsset() throws Exception {
        return fabricService.initListAsset();
    }

    @PostMapping
    public String createAsset(@RequestBody Asset asset) throws Exception {
        return fabricService.createAsset(asset);
    }

    @GetMapping
    public List<Asset> getAllAssets() throws Exception {
        return fabricService.getAllAssets();
    }

    @GetMapping("/{id}")
    public Asset getAsset(@PathVariable String id) throws Exception {
        return fabricService.readAsset(id);
    }

    @PutMapping
    public String updateAsset(@RequestBody Asset asset) throws Exception {
        return fabricService.updateAsset(asset);
    }

    @DeleteMapping("/{id}")
    public String deleteAsset(@PathVariable String id) throws Exception {
        return fabricService.deleteAsset(id);
    }

    @PutMapping("/{id}/transfer")
    public String transferAsset(@PathVariable String id, @RequestParam String newOwner) throws Exception {
        return fabricService.transferAsset(id, newOwner);
    }
}
