package com.dvdonadelli.wishlist.domain.model;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistItemNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Document(collection = "wishlists")
public final class Wishlist {

    @Id
    private final String userId;
    private final List<WishlistItem> items;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    // private constructor
    private Wishlist(String userId, List<WishlistItem> items, LocalDateTime dateCreated, LocalDateTime dateModified) {
        this.userId = userId;
        this.items = items;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public List<WishlistItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(String productId) {
        if (items.size() >= 20) {
            throw new IllegalStateException("Wishlist already contains 20 items");
        }

        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProductId().equals(productId));

        if (alreadyExists) return;

        WishlistItem item = WishlistItem.of(productId);
        items.add(item);
        setDateModified(LocalDateTime.now());
    }

    public void removeItemByProductId(String productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        dateModified = LocalDateTime.now();
    }

    public WishlistItem findItemByProductId(String productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new WishlistItemNotFoundException(productId));
    }

    // exposing factory methods to create Wishlists
    public static Wishlist forUser(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return new Wishlist(userId, new ArrayList<>(), now, now);
    }

    public static Wishlist forUserWithProduct(String userId, String productId) {
        LocalDateTime now = LocalDateTime.now();
        Wishlist wishlist = new Wishlist(userId, new ArrayList<>(), now, now);
        wishlist.addItem(productId);
        return wishlist;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }
}
