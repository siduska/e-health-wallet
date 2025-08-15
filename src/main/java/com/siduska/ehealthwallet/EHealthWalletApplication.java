package com.siduska.ehealthwallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "E-health wallet API",
                version = "1.0",
                description = "Reimbursement information"
        ),
        security = { @SecurityRequirement(name = "ehealthwalletapi") }
)
@SecurityScheme(
        name = "ehealthwalletapi",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
public class EHealthWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(EHealthWalletApplication.class, args);
    }

}
