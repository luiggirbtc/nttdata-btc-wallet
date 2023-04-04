package com.nttdata.btc.wallet.app.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entity WalletRequest.
 *
 * @author lrs
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletRequest {
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Type document", example = "1 = DNI | 2 = RUC | 3 = CE")
    private String typeDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Number document", example = "12345678")
    private String numberDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Phone number", example = "123456789")
    private String phoneNumber;

    @Schema(required = false, description = "Email", example = "pedro@gmail.com")
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
}