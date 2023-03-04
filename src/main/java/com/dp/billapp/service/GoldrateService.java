package com.dp.billapp.service;

import com.dp.billapp.model.Goldrate;

import java.util.List;

public interface GoldrateService {
    Goldrate saveGoldrate(Goldrate goldrate);
    List<Goldrate> getGoldRate();
    Goldrate updateGoldrate(Goldrate goldrate);
}
