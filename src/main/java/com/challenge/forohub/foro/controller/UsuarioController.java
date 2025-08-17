package com.challenge.forohub.foro.controller;

import com.challenge.forohub.foro.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos) {
        if (repository.existsByLogin(datos.login())) {
            return ResponseEntity.badRequest().build();
        }

        var contraseñaEncriptada = passwordEncoder.encode(datos.contraseña());
        var usuario = new Usuario(null,datos.login(),contraseñaEncriptada);
        repository.save(usuario);

        var respuesta = new DatosRespuestaUsuario(usuario.getId(), usuario.getLogin());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<DatosRespuestaUsuario>> listarTodosLosUsuarios() {
        var usuarios = repository.findAll()
                .stream()
                .map(u -> new DatosRespuestaUsuario(u.getId(), u.getLogin()))
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizacionUsuario datos) {

        var optionalUsuario = repository.findById(id);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var usuario = optionalUsuario.get();

        usuario.setLogin(datos.login());
        usuario.setContrasena(passwordEncoder.encode(datos.contraseña()));

        repository.save(usuario);

        var respuesta = new DatosRespuestaUsuario(usuario.getId(), usuario.getLogin());
        return ResponseEntity.ok(respuesta);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
