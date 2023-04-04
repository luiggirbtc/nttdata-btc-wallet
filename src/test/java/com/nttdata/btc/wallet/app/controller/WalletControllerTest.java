package com.nttdata.btc.wallet.app.controller;

import com.nttdata.btc.wallet.app.model.entity.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import com.nttdata.btc.wallet.app.model.request.WalletUpdateRequest;
import com.nttdata.btc.wallet.app.service.WalletService;
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
class WalletControllerTest {
    @InjectMocks
    WalletController controller;

    @Mock
    WalletService service;

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
    @DisplayName("Return wallet by id")
    void testFindById() {
        when(service.findById(anyString())).thenReturn(Mono.just(walletList.get(0)));

        Mono<Wallet> result = controller.findById(ID_WALLET);

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

        when(service.save(request)).thenReturn(Mono.just(response));

        Mono<Wallet> result = controller.createWallet(request);

        assertEquals(result.block().getEmail(), response.getEmail());
    }

    @Test
    @DisplayName("Return all wallets")
    void testFindAll() {
        when(service.findAll()).thenReturn(Flux.fromIterable(walletList));

        Flux<Wallet> result = controller.findAll();

        assertEquals(result.blockFirst().getNumberDocument(), walletList.get(0).getNumberDocument());
    }

    @Test
    @DisplayName("Update wallet")
    void testUpdateWallet() {
        Wallet response = walletList.get(0);

        WalletUpdateRequest request = new WalletUpdateRequest();
        request.setIdWallet(ID_WALLET);
        request.setTypeDocument(response.getTypeDocument());
        request.setNumberDocument(response.getNumberDocument());
        request.setPhoneNumber(response.getPhoneNumber());
        request.setEmail(response.getEmail());

        when(service.update(any())).thenReturn(Mono.just(response));

        Mono<Wallet> result = controller.updateWallet(request);

        assertEquals(result.block().getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    @DisplayName("Delete wallet")
    void testDeleteWallet() {
        when(service.delete(anyString())).thenReturn(Mono.just(Boolean.TRUE));

        Mono<Boolean> result = controller.deleteWallet(ID_WALLET);

        assertEquals(Boolean.TRUE, result.block().booleanValue());
    }
}