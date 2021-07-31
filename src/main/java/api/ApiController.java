package api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.smallrye.jwt.build.Jwt;

@Path("/v1/login")
public class ApiController {

	@GET
	@Path("/{role}")
	public Token token(@PathParam("role") String role) {
		var authorities = List.of("ROLE_".concat(role.toUpperCase()));
		if (role.equalsIgnoreCase("Kratos")) {
			authorities = List.of(role.toUpperCase());
		}
		var accessToken = Jwt.issuer("https://example.com/issuer").upn("jdoe@quarkus.io")
				.groups(new HashSet<>(Arrays.asList("USER", "ADMIN", "KRATOS")))
				.claim("authorities", authorities)
				.claim("name", role)
				.expiresIn(3600)
				.sign();
		return new Token(accessToken);
	}

}
