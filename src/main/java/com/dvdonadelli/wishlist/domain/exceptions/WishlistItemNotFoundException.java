package com.dvdonadelli.wishlist.domain.exceptions;

public class WishlistItemNotFoundException extends RuntimeException {
    public WishlistItemNotFoundException(String productId) {
        super("There is no product %s in the wishlist".formatted(productId));
    }
}
