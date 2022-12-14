package com.series.site_series.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.series.site_series.model.PostPublicacao;
import com.series.site_series.repository.PostPublicacaoRepository;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostPublicacaoController {

	@Autowired
	private PostPublicacaoRepository repository; 
	
	@GetMapping
	public ResponseEntity <List<PostPublicacao>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <PostPublicacao> getById(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	


@PostMapping
public ResponseEntity<PostPublicacao> post(@RequestBody PostPublicacao postagem) {
	return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
}

@PutMapping
public ResponseEntity<PostPublicacao> put(@RequestBody PostPublicacao postagem){
	return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
}

@DeleteMapping("/{id}")
public void delete(@PathVariable long id) {
	repository.deleteById(id);

}

}


