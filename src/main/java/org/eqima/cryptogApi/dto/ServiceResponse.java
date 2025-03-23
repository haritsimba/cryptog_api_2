package org.eqima.cryptogApi.dto;

import lombok.Data;
import org.eqima.cryptogApi.enums.OrchestratorDataTypes;

@Data
public class ServiceResponse<S,T> {
    S status;
    T data;
    public ServiceResponse(S status, T data) {
        this.status = status;
        this.data = data;
    }

}
