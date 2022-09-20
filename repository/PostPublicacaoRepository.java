package com.series.site_series.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.series.site_series.model.PostPublicacao;

public interface PostPublicacaoRepository extends JpaRepository<PostPublicacao, Long>{
	public List<PostPublicacao> findAllByTituloContainingIgnoreCase(String titulo);
}
