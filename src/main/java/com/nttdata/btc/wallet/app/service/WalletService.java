package com.nttdata.btc.wallet.app.service;

import com.nttdata.btc.wallet.app.model.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import com.nttdata.btc.wallet.app.model.request.WalletUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service WalletService.
 *
 * @author lrs.
 */
public interface WalletService {
    /**
     * Method findAll.
     */
    Flux<Wallet> findAll();

    /**
     * Method findById.
     */
    Mono<Wallet> findById(String id);

    /**
     * Method save.
     */
    Mono<Wallet> save(WalletRequest request);

    /**
     * Method Delete.
     */
    Mono<Boolean> delete(String id);

    /**
     * Method update.
     *
     * @param request {@link WalletUpdateRequest}
     * @return {@link Wallet}
     */
    Mono<Wallet> update(WalletUpdateRequest request);
}