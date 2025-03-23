package org.eqima.cryptogApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallet")
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Getter
    private Long id;
    @Getter
    @Setter
    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"wallet"})
    List<AssociatedTokenAccount> associatedTokenAccounts = new ArrayList<>();
    @Getter
    @Setter
    private String walletName;
    @Getter
    @Setter
    private String PIN;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String walletType;
    @Lob
    @Getter
    @Setter
    private byte[] walletPrivateKey;
    @Transient
    @Setter
    private Integer[] walletPrivateKeyInt;

    public Integer[] getWalletPrivateKeyInt() {
        if (walletPrivateKey != null) {
            Integer[] integers = new Integer[walletPrivateKey.length];
            for (int i = 0; i < walletPrivateKey.length; i++) {
                integers[i] = Byte.toUnsignedInt(walletPrivateKey[i]);
            }
            return integers;
        }
        return null;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mifos_client_id", referencedColumnName = "id")
    @Getter
    @Setter
    @ToString.Exclude
    private MifosClient mifosClient;
}