package org.eqima.cryptogApi.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.eqima.cryptogApi.enums.TransactionTypes;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;
    @Enumerated(EnumType.STRING)
    TransactionTypes TransactionType;
    @NotNull
    String sourceWallet;
    @Nullable
    String destinationWallet;
    @NotNull
    String sourceWalletName;
    @Nullable
    String destinationWalletName;
    @NotNull
    Integer sourceWalletBalance;
    @Nullable
    Integer destinationWalletBalance;
    @NotNull
    String sourcePhoneNumber;
    @Nullable
    String destinationPhoneNumber;
    Long timestamp;
    Integer amount;
    String signature;

}
