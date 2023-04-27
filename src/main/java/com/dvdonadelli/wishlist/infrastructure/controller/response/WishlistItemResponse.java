package com.dvdonadelli.wishlist.infrastructure.controller.response;

import com.dvdonadelli.wishlist.domain.model.WishlistItem;

public record WishlistItemResponse(
        String productId
) {

    public static WishlistItemResponse fromDomain(WishlistItem wishlistItem) {
        return new WishlistItemResponse(wishlistItem.getProductId());
    }
}
