package model;

import java.sql.Timestamp;

public class Postagem {

    private int id;
    private String titulo;
    private String conteudo;
    private String imagem;
    private int autorId;
    private String autorNome;
    private Timestamp dataPublicacao;
    private String status;

    public Postagem() {
    }

    public Postagem(String titulo, String conteudo, String imagem, int autorId) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.imagem = imagem;
        this.autorId = autorId;
        this.status = "PUBLICADA";
    }

    public Postagem(int id, String titulo, String conteudo, String imagem, int autorId) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.imagem = imagem;
        this.autorId = autorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public Timestamp getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Timestamp dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
