package com.dp.billapp.daoServiceImpl;

import com.dp.billapp.daoService.DraftDaoService;
import com.dp.billapp.model.Draft;
import com.dp.billapp.repository.DraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftDaoServiceImpl implements DraftDaoService {

    @Autowired
    DraftRepository draftRepository;

    @Override
    public Draft saveInvoice(Draft draft) {
        return draftRepository.save(draft);
    }

    @Override
    public List<Draft> getAllInvoice() {
        return draftRepository.findAll();
    }
}
