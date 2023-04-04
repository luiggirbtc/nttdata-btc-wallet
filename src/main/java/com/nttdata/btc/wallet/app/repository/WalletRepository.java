package com.nttdata.btc.wallet.app.repository;

import com.nttdata.btc.wallet.app.model.entity.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Response bean WalletRepository.
 *
 * @author lrs
 */
@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {
}