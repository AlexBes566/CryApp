package com.example.cryapp;

import android.content.Context;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

class setupBounceCastle {

    public void setBouncyCastle() {                                                           //Android uses the Bouncy Castle Java libraries by default to
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);      //implement some of its cryptographic functionality
        if (provider == null) {                                                                  //But Android included a shortened version of Bouncycastle, and there is no full support for ECDSA.
            // Web3j will set up the provider lazily when it's first used.                       //KeyPairGenerator/ECDSA is not supported, which is the required one to generate ethereum keys.
            return;                                                                              // So we are providing it manually here
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
}
