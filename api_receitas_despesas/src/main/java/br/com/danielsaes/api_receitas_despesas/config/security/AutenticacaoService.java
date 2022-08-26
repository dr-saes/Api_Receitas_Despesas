package br.com.danielsaes.api_receitas_despesas.config.security;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.danielsaes.api_receitas_despesas.modelo.Usuario;
import br.com.danielsaes.api_receitas_despesas.repository.UsuarioRepository;

@Component
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String usename) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByNome(usename);
		if(usuario.isPresent()) {
			return usuario.get();
		}
		throw new UsernameNotFoundException("Dados inválidos");
	}
}

