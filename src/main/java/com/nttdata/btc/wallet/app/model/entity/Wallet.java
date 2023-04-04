package com.nttdata.btc.wallet.app.model.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Wallet.
 *
 * @author lrs
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "wallet")
public class Wallet {
    @Id
    private String id_wallet;

    private String typeDocument;

    private String numberDocument;

    private String phoneNumber;

    private String email;

    private Date registerDate = new Date();

    private boolean status = true;

    /**
     * Constructor create a new wallet.
     *
     * @param typeDocument   {@link String}
     * @param numberDocument {@link String}
     * @param phoneNumber    {@link String}
     * @param email          {@link String}
     */
    public Wallet(String typeDocument,
                  String numberDocument, String phoneNumber, String email) {
        this.typeDocument = typeDocument;
        this.numberDocument = numberDocument;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        if (registerDate != null) {
            this.registerDate = registerDate;
        } else {
            this.registerDate = new Date();
        }
    }
}