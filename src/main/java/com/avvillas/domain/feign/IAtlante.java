package com.avvillas.domain.feign;

import com.avvillas.domain.dto.BillResponseDto;
import com.avvillas.domain.dto.BillResponseDtoJson;

public interface IAtlante {

    public BillResponseDto getBill();
}
