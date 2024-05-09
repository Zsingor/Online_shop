package com.shop.service;

import com.shop.entity.BrowseTime;
import com.shop.entity.Goods;
import com.shop.entity.User;

import java.util.List;

public interface BrowseService {
    List<BrowseTime> getbrowse();

    int addbrowse(BrowseTime browseTime);

    int deletebrowse(BrowseTime browseTime);
}
