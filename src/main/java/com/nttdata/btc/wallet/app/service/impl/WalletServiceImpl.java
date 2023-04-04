package com.nttdata.btc.wallet.app.service.impl;

import com.nttdata.btc.wallet.app.model.entity.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import com.nttdata.btc.wallet.app.model.request.WalletUpdateRequest;
import com.nttdata.btc.wallet.app.repository.WalletRepository;
import com.nttdata.btc.wallet.app.service.WalletService;
import com.nttdata.btc.wallet.app.util.WalletMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * Service Implement WalletService.
 *
 * @author lrs
 */
@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

    /**
     * Inject dependency {@link WalletRepository}
     */
    @Autowired
    WalletRepository repository;

    /**
     * Reference interface WalletMapper.
     */
    private WalletMapper walletMapper = Mappers.getMapper(WalletMapper.class);


    /**
     * Method findAll.
     */
    @Override
    public Flux<Wallet> findAll() {
        return repository.findAll().filter(Wallet::isStatus)
                .onErrorResume(e -> Flux.error(customException(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }

    /**
     * Method findById.
     *
     * @param id {@link String}
     */
    @Override
    public Mono<Wallet> findById(String id) {
        return repository.findById(id).filter(Wallet::isStatus)
                .onErrorResume(e -> Mono.error(customException(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }

    /**
     * Method save.
     *
     * @param request {@link WalletRequest}
     */
    @Override
    public Mono<Wallet> save(WalletRequest request) {
        return repository.save(walletMapper.toWallet(request))
                .onErrorResume(e -> Mono.error(customException(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }

    /**
     * Method Delete.
     *
     * @param id {@link String}
     */
    @Override
    public Mono<Boolean> delete(String id) {
        return repository.findById(id).filter(Wallet::isStatus).flatMap(e -> {
            e.setStatus(false);
            return repository.save(e)
                    .flatMap(x -> (null != x.getNumberDocument()) ? Mono.just(Boolean.TRUE) : Mono.just(Boolean.FALSE));
        });
    }

    /**
     * Method update.
     *
     * @param request {@link WalletUpdateRequest}
     * @return {@link Wallet}
     */
    @Override
    public Mono<Wallet> update(WalletUpdateRequest request) {
        return repository.findById(request.getIdWallet())
                .map(entity -> updateWallet.apply(request, entity))
                .flatMap(wallet -> repository.save(wallet))
                .onErrorResume(e -> Mono.error(customException(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }

    /**
     * BiFunction update Wallet.
     */
    BiFunction<WalletUpdateRequest, Wallet, Wallet> updateWallet = (request, customer) -> {
        customer.setTypeDocument(request.getTypeDocument());
        customer.setNumberDocument(request.getNumberDocument());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());
        return customer;
    };

    /**
     * Method custom exception.
     *
     * @param status  {@link HttpStatus}
     * @param message {@link String}
     * @return {@link ResponseStatusException}
     */
    private ResponseStatusException customException(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }
}