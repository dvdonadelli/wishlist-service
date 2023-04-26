package com.dvdonadelli.wishlist.infrastructure.controller;

import com.dvdonadelli.wishlist.domain.service.WishlistService;
import com.dvdonadelli.wishlist.infrastructure.controller.request.AddItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestBody AddItemRequest request) {
        service.addItem(request.userId(), request.productId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
