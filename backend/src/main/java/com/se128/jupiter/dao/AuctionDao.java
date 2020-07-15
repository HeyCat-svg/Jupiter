package com.se128.jupiter.dao;

import com.se128.jupiter.entity.Auction;

import java.util.List;

public interface AuctionDao {
    List<Auction> getAllAuctions();

    Auction getAuctionByAuctionId(Integer auctionId);
}
