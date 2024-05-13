package com.example.demodb;

import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class MyEndpoint {

    static Logger LOGGER = LoggerFactory.getLogger(MyEndpoint.class);

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/account")
    public String postMethodName(@RequestBody CreateAccount entity) {
        LOGGER.info("deserialized: {}", entity);

        var id = entity.getId();
        var amount = entity.getAmount();

        var newAccount = new AccountDb();
        newAccount.setId(id);
        newAccount.setAmount(amount);

        accountRepository.save(newAccount);
        return "Ok";
    }

    @GetMapping("account")
    public List<String> getMethodName() {
        var all = accountRepository.findAll();
        return StreamSupport
            .stream(all.spliterator(), false)
            .map(it -> it.getId() + ": " + it.getAmount())
            .toList();
    }

    @PostMapping("exchange")
    public String postMethodName(@RequestBody Exchange dto) {

        int accountFromId = dto.getFrom();
        Optional<AccountDb> maybeAccountFrom = accountRepository.findById(accountFromId);
        AccountDb accountFrom = maybeAccountFrom.get();

        int accountToId = dto.getTo();
        AccountDb accountTo = accountRepository.findById(accountToId).get();

        double amount = dto.getAmount();

        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        return "Ok";
    }
    
    
    
}

@Data
class CreateAccount {
    private int id;
    private double amount;
}

class Exchange {
    private int from;
    private int to;
    private double amount;
    
    public int getFrom() {
        return from;
    }
    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }
    public double getAmount() {
        return amount;
    }
    public void setTo(int to) {
        this.to = to;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + from;
        result = prime * result + to;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exchange other = (Exchange) obj;
        if (from != other.from)
            return false;
        if (to != other.to)
            return false;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Exchange [from=" + from + ", to=" + to + ", amount=" + amount + "]";
    }

}