package com.dvdonadelli.wishlist.domain.exceptions;


public class WishlistNotFoundException extends RuntimeException {

    public WishlistNotFoundException(String message) {
        super(message);
    }
}
