package com.inventario.controller;

import java.util.List;

import jakarta.validation.Valid;
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

import com.inventario.model.Categoria;
import com.inventario.service.CategoriaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	private final CategoriaService categoriaService;

	@Autowired
	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> listarCategorias() {
		return ResponseEntity.ok(categoriaService.listarCategorias());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Categoria> crearCategoria(@RequestBody @Valid Categoria categoria) {
		try {
			Categoria categoriaCreado = categoriaService.crearCategoria(categoria);
			return ResponseEntity.ok(categoriaCreado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @RequestBody @Valid Categoria categoria) {
		try {
			Categoria categoriaExistente = categoriaService.buscarCategoriaPorId(id);
			
            if (categoria.getCodigoCategoria() != null && !categoria.getCodigoCategoria().isEmpty())
				categoriaExistente.setCodigoCategoria(categoria.getCodigoCategoria());

			if (categoria.getNombreCategoria() != null && !categoria.getNombreCategoria().isEmpty())
				categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
			
			if (categoria.getDescripcionCategoria() != null && !categoria.getDescripcionCategoria().isEmpty())
				categoriaExistente.setDescripcionCategoria(categoria.getDescripcionCategoria());
			
			
			categoriaService.actualizarCategoria(categoriaExistente);
			
			return ResponseEntity.ok(categoriaExistente);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> eliminarCategoria(@PathVariable Long id) {
		try {
			Categoria categoriaEliminar = categoriaService.buscarCategoriaPorId(id);
			categoriaService.eliminarCategoria(id);
			return ResponseEntity.ok(categoriaEliminar);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
