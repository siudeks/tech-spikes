package com.example.demodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository
public interface AccountRepository extends CrudRepository<AccountDb, Integer> {
}

@Data
@Table("accounts")
class AccountDb {
    @Id
    private int id;

    @Version
    private Integer version;

    private double amount;
}
