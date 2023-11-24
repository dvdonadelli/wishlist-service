package com.dvdonadelli.wishlist.infrastructure.controller;

import com.dvdonadelli.wishlist.domain.service.WishlistService;
import com.dvdonadelli.wishlist.infrastructure.controller.request.AddItemRequest;
import com.dvdonadelli.wishlist.infrastructure.controller.response.WishlistItemResponse;
import com.dvdonadelli.wishlist.infrastructure.controller.response.WishlistResponse;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/items")
    @ResponseStatus(CREATED)
    public void addItem(@PathVariable String userId,
                        @RequestBody AddItemRequest request) {
        service.addItem(userId, request.productId());
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @ResponseStatus(OK)
    public void removeItemFromWishlist(@PathVariable String userId,
                                       @PathVariable String productId) {
        service.removeItemFromWishlist(userId, productId);
    }

    @GetMapping("/{userId}/items/{productId}")
    public WishlistItemResponse isProductInWishlist(@PathVariable String userId,
                                                    @PathVariable String productId) {
        return WishlistItemResponse.fromDomain(service.isProductInWishlist(userId, productId));
    }

    @GetMapping("/{userId}")
    public WishlistResponse getWishlist(@PathVariable String userId) {
        return WishlistResponse.fromDomain(service.getWishlist(userId));
    }
}
