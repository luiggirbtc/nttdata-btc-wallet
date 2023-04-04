package com.nttdata.btc.wallet.app.service;

import com.nttdata.btc.wallet.app.model.entity.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import com.nttdata.btc.wallet.app.model.request.WalletUpdateRequest;
import com.nttdata.btc.wallet.app.repository.WalletRepository;
import com.nttdata.btc.wallet.app.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @InjectMocks
    WalletServiceImpl service;

    @Mock
    WalletRepository repository;

    private List<Wallet> walletList = new ArrayList<>();

    static final String ID_WALLET = "642b379d4478e825a7e04687";

    @BeforeEach
    private void setUp() {
        Wallet wallet = new Wallet();
        wallet.setId_wallet(ID_WALLET);
        wallet.setTypeDocument("1");
        wallet.setNumberDocument("12345678");
        wallet.setEmail("Luiggi@gmail.com");
        wallet.setPhoneNumber("123456789");
        wallet.setRegisterDate(new Date());
        wallet.setStatus(true);

        walletList.add(wallet);
    }

    @Test
    @DisplayName("Return all wallets")
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(walletList));

        Flux<Wallet> result = service.findAll();

        assertEquals(result.blockFirst().getPhoneNumber(), walletList.get(0).getPhoneNumber());
    }

    @Test
    @DisplayName("Return wallet by id")
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(walletList.get(0)));

        Mono<Wallet> result = service.findById(ID_WALLET);

        assertEquals(result.block().getId_wallet(), walletList.get(0).getId_wallet());
    }

    @Test
    @DisplayName("Create new wallet")
    void testCreateWallet() {
        Wallet response = walletList.get(0);

        WalletRequest request = new WalletRequest();
        request.setTypeDocument(response.getTypeDocument());
        request.setNumberDocument(response.getNumberDocument());
        request.setPhoneNumber(response.getPhoneNumber());
        request.setEmail(response.getEmail());

        when(repository.save(any())).thenReturn(Mono.just(response));

        Mono<Wallet> result = service.save(request);

        assertEquals(result.block().getEmail(), response.getEmail());
    }

    @Test
    @DisplayName("Update wallet")
    void testUpdate() {
        Wallet entity = walletList.get(0);

        WalletUpdateRequest request = new WalletUpdateRequest();
        request.setIdWallet(ID_WALLET);
        request.setTypeDocument(entity.getTypeDocument());
        request.setNumberDocument(entity.getNumberDocument());
        request.setPhoneNumber(entity.getPhoneNumber());
        request.setEmail(entity.getEmail());

        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(Mono.just(entity));

        Mono<Wallet> result = service.update(request);

        assertEquals(result.block().getPhoneNumber(), entity.getPhoneNumber());
    }

    @Test
    @DisplayName("Delete wallet")
    void testDeleteWallet() {
        Wallet wallet = walletList.get(0);

        when(repository.findById(anyString())).thenReturn(Mono.just(wallet));

        when(repository.save(any())).thenReturn(Mono.just(wallet));

        Mono<Boolean> result = service.delete(ID_WALLET);

        assertEquals(Boolean.TRUE, result.block().booleanValue());
    }
}