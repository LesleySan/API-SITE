package com.series.site_series.service;

import java.nio.charset.Charset;

import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.series.site_series.model.Usuario;
import com.series.site_series.model.UsuarioLogin;
import com.series.site_series.repository.UsuarioRepository;

@Service
public class UserService {
	@Autowired
	private UsuarioRepository repository;

	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return repository.save(usuario);
	}
	
	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> user = repository.findByUsuario(usuario.get().getUsuario());

		if (user.isPresent()) {
			if (encoder.matches(user.get().getSenha(), user.get().getSenha())) {

				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);

				usuario.get().setToken(authHeader);
				usuario.get().setId(usuario.get().getId());
				usuario.get().setNome(usuario.get().getNome());
				usuario.get().setFoto(usuario.get().getFoto());				
				usuario.get().setSenha(usuario.get().getSenha());

				return usuario;
			}
		}

		return null;
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (repository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = repository.findByUsuario(usuario.getUsuario());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(repository.save(usuario));
		}
		return Optional.empty();
	}
	
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);
	}
}
