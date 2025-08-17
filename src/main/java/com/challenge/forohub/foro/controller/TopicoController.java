package com.challenge.forohub.foro.controller;

import com.challenge.forohub.foro.domain.topico.*;
import com.challenge.forohub.foro.infra.exceptions.DuplicatedResourceException;
import com.challenge.forohub.foro.infra.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {

        if (repository.existsByTitulo(datos.titulo())) {
            throw new DuplicatedResourceException("El título ya está registrado");
        }

        if (repository.existsByMensaje(datos.mensaje())) {
            throw new DuplicatedResourceException("El mensaje ya está registrado");
        }

        var topico = new Topico(datos);
        repository.save(topico);

        var uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @RequestParam(required = false) Curso curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaDeCreacion", direction = Sort.Direction.ASC) Pageable paginacion
    ) {
        Page<Topico> topicos;

        if (curso != null) {
            topicos = repository.findByCurso(curso, paginacion);
        } else if (anio != null) {
            topicos = repository.findByYear(anio, paginacion);
        } else {
            topicos = repository.findAll(paginacion);
        }

        var topicosDTO = topicos.map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        var  topico = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con id: " + id));

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizacionTopico(@PathVariable Long id,
                                                                  @RequestBody @Valid DatosActualizacionTopico datos) {
        Optional<Topico> optionalTopico = repository.findById(id);

        if (optionalTopico.isEmpty()) {
            throw new ResourceNotFoundException("Topico no encontrado con el id: " + id);
        }

        Topico topico = optionalTopico.get();

        //titulo duplicado
        if (datos.titulo() != null && !datos.titulo().equals(topico.getTitulo())) {
            if (repository.existsByTitulo(datos.titulo())) {
                throw new DuplicatedResourceException("El título ya está registrado en el foro.");
            }
        }

        //mensaje duplicado
        if (datos.mensaje() != null && !datos.mensaje().equals(topico.getMensaje())) {
            if (repository.existsByMensaje(datos.mensaje())) {
                throw new DuplicatedResourceException("El mensaje ya está registrado en el foro.");
            }
        }

        topico.actualizarTopico(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = topicoOptional.get();
        topico.eliminar();
        return ResponseEntity.noContent().build();
    }
}
