package com.dvdonadelli.wishlist.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class WishlistItem {
    private final String productId;
    private final LocalDateTime dateAdded;

    private WishlistItem(String productId, LocalDateTime dateAdded) {
        this.productId = productId;
        this.dateAdded = dateAdded;
    }

    public String getProductId() {
        return productId;
    }

    public static WishlistItem of(String productId) {
        LocalDateTime now = LocalDateTime.now();
        return new WishlistItem(productId, now);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishlistItem that)) return false;
        return Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId());
    }
}
