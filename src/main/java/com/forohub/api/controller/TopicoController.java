package com.forohub.api.controller;

import com.forohub.api.dto.DatosRegistroTopico;
import com.forohub.api.model.Topico;
import com.forohub.api.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<String> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos) {

        // Validar duplicados
        if (repository.findByTituloAndMensaje(datos.getTitulo(), datos.getMensaje()).isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }

        Topico topico = new Topico();
        topico.setTitulo(datos.getTitulo());
        topico.setMensaje(datos.getMensaje());
        topico.setAutor(datos.getAutor());
        topico.setCurso(datos.getCurso());

        repository.save(topico);

        return ResponseEntity.ok("Tópico registrado correctamente");
    }

    // Nuevo método GET para listar todos los tópicos
    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<Topico>> ultimosTopicos() {
        List<Topico> topicos = repository.findTop10ByOrderByFechaCreacionAsc();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Topico>> buscarTopicos(
            @RequestParam String curso,
            @RequestParam int año) {
        List<Topico> topicos = repository.findByCursoAndYear(curso, año);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Topico>> topicosPaginados(@PageableDefault(size = 5) Pageable pageable) {
        Page<Topico> topicos = repository.findAll(pageable);
        return ResponseEntity.ok(topicos);
    }

    // Método PUT para actualizar un tópico existente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosRegistroTopico datos) {

        // 1. Verificar si el tópico existe
        var topicoExistente = repository.findById(id);
        if (topicoExistente.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }

        // 2. Validar duplicados (otro tópico con mismo título y mensaje)
        var duplicado = repository.findByTituloAndMensaje(datos.getTitulo(), datos.getMensaje());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }

        // 3. Actualizar los datos
        Topico topico = topicoExistente.get();
        topico.setTitulo(datos.getTitulo());
        topico.setMensaje(datos.getMensaje());
        topico.setAutor(datos.getAutor());
        topico.setCurso(datos.getCurso());

        repository.save(topico);

        return ResponseEntity.ok("Tópico actualizado correctamente");
    }
    // Método DELETE para eliminar un tópico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {

        // 1. Verificar si el tópico existe
        var topicoExistente = repository.findById(id);
        if (topicoExistente.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }

        // 2. Eliminar el tópico
        repository.deleteById(id);

        return ResponseEntity.ok("Tópico eliminado correctamente");
    }
    @PostMapping("/batch")
    public ResponseEntity<?> crearTopicos(@RequestBody List<DatosRegistroTopico> topicos) {
        Set<String> vistos = new HashSet<>();
        List<String> guardados = new ArrayList<>();

        for (DatosRegistroTopico datos : topicos) {
            String clave = datos.getTitulo() + "::" + datos.getMensaje();

            // Evitar duplicados internos y existentes en DB
            if (vistos.contains(clave) || repository.findByTituloAndMensaje(datos.getTitulo(), datos.getMensaje()).isPresent()) {
                continue;
            }

            Topico topico = new Topico();
            topico.setTitulo(datos.getTitulo());
            topico.setMensaje(datos.getMensaje());
            topico.setAutor(datos.getAutor());
            topico.setCurso(datos.getCurso());

            repository.save(topico);
            vistos.add(clave);
            guardados.add(clave);
        }

        return ResponseEntity.ok("Tópicos guardados: " + guardados.size());
    }


}