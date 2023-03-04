package com.dp.billapp.serviceImpl;

import com.dp.billapp.daoService.GoldrateDaoService;
import com.dp.billapp.model.Goldrate;
import com.dp.billapp.service.GoldrateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GoldrateServiceImpl implements GoldrateService {
    GoldrateDaoService goldrateDaoService;
    @Override
    public Goldrate saveGoldrate(Goldrate goldrate) {
        return goldrateDaoService.saveGoldrate(goldrate);
    }

    @Override
    public List<Goldrate> getGoldRate() {
        List<Goldrate> rates = goldrateDaoService.getGoldRate();
        return rates;
    }

    @Override
    public Goldrate updateGoldrate(Goldrate goldrate) {

        return goldrateDaoService.updateGoldrate(goldrate);
    }
}
