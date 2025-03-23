/*
package org.eqima.cryptogApi.services;

import eqima.org.cryptog.beans.FineractSavingAccount;
import eqima.org.cryptog.beans.MifosAddress;
import eqima.org.cryptog.dto.*;
import eqima.org.cryptog.entities.MifosClient;
import eqima.org.cryptog.entities.Wallet;
import eqima.org.cryptog.repositories.MifosClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class MifosClientService {
    @Autowired
    private WebClient mifos_webclient;
    @Autowired
    private MifosClientRepository mifosClientRepository;

    public ResponseEntity<MifosClientInfo> getMifosClientInfo(Long clientId){

        return mifos_webclient.get()
                .uri("/fineract-provider/api/v1/clients/"+clientId+"/accounts")
                .retrieve()
                .toEntity(MifosClientInfo.class)
                .block();
    }

    public Double getMifosAccountBanalance() {
        ResponseEntity<MifosClientInfo> mifosClientInfo = getMifosClientInfo(1L);
        if(mifosClientInfo.getStatusCode().is2xxSuccessful()){
            MifosClientInfo body = mifosClientInfo.getBody();
            System.out.println(body.toString());
            return body.getSavingsAccounts().get(0).getAccountBalance();
        }
        return null;
    }

    public ResponseEntity<MifosClient> createMifosClient(String phoneNumber, String lastName){
        MifosAddress mifosAddress = new MifosAddress();
        mifosAddress.setAddressTypeId(805L);
        mifosAddress.setActive(false);
        mifosAddress.setStreet("rapchik");
        mifosAddress.setStateProvinceId(800L);
        mifosAddress.setContryId(802L);

        MifosAccountCreationDto mifosAccountCreationDto = new MifosAccountCreationDto();
        mifosAccountCreationDto.setFirstname(lastName);
        mifosAccountCreationDto.setLastname("m");
        mifosAccountCreationDto.setExternalId(phoneNumber);
        mifosAccountCreationDto.setDateFormat("dd MMMM yyyy");
        mifosAccountCreationDto.setLocale("en");
        mifosAccountCreationDto.setActive(false);
        mifosAccountCreationDto.setActivationDate(localDateToString(LocalDate.now()));
        mifosAccountCreationDto.setSubmittedOnDate(localDateToString(LocalDate.now()));
        mifosAccountCreationDto.setLegalFormId(1L);
        mifosAccountCreationDto.setOfficeId(1L);
        mifosAccountCreationDto.setAddress(List.of(mifosAddress));
        System.out.println(mifosAccountCreationDto.toString());
        return mifos_webclient.post()
                .uri("/fineract-provider/api/v1/clients")
                .bodyValue(mifosAccountCreationDto)
                .retrieve()
                .toEntity(MifosClient.class)
                .block();
    }

    public ResponseEntity<MifosTransferDetailDto> transferFromMifos(Wallet fromClient, Wallet toClient, Double amount, String description){
        String fromAccount = null;
        String toAccount = null;

        if(fromClient.getMifosClient().isActive()){
            fromAccount = fromClient.getMifosClient().getAccountNo();
        }
        if(toClient.getMifosClient().isActive()) {
            toAccount = toClient.getMifosClient().getAccountNo();
        }

        if(!fromClient.getMifosClient().isActive() || !toClient.getMifosClient().isActive()){
            ResponseEntity<GetFineractSavingAccountListDto> savingAccountList = getFineractSavingAccountList();
            ResponseEntity<MifosClientInfo> fromClientInfo = getMifosClientInfo(fromClient.getMifosClient().getClientId());
            if(savingAccountList.getStatusCode().is2xxSuccessful() && savingAccountList.getBody() != null && fromClientInfo.getStatusCode().is2xxSuccessful() && fromClientInfo.getBody() != null){
                if(!fromClient.getMifosClient().isActive()) {
                    System.out.println(fromClient.getMifosClient());
                    FineractSavingAccount fromSavingAccount = getActiveAccountFromSavingAccounts(fromClient.getMifosClient().getClientId(), savingAccountList.getBody());
                    if(fromSavingAccount == null){
                        return ResponseEntity.badRequest().body(null);
                    }

                    fromAccount = fromSavingAccount.getAccountNo();
                    MifosClient fromMifosClient = mifosClientRepository.findById(fromClient.getMifosClient().getId()).get();
                    fromMifosClient.setAccountNo(fromAccount);
                    fromMifosClient.setActive(true);
                    mifosClientRepository.save(fromMifosClient);

                }
                if(!toClient.getMifosClient().isActive()) {
                    System.out.println(toClient.getMifosClient());
                    FineractSavingAccount toSavingAccount = getActiveAccountFromSavingAccounts(toClient.getMifosClient().getClientId(), savingAccountList.getBody());
                    if(toSavingAccount == null){
                        return ResponseEntity.badRequest().body(null);
                    }
                    toAccount = toSavingAccount.getAccountNo();
                    MifosClient toMifosClient = mifosClientRepository.findById(toClient.getMifosClient().getId()).get();
                    toMifosClient.setAccountNo(toAccount);
                    toMifosClient.setActive(true);
                    mifosClientRepository.save(toMifosClient);
                }
            }
        }


        if(toAccount == null || fromAccount == null){
            return ResponseEntity.badRequest().body(null);
        }

        MifosTransferRequestDto transferDto = new MifosTransferRequestDto();
        transferDto.setFromOfficeId(1L);
        transferDto.setFromClientId(1L);
        transferDto.setFromAccountType(2L);
        transferDto.setFromAccountId(stringToLong(fromAccount));
        transferDto.setToOfficeId(1L);
        transferDto.setToClientId(1L);
        transferDto.setToAccountType(2L);
        transferDto.setToAccountId(stringToLong(toAccount));
        transferDto.setDateFormat("dd MMMM yyyy");
        transferDto.setLocale("en");
        transferDto.setTransferDate(localDateToString(LocalDate.now()));
        transferDto.setTransferAmount(amount);
        transferDto.setTransferDescription(description);
        System.out.println(transferDto.toString());
        return mifos_webclient.post()
                .uri("/fineract-provider/api/v1/accounttransfers")
                .bodyValue(transferDto)
                .retrieve()
                .toEntity(MifosTransferDetailDto.class)
                .block();
    }
    String localDateToString(LocalDate date){
        return date.format(java.time.format.DateTimeFormatter.ofPattern("dd MM yyyy"));
    }

    public ResponseEntity<GetFineractSavingAccountListDto> getFineractSavingAccountList(){
        return mifos_webclient.get()
                .uri("/fineract-provider/api/v1/savingsaccounts")
                .retrieve()
                .toEntity(GetFineractSavingAccountListDto.class)
                .block();
    }

    public FineractSavingAccount getActiveAccountFromSavingAccounts(Long clientId, GetFineractSavingAccountListDto savingAccountList){
        for (FineractSavingAccount account : savingAccountList.getPageItems()){
            if(Objects.equals(account.getClientId(), clientId) && account.getStatus().getValue().equalsIgnoreCase("active")){
                return account;
            }
        }
        return null;
    }

    private Long stringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}*/
