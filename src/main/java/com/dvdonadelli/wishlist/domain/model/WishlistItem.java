package com.dvdonadelli.wishlist.domain.model;

import java.time.LocalDateTime;

public class WishlistItem {
    private final String productId;
    private final LocalDateTime dateAdded;

    public WishlistItem(String productId, LocalDateTime dateAdded) {
        this.productId = productId;
        this.dateAdded = dateAdded;
    }

    public String getProductId() {
        return productId;
    }
}
