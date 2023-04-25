package com.dvdonadelli.wishlist.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Wishlist {
    private String id;
    private final String userId;
    private final List<WishlistItem> items;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public Wishlist(String userId, List<WishlistItem> items, LocalDateTime dateCreated, LocalDateTime dateModified) {
        this.userId = userId;
        this.items = items;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public List<WishlistItem> getItems() {
        return items;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }
}
