package cmahy.simple.spring.webapp.shell.client.impl.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ShellClientApplicationTest {

    @InjectMocks
    private ShellClientApplication shellClientApplication;

    @Test
    void name() {
        assertDoesNotThrow(() -> {
            String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0V0lnSlhsaGRySVJqN2lMWXQtNXBQdHpyT3BEdG1ORWE3RVZCdHB1ZjdVIn0.eyJleHAiOjE3NDgyMDI3NDYsImlhdCI6MTc0ODIwMjQ0NiwianRpIjoidHJydGNjOjRjYzZhZjMyLTFhNzQtNGUxOS04YjhhLTQ3NDNhZjIwYzljYSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTgwODAvcmVhbG1zL3NwcmluZy1hcHBzIiwic3ViIjoiMDYwODM4OTQtZDlkNi00OWU2LTllZDEtNDEwZWEyNWU5OTBhIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nLXdlYi1hcHAuYXBpIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInNjb3BlIjoiIiwiY2xpZW50SG9zdCI6IjE5Mi4xNjguNjUuMSIsInNjb3BlIjpbImluZ3JlZGllbnQud3JpdGUiLCJ1bWFfcHJvdGVjdGlvbiIsImluZ3JlZGllbnQucmVhZCIsImluZ3JlZGllbnQuZGVsZXRlIl0sImNsaWVudEFkZHJlc3MiOiIxOTIuMTY4LjY1LjEiLCJjbGllbnRfaWQiOiJzcHJpbmctd2ViLWFwcC5hcGkifQ.VCu-sXQyFI2nlGlbkoltE-VvQaMjkoFItHSRUCQ44neQuOVwCw-zivFTS84Tu4cXDXUL0BpTzoGHBII_dl2SKVcp9mLjS1g5dM0eVYet6lZM9sF_xORzNAeBM16STidOhcTXtnRE51hPrxu5sVp3jNVi8Nn9ofU_aFaRPZi2gflxfkmq_QglSKyzzdqfnIr2zLXxV9475ssnJRKPmxHUyTzsi9pVms-Uu_x7spC3PbmOh3eVfMaC6pBOd2KTdb5pQKZZNNf7SRDP7ChOtgGEkQnE8KU8iARXFtmbw308GuHisDw0QHU48GVRm1F05ZkDf4coJxNi3rJ-Pw7gU4V5og";

            String issuer = SignedJWT.parse(token).getJWTClaimsSet().getIssuer();

            System.out.println(issuer);
        });
    }

    @Test
    void name_2() {
        assertDoesNotThrow(() -> {
            String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0V0lnSlhsaGRySVJqN2lMWXQtNXBQdHpyT3BEdG1ORWE3RVZCdHB1ZjdVIn0.eyJleHAiOjE3NDgyMTIwNDIsImlhdCI6MTc0ODIxMTc0MiwianRpIjoidHJydGNjOjc5NmI2NDZkLTcyZWUtNGEyYi1iMjYzLTA2Y2MxYTU1ZTQ3NCIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTgwODAvcmVhbG1zL3NwcmluZy1hcHBzIiwic3ViIjoiMDYwODM4OTQtZDlkNi00OWU2LTllZDEtNDEwZWEyNWU5OTBhIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nLXdlYi1hcHAuYXBpIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwic2NvcGUiOiJvcGVuaWQgc3ByaW5nX3Njb3Blcy5hcGkiLCJjbGllbnRIb3N0IjoiMTkyLjE2OC42NS4xIiwic2NvcGUiOlsiaW5ncmVkaWVudC53cml0ZSIsInVtYV9wcm90ZWN0aW9uIiwiaW5ncmVkaWVudC5yZWFkIiwiaW5ncmVkaWVudC5kZWxldGUiXSwiY2xpZW50QWRkcmVzcyI6IjE5Mi4xNjguNjUuMSIsImNsaWVudF9pZCI6InNwcmluZy13ZWItYXBwLmFwaSJ9.WGYGwQjcwUT0vD_1vrdGAYwVoPlg7cAB7ikaKZmjIaVLFVxru9kiL2tx209EYwLAO5Pr8o7C6deJCZbdRjuPxHqXt-jIJNrv5_OD5DrgmBTU1CXdFLu3B-By2glVx-vMidqkcd_FOzl36wrnUinR-iOhKtOGUHj1-P58lBjRLa2X2rPiWOJSXwdoYO2NMHeBB3ddIM5D9-xxgsLNCZyFJoSeJ0lVx2xUPWsueMnQ7H1EKdGJ200-AlU5GWyG9XTGYpYpcvbmJvwUrAxxt-BMnztBUmNrgSDUqMfUWgguzkzpuWSOIDQTzs_vWgzCvZIAlyYaTfwSFkTebcWSmipLWQ";

            String issuer = JWTParser.parse(token).getJWTClaimsSet().getIssuer();

            System.out.println(issuer);
        });
    }

    @Test
    void name_3() {
        assertDoesNotThrow(() -> {

            String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0V0lnSlhsaGRySVJqN2lMWXQtNXBQdHpyT3BEdG1ORWE3RVZCdHB1ZjdVIn0.eyJleHAiOjE3NDgyMDI3NDYsImlhdCI6MTc0ODIwMjQ0NiwianRpIjoidHJydGNjOjRjYzZhZjMyLTFhNzQtNGUxOS04YjhhLTQ3NDNhZjIwYzljYSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTgwODAvcmVhbG1zL3NwcmluZy1hcHBzIiwic3ViIjoiMDYwODM4OTQtZDlkNi00OWU2LTllZDEtNDEwZWEyNWU5OTBhIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nLXdlYi1hcHAuYXBpIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInNjb3BlIjoiIiwiY2xpZW50SG9zdCI6IjE5Mi4xNjguNjUuMSIsInNjb3BlIjpbImluZ3JlZGllbnQud3JpdGUiLCJ1bWFfcHJvdGVjdGlvbiIsImluZ3JlZGllbnQucmVhZCIsImluZ3JlZGllbnQuZGVsZXRlIl0sImNsaWVudEFkZHJlc3MiOiIxOTIuMTY4LjY1LjEiLCJjbGllbnRfaWQiOiJzcHJpbmctd2ViLWFwcC5hcGkifQ.VCu-sXQyFI2nlGlbkoltE-VvQaMjkoFItHSRUCQ44neQuOVwCw-zivFTS84Tu4cXDXUL0BpTzoGHBII_dl2SKVcp9mLjS1g5dM0eVYet6lZM9sF_xORzNAeBM16STidOhcTXtnRE51hPrxu5sVp3jNVi8Nn9ofU_aFaRPZi2gflxfkmq_QglSKyzzdqfnIr2zLXxV9475ssnJRKPmxHUyTzsi9pVms-Uu_x7spC3PbmOh3eVfMaC6pBOd2KTdb5pQKZZNNf7SRDP7ChOtgGEkQnE8KU8iARXFtmbw308GuHisDw0QHU48GVRm1F05ZkDf4coJxNi3rJ-Pw7gU4V5og";

            Base64.Decoder urlDecoder = Base64.getUrlDecoder();
            String[] parts = token.split("\\.");

            System.out.println("Header:");
            System.out.println(new String(urlDecoder.decode(parts[0]), StandardCharsets.UTF_8));

            System.out.println("Payload:");
            System.out.println(new String(urlDecoder.decode(parts[1]), StandardCharsets.UTF_8));

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.readTree(new String(urlDecoder.decode(parts[0]), StandardCharsets.UTF_8)));
            System.out.println(mapper.readTree(new String(urlDecoder.decode(parts[1]), StandardCharsets.UTF_8)).get("iss"));

        });
    }
}