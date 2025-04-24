package com.vnpt.fabricclient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.hyperledger.fabric.gateway.*;
import org.springframework.core.io.ClassPathResource;

public class FabricClient {
    private static Gateway gateway;

    public static Contract getContract() throws Exception {
        if (gateway == null) {
            Path resourcesPath = Paths.get("src", "main", "resources");
            Path walletPath = resourcesPath.resolve("wallet");
            Wallet wallet = Wallets.newFileSystemWallet(walletPath);

            System.out.println(walletPath);

            ClassPathResource resource = new ClassPathResource("connection-org1.yaml");
            Path networkConfigPath = resource.getFile().toPath();
            System.out.println(networkConfigPath);

            Gateway.Builder builder = Gateway.createBuilder()
                    .identity(wallet, "appUser")
                    .networkConfig(networkConfigPath)
                    .discovery(true);

            gateway = builder.connect();
        }

        Network network = gateway.getNetwork("mychannel");
        return network.getContract("basic");
    }
}
