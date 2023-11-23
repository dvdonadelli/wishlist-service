package com.dvdonadelli.wishlist.infrastructure.controller.response;

import com.dvdonadelli.wishlist.domain.model.WishlistItem;

import java.time.LocalDateTime;

public record WishlistItemResponse(String productId, LocalDateTime dateAdded) {

    public static WishlistItemResponse fromDomain(WishlistItem wishlistItem) {
        return new WishlistItemResponse(wishlistItem.getProductId(), wishlistItem.getDateAdded());
    }
}
