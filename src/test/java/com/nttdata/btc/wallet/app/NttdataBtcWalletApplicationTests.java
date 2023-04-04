package com.nttdata.btc.wallet.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NttdataBtcWalletApplicationTests {

    @Test
    void contextLoads() {
        String expected = "btc-wallet";
        String actual = "btc-wallet";

        assertEquals(expected, actual);
    }
}