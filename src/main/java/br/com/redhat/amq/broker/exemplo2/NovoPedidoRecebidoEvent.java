package br.com.redhat.amq.broker.exemplo2;

public class NovoPedidoRecebidoEvent {

  private long codigoPedido;
  private String nomeCliente;

  public NovoPedidoRecebidoEvent() {}

  public NovoPedidoRecebidoEvent(long codigoPedido, String nomeCliente) {
    super();
    this.codigoPedido = codigoPedido;
    this.nomeCliente = nomeCliente;
  }


  public long getCodigoPedido() {
    return codigoPedido;
  }


  public void setCodigoPedido(long codigoPedido) {
    this.codigoPedido = codigoPedido;
  }


  public String getNomeCliente() {
    return nomeCliente;
  }

  public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
  }

  @Override
  public String toString() {
    return "NovoPedidoRecebidoEvent [codigoPedido=" + codigoPedido + ", nomeCliente=" + nomeCliente + "]";
  }



}
