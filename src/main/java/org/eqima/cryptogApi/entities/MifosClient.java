package org.eqima.cryptogApi.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "mifos_client")
public class MifosClient {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long officeId;
    private Long clientId;
    private Long resourceId;
    private boolean active;
    private String accountNo;

    @OneToOne(mappedBy = "mifosClient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Wallet wallet;
}
