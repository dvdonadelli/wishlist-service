package com.dvdonadelli.wishlist.infrastructure.controller;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.service.WishlistService;
import com.dvdonadelli.wishlist.infrastructure.controller.request.AddItemRequest;
import com.dvdonadelli.wishlist.infrastructure.controller.response.WishlistItemResponse;
import com.dvdonadelli.wishlist.infrastructure.controller.response.WishlistResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Void> addItem(@PathVariable String userId,
                                        @RequestBody AddItemRequest request) {
        service.addItem(userId, request.productId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<?> removeItemFromWishlist(@PathVariable String userId,
                                                    @PathVariable String productId) {
        service.removeItemFromWishlist(userId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/items/{productId}")
    public ResponseEntity<?> isProductInWishlist(@PathVariable String userId,
                                                 @PathVariable String productId) {
        WishlistItemResponse response = WishlistItemResponse.fromDomain(service.isProductInWishlist(userId, productId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWishlist(@PathVariable String userId) {
        Wishlist wishlist = service.getWishlist(userId);
        return ResponseEntity.ok(WishlistResponse.fromDomain(wishlist));
    }
}
