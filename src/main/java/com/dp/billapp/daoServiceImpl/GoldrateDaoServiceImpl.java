package com.dp.billapp.daoServiceImpl;

import com.dp.billapp.daoService.GoldrateDaoService;
import com.dp.billapp.model.Goldrate;
import com.dp.billapp.repository.GoldrateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GoldrateDaoServiceImpl implements GoldrateDaoService {

    GoldrateRepository goldrateRepository;
    @Override
    public Goldrate saveGoldrate(Goldrate goldrate) {
        return goldrateRepository.save(goldrate);
    }

    @Override
    public List<Goldrate> getGoldRate() {
        return goldrateRepository.findAll();
    }

    @Override
    public Goldrate updateGoldrate(Goldrate goldrate) {
        return goldrateRepository.save(goldrate);
    }
}
