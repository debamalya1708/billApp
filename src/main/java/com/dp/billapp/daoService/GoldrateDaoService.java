package com.dp.billapp.daoService;

import com.dp.billapp.model.Goldrate;

import java.util.List;

public interface GoldrateDaoService {
    Goldrate saveGoldrate(Goldrate goldrate);
    List<Goldrate> getGoldRate();
    Goldrate updateGoldrate(Goldrate goldrate);

}
