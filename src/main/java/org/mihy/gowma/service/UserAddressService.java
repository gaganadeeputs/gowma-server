/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.UserAddress;
import org.mihy.gowma.repository.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    public List<UserAddress> getAddressesForUserId(Integer userId) {
        return userAddressRepository.getByUserId(userId);
    }

    public UserAddress createAddress(UserAddress userAddress) {
        return userAddressRepository.create(userAddress);
    }

    public UserAddress updateAddress(UserAddress userAddress) {
        return userAddressRepository.update(userAddress);
    }

    public void deleteForUserIdNAddressId(Integer userId, Integer addressId) {
        userAddressRepository.deleteByIdNUserId(userId, addressId);
    }
}
