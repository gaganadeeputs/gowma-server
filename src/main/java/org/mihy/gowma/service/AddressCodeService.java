package org.mihy.gowma.service;

import org.mihy.gowma.model.AddressCode;
import org.mihy.gowma.repository.AddressCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressCodeService {

    @Autowired
    AddressCodeRepository addressCodeRepository;

    public AddressCode getAddressCodeForCode(String addressCode) {
        return addressCodeRepository.getAddressCodeForCode(addressCode);
    }
}
