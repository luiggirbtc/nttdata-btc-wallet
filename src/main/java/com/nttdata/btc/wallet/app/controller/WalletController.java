package com.nttdata.btc.wallet.app.controller;

import com.nttdata.btc.wallet.app.model.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import com.nttdata.btc.wallet.app.model.request.WalletUpdateRequest;
import com.nttdata.btc.wallet.app.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Class WalletController.
 *
 * @author lrs
 */
@Slf4j
@Tag(name = "Wallet", description = "Service wallet")
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    /**
     * Inject dependency WalletService.
     */
    @Autowired
    private WalletService service;

    /**
     * Service find by id.
     *
     * @param id {@link String}
     * @return {@link Wallet}
     */
    @Operation(summary = "Get a wallet by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping(value = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Wallet> findById(@PathVariable String id) {
        log.info("Start FindById");
        return service.findById(id);
    }

    /**
     * Service create wallet.
     *
     * @param request {@link WalletRequest}
     * @return {@link Wallet}
     */
    @Operation(summary = "Create a new wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Wallet> createWallet(@Valid @RequestBody final WalletRequest request) {
        log.info("Start CreateWallet.");
        return service.save(request);
    }

    /**
     * Service list all wallets.
     *
     * @return {@link Wallet}
     */
    @Operation(summary = "Get all wallets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Wallet> findAll() {
        log.info("Start findAll Wallets.");
        return service.findAll()
                .doOnNext(wallet -> log.info(wallet.toString()));
    }

    /**
     * Service update wallet.
     *
     * @param request {@link WalletUpdateRequest}
     * @return {@link Wallet}
     */
    @Operation(summary = "Update wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Wallet> updateWallet(@Valid @RequestBody final WalletUpdateRequest request) {
        log.info("Start UpdateWallet.");
        return service.update(request);
    }

    /**
     * Service delete wallet.
     *
     * @param id {@link String}
     * @return {@link Void}
     */
    @Operation(summary = "Delete wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @DeleteMapping(value = "/{id}")
    public Mono<Boolean> deleteWallet(@PathVariable(value = "id") final String id) {
        return service.delete(id);
    }
}