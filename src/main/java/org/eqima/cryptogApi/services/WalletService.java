package org.eqima.cryptogApi.services;

import org.eqima.cryptogApi.entities.Wallet;
import org.eqima.cryptogApi.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Wallet saveWallet(String walletName, String PIN, String phoneNumber,ArrayList<Integer> walletPrivateKeyInt){

        Wallet wallet = new Wallet();
        wallet.setWalletName(walletName);
        wallet.setPIN(passwordEncoder.encode(PIN));
        wallet.setPhoneNumber(phoneNumber);
        wallet.setWalletPrivateKeyInt(walletPrivateKeyInt.toArray(Integer[]::new));
        wallet.setWalletPrivateKey(arrayToByte(walletPrivateKeyInt));
        wallet.setWalletType("STANDARD");
        return walletRepository.save(wallet);
    }

    private byte[] arrayToByte(ArrayList<Integer> array){
        byte[] bytes = new byte[array.size()];
        for (int i = 0; i < array.size(); i++) {
            bytes[i] = array.get(i).byteValue();
        }
        return bytes;
    }

    public Wallet getWalletById(Long id){
       return walletRepository.findById(id).orElse(null);
    }
}
