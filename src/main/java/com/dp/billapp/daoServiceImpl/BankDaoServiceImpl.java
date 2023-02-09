package com.dp.billapp.daoServiceImpl;

import com.dp.billapp.daoService.BankDaoService;
import com.dp.billapp.model.BankDetails;
import com.dp.billapp.repository.BankRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BankDaoServiceImpl implements BankDaoService {

    private BankRepository bankRepository;
    @Override
    public BankDetails saveBankDetails(BankDetails bankDetails) {
        return bankRepository.save(bankDetails);
    }

    @Override
    public List<BankDetails> getAllBankDetails() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<BankDetails> getBankDetailsById(long id) {
        return bankRepository.findById(id);
    }

    @Override
    public BankDetails updateBankDetails(BankDetails bankDetails) {
        return bankRepository.save(bankDetails);
    }

    @Override
    public String deleteBankDetails(long id) {
        bankRepository.deleteById(id);
        return "Bank Details Deleted";
    }

    @Override
    public Optional<BankDetails> getBankByAccountNumber(String accountNumber) {
        return bankRepository.findByAccountNumber(accountNumber);
    }


}
