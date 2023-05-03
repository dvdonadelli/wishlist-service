package com.dvdonadelli.wishlist.domain.exceptions;

public class WishlistItemNotFoundException extends RuntimeException {
    public WishlistItemNotFoundException(String productId) {
        super("There is no product " + productId + " in the wishlist");
    }
}
