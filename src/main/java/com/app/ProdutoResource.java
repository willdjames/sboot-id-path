package com.app;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    private Map<Integer, Produto> produtos;

    private static final String KEY = "Bar12345Bar12345"; // 128 bit key
    
    private static final String INIT_VECTOR = "RandomInitVector"; // 16 bytes IV

    public ProdutoResource() {
        if(Objects.isNull(produtos)) {
            produtos = new HashMap<>();
        }
    }


    private int getIdAleatorio() {
        Random random = new Random();
        return random.nextInt(11 - 1) + 1;
    }
    

    @PostMapping(
        consumes = {"application/json"}
    )
    public ResponseEntity<Object> post(@RequestBody Produto produto) {
        int id = this.getIdAleatorio();

        String idEncrypt = Encryptor.encrypt(KEY, INIT_VECTOR, String.valueOf(id));

        produtos.put(id, produto);

        return ResponseEntity.created(URI.create("/produtos/"+idEncrypt)).build();
    }


    @GetMapping(
        produces = {"application/json"}
    )
    public ResponseEntity<Map<Integer, Produto>> getAll() {
        return ResponseEntity.ok(produtos);
    }


    @GetMapping(
        path = "/{id}",
        produces = {"application/json"}
    )
    public ResponseEntity<Produto> get(@PathVariable String id) {

        String idDecrypt = Encryptor.decrypt(KEY, INIT_VECTOR, id);

        return ResponseEntity.ok(produtos.get(Integer.parseInt(idDecrypt)));
    }
}
