package com.dvdonadelli.wishlist.domain.model;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistItemNotFoundException;
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

    public String getUserId() {
        return userId;
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

    public void removeItemByProductId(String productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        dateModified = LocalDateTime.now();
    }

    public WishlistItem findItemByProductId(String productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new WishlistItemNotFoundException("There is no product " + productId + " in the wishlist"));
    }

    public static Wishlist forUser(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return new Wishlist(userId, new ArrayList<>(), now, now);
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }
}
