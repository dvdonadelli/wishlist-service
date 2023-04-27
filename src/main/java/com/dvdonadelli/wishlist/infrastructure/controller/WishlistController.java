package com.dvdonadelli.wishlist.infrastructure.controller;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.service.WishlistService;
import com.dvdonadelli.wishlist.infrastructure.controller.request.AddItemRequest;
import com.dvdonadelli.wishlist.infrastructure.controller.response.WishlistResponse;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                                                    @PathVariable String productId) throws NotFoundException {
        service.removeItemFromWishlist(userId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/items/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable String userId,
                                                       @PathVariable String productId) throws NotFoundException {
        boolean isProductInWishlist = service.isProductInWishlist(userId, productId);
        return ResponseEntity.ok(isProductInWishlist);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWishlist(@PathVariable String userId) {
        Optional<Wishlist> wishlistOptional = service.getWishlist(userId);

        if (wishlistOptional.isPresent()) {
            Wishlist wishlist = wishlistOptional.get();
            WishlistResponse response = WishlistResponse.fromDomain(wishlist);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wishlist not found for user: " + userId);
        }
    }
}
