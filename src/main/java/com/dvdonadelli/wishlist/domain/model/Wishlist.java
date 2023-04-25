package com.dvdonadelli.wishlist.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Wishlist {

    private String id;
    private String userId;
    private List<WishlistItem> items;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

}
