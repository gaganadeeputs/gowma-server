package org.mihy.gowma.service;

import org.mihy.gowma.model.ProductInventory;
import org.mihy.gowma.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    public ProductInventory update(ProductInventory productInventory) {
        return productInventoryRepository.update(productInventory);
    }

    public ProductInventory createForProductId(ProductInventory productInventory) {
        return productInventoryRepository.create(productInventory);
    }

    public ProductInventory getProductInventoryForProductId(Integer productId) {
        return productInventoryRepository.getByProductId(productId);
    }

    public void deleteByProductIdNId(Integer productId, Integer productInventoryId) {
        productInventoryRepository.deleteByProductIdNId(productId, productInventoryId);
    }
}
