package com.generation.blogpessoal.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*",allowedHeaders="*")
public class TemaController {
	private TemaRepository temaRepository;
	
	@Autowired 
	public TemaController(PostagemRepository postagemRepository, TemaRepository temaRepository){
		this.temaRepository = temaRepository;
	}
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id){
		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
				}
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao ));
		
	}
    @PostMapping
    public ResponseEntity<Tema> create(@Valid @RequestBody Tema tema) {
        Tema savedTema = temaRepository.save(tema);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedTema.getId()).toUri();

        return ResponseEntity.created(location).body(savedTema);
    }
	
    @PutMapping
	public ResponseEntity <Tema> put (@RequestBody Tema tema){
		return ResponseEntity.ok(temaRepository.save(tema));
	}
	
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable long id) {
		temaRepository.deleteById(id);
	}
 
	
}
