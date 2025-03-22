package org.eqima.cryptogApi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.eqima.cryptogApi.enums.TransferRequestStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transfer_request")
@ToString
public class TransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String sourceWalletAddress;
    private String destinationWalletAddress;
    private String sourcePhoneNumber;
    private String destinationPhoneNumber;
    private Long timestamp;
    private Integer amount;
    private String sourceWalletName;
    private String destinationWalletName;;
    @Enumerated(EnumType.STRING)
    private TransferRequestStatus status;
}
