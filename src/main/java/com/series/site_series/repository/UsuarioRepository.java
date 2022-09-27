package com.series.site_series.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.series.site_series.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByUsuario(String usuario);
}
