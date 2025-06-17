package com.clinic.management.response;

public class ApiResponse<T> {

    private T dados;
    private String mensagem;

    public ApiResponse(T dados, String mensagem) {
        this.dados = dados;
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
