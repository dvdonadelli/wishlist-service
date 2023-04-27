package com.dvdonadelli.wishlist.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String userId;
    private List<WishlistItem> items;
    private LocalDateTime dateCreated;
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

    public void addItem(String productId) {
        if (items.size() >= 20) {
            throw new IllegalStateException("Wishlist already contains 20 items");
        }
        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProductId().equals(productId));

        if (alreadyExists) return;

        WishlistItem item = new WishlistItem(productId, LocalDateTime.now());
        items.add(item);
        setDateModified(LocalDateTime.now());
    }

    public static Wishlist forUser(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return new Wishlist(userId, new ArrayList<>(), now, now);
    }

    private void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }
}
