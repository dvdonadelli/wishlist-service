package com.dvdonadelli.wishlist.domain.exceptions;

public class WishlistItemNotFoundException extends RuntimeException {
    public WishlistItemNotFoundException(String message) {
        super(message);
    }
}
