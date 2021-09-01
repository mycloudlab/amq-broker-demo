package br.com.redhat.amq.broker.exemplo1;

public class Funcionario {

  private Long id;
  private String nome;



  public Funcionario() {}

  public Funcionario(long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String toString() {
    return "Funcionario [id=" + id + ", nome=" + nome + "]";
  }



}
