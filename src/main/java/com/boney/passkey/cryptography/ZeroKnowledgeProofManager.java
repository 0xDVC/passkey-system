package com.boney.passkey.cryptography;

import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECAlgorithms;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;

public class ZeroKnowledgeProofManager {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ECC_CURVE_NAME = "secp256r1";

    public boolean performZeroKnowledgeProof(String userId, String password, String publicKey) {
        try {
            KeyPair keyPair = generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKeyObj = keyPair.getPublic();

            // Simulate zero-knowledge proof using Schnorr protocol
            BigInteger x = generateRandomScalar();
            ECPoint commitment = generateCommitment(publicKeyObj, x);

            // Send the commitment to the server for verification

            // Simulate server-side verification
            SignatureVerifier verifier = new SignatureVerifier(publicKeyObj, commitment);
            return verifier.verify(password, x);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec(ECC_CURVE_NAME);
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private BigInteger generateRandomScalar() {
        return new BigInteger(256, new SecureRandom());
    }

    private ECPoint generateCommitment(PublicKey publicKey, BigInteger x) throws InvalidKeyException {
        if (!(publicKey instanceof ECPublicKey ecPublicKey)) {
            throw new IllegalArgumentException("Public key is not an EC public key");
        }

        // Use Bouncy Castle for scalar multiplication

        ECPoint G = ECPointUtil.convertPoint(ecPublicKey.getParams().getCurve(), ecPublicKey.getW(), false);
        return (ECPoint) ECAlgorithms.referenceMultiply(G, x).normalize();
    }

    private record SignatureVerifier(PublicKey publicKey, ECPoint commitment) {

        public boolean verify(String message, BigInteger x) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
                try {
                    Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
                    signature.initVerify(publicKey);
                    signature.update(message.getBytes());

                    // Use the verify method directly
                    return signature.verify(x.toByteArray());
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
}
