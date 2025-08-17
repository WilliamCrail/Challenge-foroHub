package com.challenge.forohub.foro.controller;

import com.challenge.forohub.foro.domain.respuesta.*;
import com.challenge.forohub.foro.domain.topico.TopicoRepository;
import com.challenge.forohub.foro.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos) {
        var topico = topicoRepository.findById(datos.topicoId())
                .orElseThrow(() -> new RuntimeException("Topico no encontrado."));

        var usuario = usuarioRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        var respuesta = new Respuesta(datos.mensaje(), topico, usuario);
        respuestaRepository.save(respuesta);

        return ResponseEntity.ok(new DatosRespuesta(respuesta));
    }

    @GetMapping
    public ResponseEntity<List<DatosRespuesta>> listarRespuestas() {
        var respuestas = respuestaRepository.findAll();

        List<DatosRespuesta> datosRespuestas = respuestas.stream()
                .map(DatosRespuesta::new)
                .toList();

        return ResponseEntity.ok(datosRespuestas);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuesta> actualizarMensajeRespuesta(@PathVariable Long id, @RequestBody @Valid DatosActualizacionRespuesta datos) {

        var optionalRespuesta = respuestaRepository.findById(id);
        if (optionalRespuesta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var respuesta = optionalRespuesta.get();
        respuesta.setMensaje(datos.mensaje());
        respuestaRepository.save(respuesta);

        DatosRespuesta respuestaDTO = new DatosRespuesta(respuesta);

        return ResponseEntity.ok(respuestaDTO);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<DatosRespuesta> eliminarRespuesta(@PathVariable Long id) {
        var respuestaOptional = respuestaRepository.findById(id);

        if (respuestaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var respuestaEliminar = respuestaOptional.get();
        respuestaRepository.delete(respuestaEliminar);

        return ResponseEntity.ok(new DatosRespuesta(respuestaEliminar));
    }
}
