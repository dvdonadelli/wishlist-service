package com.dvdonadelli.wishlist.domain.exceptions;

public class WishlistNotFoundException extends RuntimeException {

    public WishlistNotFoundException(String userId) {
        super("Wishlist not found for user: " + userId);
    }
}
