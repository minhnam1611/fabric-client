package com.vnpt.fabricclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnpt.fabricclient.FabricClient;
import com.vnpt.fabricclient.model.Asset;
import org.hyperledger.fabric.gateway.Contract;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class FabricService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createAsset(Asset asset) throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.submitTransaction("CreateAsset",
                asset.getAssetID(), asset.getColor(), String.valueOf(asset.getSize()),
                asset.getOwner(), String.valueOf(asset.getAppraisedValue()));
        return new String(result);
    }

    public List<Asset> getAllAssets() throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.evaluateTransaction("GetAllAssets");
        Asset[] assets = objectMapper.readValue(result, Asset[].class);
        return Arrays.asList(assets);
    }

    public Asset readAsset(String id) throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.evaluateTransaction("ReadAsset", id);
        return objectMapper.readValue(result, Asset.class);
    }

    public String updateAsset(Asset asset) throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.submitTransaction("UpdateAsset",
                asset.getAssetID(), asset.getColor(), String.valueOf(asset.getSize()),
                asset.getOwner(), String.valueOf(asset.getAppraisedValue()));
        return new String(result);
    }

    public String deleteAsset(String id) throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.submitTransaction("DeleteAsset", id);
        return new String(result);
    }

    public String transferAsset(String id, String newOwner) throws Exception {
        Contract contract = FabricClient.getContract();
        byte[] result = contract.submitTransaction("TransferAsset", id, newOwner);
        return new String(result);
    }
}
