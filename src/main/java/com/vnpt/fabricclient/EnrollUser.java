package com.vnpt.fabricclient;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.*;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

public class EnrollUser {
    public static void main(String[] args) throws Exception {
        // Wallet folder
        Path resourcesPath = Paths.get("src", "main", "resources");
        Path walletPath = resourcesPath.resolve("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        System.out.println("Wallet path: " + walletPath);
        if (wallet.get("appUser") != null) {
            System.out.println("appUser already exists in the wallet");
            return;
        }

        // Load CA
        ClassPathResource resource = new ClassPathResource("ca.org1.example.com-cert.pem");
        Path caCertPath = resource.getFile().toPath();

        HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", new Properties() {{
            put("pemFile", caCertPath.toString());
            put("allowAllHostNames", "true");
        }});
        caClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

        // Enroll admin (nếu chưa có)
        if (wallet.get("admin") == null) {
            Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
            Identity adminIdentity = Identities.newX509Identity("Org1MSP", adminEnrollment);
            wallet.put("admin", adminIdentity);
            System.out.println("Successfully enrolled admin user and imported into the wallet");
        }

        // Register & Enroll appUser
        Identity adminIdentity = wallet.get("admin");
        User admin = new User() {
            @Override public String getName() { return "admin"; }
            @Override public Set<String> getRoles() { return null; }
            @Override public String getAccount() { return null; }
            @Override public String getAffiliation() { return "org1.department1"; }
            @Override public Enrollment getEnrollment() {
                try {
                    return new Enrollment() {
                        @Override public PrivateKey getKey() { return ((X509Identity) adminIdentity).getPrivateKey(); }
                        @Override public String getCert() { return Identities.toPemString(((X509Identity) adminIdentity).getCertificate()); }
                    };
                } catch (Exception e) { throw new RuntimeException(e); }
            }
            @Override public String getMspId() { return "Org1MSP"; }
        };

        RegistrationRequest registrationRequest = new RegistrationRequest("appUser");
        registrationRequest.setAffiliation("org1.department1");
        registrationRequest.setEnrollmentID("appUser");
        String enrollmentSecret = caClient.register(registrationRequest, admin);

        Enrollment enrollment = caClient.enroll("appUser", enrollmentSecret);
        Identity user = Identities.newX509Identity("Org1MSP", enrollment);
        wallet.put("appUser", user);
        System.out.println("Successfully enrolled appUser and imported into the wallet");
    }
}
