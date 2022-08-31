package br.com.danielsaes.api_receitas_despesas.controller;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielsaes.api_receitas_despesas.config.security.TokenService;
import br.com.danielsaes.api_receitas_despesas.controller.dto.TokenDto;
import br.com.danielsaes.api_receitas_despesas.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;
 
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) throws AuthenticationException {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();

		Authentication authentication = authManager.authenticate(dadosLogin);
		String token = tokenService.gerarToken(authentication);

		return ResponseEntity.ok(new TokenDto(token, "Bearer"));

	}

}
