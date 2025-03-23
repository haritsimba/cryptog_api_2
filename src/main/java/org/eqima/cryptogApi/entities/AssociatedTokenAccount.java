package org.eqima.cryptogApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "associated_token_account")
public class AssociatedTokenAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;
    String mintName;
    String mintAddress;
    String tokenAccountAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet wallet;
}
