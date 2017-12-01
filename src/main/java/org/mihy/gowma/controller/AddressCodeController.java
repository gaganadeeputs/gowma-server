/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.AddressCode;
import org.mihy.gowma.service.AddressCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressCodeController extends AbstractWebResponseController {

    @Autowired
    private AddressCodeService addressCodeService;

    @ApiOperation(value = "Get a Address information based on code")
    @GetMapping(EndPoints.AddressCode.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AddressCode getAddressCodeForCode(@RequestParam(value = EndPoints.QueryParam.ADDRESS_CODE) String addressCode) {
        if (StringUtils.isEmpty(addressCode)) {
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_INPUT, "Required query param missing: "
                    + EndPoints.QueryParam.ADDRESS_CODE);
        }
        return addressCodeService.getAddressCodeForCode(addressCode);
    }


}
